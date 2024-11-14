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

import org.angproj.aux.buf.TextBuffer
import org.angproj.aux.buf.asBinary
import org.angproj.aux.buf.wrap
import org.angproj.aux.io.*
import org.angproj.aux.pipe.Pipe
import org.angproj.aux.util.Base64
import kotlin.math.min

public class Base64Decoder(
    protected val alphabet: Map<Int, Int> = Base64.base2bin,
    protected val padding: Int = Base64.padding
) /*: Decoder<TextBuffer, Binary>*/{

    private class TextBufferReader(private val buffer: TextBuffer): PumpReader {
        private var mark = buffer.mark
        private val limit = buffer.limit

        override val outputCount: Long
            get() = (mark - buffer.mark).toLong()

        override val outputStale: Boolean
            get() = limit - mark <= 0

        override fun read(data: Segment<*>): Int {
            val length = min(limit - mark, data.limit)
            buffer.asBinary().copyInto(data, 0, mark, mark + length)
            mark += length
            return length
        }
    }

    public fun decode(data: TextBuffer): Binary {
        require((data.limit - data.mark).mod(4) == 0) { "Base64 block must be divisible by 4." }

        val fullBlkCnt = data.limit - data.mark / 4
        val bin = binOf(3 * fullBlkCnt)

        return bin.wrap {
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
                        writeByte((bits shr 16).toByte())
                        writeByte((bits shr 8).toByte())
                        writeByte(bits.toByte())
                    }
                    2 -> {
                        writeByte((bits shr 16).toByte())
                        writeByte((bits shr 8).toByte())
                    }
                }
            } while (cp != padding)
            pipe.close()

            bin.apply { limitAt(position) }
        }
    }
}