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
import org.angproj.aux.io.*
import org.angproj.aux.mem.BufMgr
import org.angproj.aux.pipe.Pipe
import org.angproj.aux.util.Base64
import org.angproj.aux.util.toCodePoint
import kotlin.math.min

public class Base64Encoder(
    protected val alphabet: List<Int> = Base64.bin2base,
    protected val padding: Int = Base64.padding
) : Encoder<Binary, TextBuffer> {

    private class BinaryBufferReader(private val buffer: Binary): PumpReader {
        private var mark = 0
        private val limit = buffer.limit

        override fun read(data: Segment): Int {
            val length = min(limit - mark, data.limit)
            buffer.asBinary().copyInto(data, 0, mark, mark + length)
            mark += length
            return length
        }
    }

    override fun encode(data: Binary): TextBuffer {
        val limit = data.limit
        val fullBlkCnt = limit / 3
        val tb = BufMgr.text(fullBlkCnt * 4)
        val pipe = Pipe.buildBinaryPullPipe(BinaryBufferReader(data))

        do {
            var octet: Int
            var bits: Int = 0
            var cnt = 3
            do {
                octet = pipe.readByte().toInt()
                bits = (bits shl 8) or octet and 0xff
            } while(--cnt > 0 || !pipe.eofReached())
            when (cnt) {
                0 -> {
                    tb.writeGlyph(alphabet[bits ushr 18 and 0x3f].toCodePoint())
                    tb.writeGlyph(alphabet[bits ushr 12 and 0x3f].toCodePoint())
                    tb.writeGlyph(alphabet[bits ushr 6 and 0x3f].toCodePoint())
                    tb.writeGlyph(alphabet[bits and 0x3f].toCodePoint())
                }
                1 -> {
                    tb.writeGlyph(alphabet[octet shr 2].toCodePoint())
                    tb.writeGlyph(alphabet[(octet shl 4) and 0x3f or (octet shr 4)].toCodePoint())
                    tb.writeGlyph(alphabet[octet shl 2 and 0x3f].toCodePoint())
                    tb.writeGlyph(padding.toCodePoint())
                }
                2 -> {
                    tb.writeGlyph(alphabet[bits shr 2].toCodePoint())
                    tb.writeGlyph(alphabet[bits shl 4 and 0x3f].toCodePoint())
                    tb.writeGlyph(padding.toCodePoint())
                    tb.writeGlyph(padding.toCodePoint())
                }
            }
        } while (!pipe.eofReached() || cnt == 0)
        pipe.close()

        tb.flip()
        return tb
    }
}