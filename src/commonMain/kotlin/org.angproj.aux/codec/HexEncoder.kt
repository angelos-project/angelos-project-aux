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
import org.angproj.aux.io.Binary
import org.angproj.aux.io.PumpReader
import org.angproj.aux.io.Segment
import org.angproj.aux.io.copyInto
import org.angproj.aux.pipe.Pipe
import org.angproj.aux.util.Hex
import org.angproj.aux.util.toCodePoint
import kotlin.math.min


public class HexEncoder : Encoder<Binary, TextBuffer> {

    private class BinaryBufferReader(private val buffer: Binary): PumpReader {
        private var mark = 0
        private val limit = buffer.limit

        override fun read(data: Segment): Int {
            val length = min(limit - mark, data.limit)
            buffer.segment.copyInto(data, 0, mark, mark + length)
            mark += length
            return length
        }
    }

    override fun encode(data: Binary): TextBuffer {
        val limit = data.limit
        val tb = TextBuffer(limit * 2)
        val pipe = Pipe.buildBinaryPullPipe(BinaryBufferReader(data))

        do {
            val octet = pipe.readByte().toInt()
            tb.writeGlyph(Hex.bin2hex[octet shr 4 and 0xF].toCodePoint())
            tb.writeGlyph(Hex.bin2hex[octet and 0xF].toCodePoint())
        } while(pipe.eofReached())
        pipe.close()

        return tb
    }
}