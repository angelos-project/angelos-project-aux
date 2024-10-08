/**
 * Copyright (c) 2024 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
 *
 * This software is available under the terms of the MIT license. Parts are licensed
 * under different terms if stated. The legal terms are attached to the LICENSE file
 * and are made available on:
 *
 *      https://opensource.org/licenses/MIT
 *
 * SPDX-License-Identifier: MIT
 *
 * Contributors:
 *      Kristoffer Paulsson - initial implementation
 */
package org.angproj.aux.codec

import org.angproj.aux.buf.BinaryBuffer
import org.angproj.aux.buf.TextBuffer
import org.angproj.aux.io.Binary
import org.angproj.aux.io.PumpReader
import org.angproj.aux.io.Segment
import org.angproj.aux.io.copyInto
import org.angproj.aux.pipe.Pipe
import org.angproj.aux.util.Base64
import kotlin.math.min

public class Base64Decoder(
    protected val alphabet: Map<Int, Int> = Base64.base2bin,
    protected val padding: Int = Base64.padding
) : Decoder<TextBuffer, Binary>{

    private class TextBufferReader(private val buffer: TextBuffer): PumpReader {
        private var mark = buffer.mark
        private val limit = buffer.limit

        override fun read(data: Segment): Int {
            val length = min(limit - mark, data.limit)
            buffer.segment.copyInto(data, 0, mark, mark + length)
            mark += length
            return length
        }
    }

    override fun decode(data: TextBuffer): Binary {
        require((data.limit - data.mark).mod(4) == 0) { "Base64 block must be divisible by 4." }

        val fullBlkCnt = data.limit - data.mark / 4
        val bin = Binary(3 * fullBlkCnt)
        val bb = BinaryBuffer(bin.segment, true)
        val pipe = Pipe.buildTextPullPipe(TextBufferReader(data))

        do {
            var cp: Int
            var bits: Int = 0
            var cnt = 4
            do {
                cp = pipe.readGlyph().value
                when {
                    cp != padding -> bits = (bits shl 6) or alphabet.getValue(cp) and 0B0011_1111
                    cnt == 2 -> bits = (bits shl 12)
                    cnt == 1 -> bits = (bits shl 6)
                }
            } while(--cnt > 0 && cp != padding)
            when (cnt) {
                0, 1 -> {
                    bb.writeByte((bits shr 16).toByte())
                    bb.writeByte((bits shr 8).toByte())
                    bb.writeByte(bits.toByte())
                }
                2 -> {
                    bb.writeByte((bits shr 16).toByte())
                    bb.writeByte((bits shr 8).toByte())
                }
            }
        } while (!pipe.eofReached() || cp != padding)
        pipe.close()

        bin.limitAt(bb.position)
        return bin
    }
}