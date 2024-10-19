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
import org.angproj.aux.util.Hex
import kotlin.math.min


public class HexDecoder : Decoder<TextBuffer, Binary> {

    private class TextBufferReader(private val buffer: TextBuffer): PumpReader {
        private var mark = buffer.mark
        private val limit = buffer.limit

        override fun read(data: Segment): Int {
            val length = min(limit - mark, data.limit)
            buffer.asBinary().copyInto(data, 0, mark, mark + length)
            mark += length
            return length
        }
    }

    override fun decode(data: TextBuffer): Binary {
        require((data.limit - data.mark).mod(2) == 0) { "Hexadecimals must be divisible by two." }

        val limit = data.limit - data.mark
        val bin = binOf(limit / 2)

        return bin.wrap {
            data.reset() // Resetting position to mark
            val pipe = Pipe.buildTextPullPipe(TextBufferReader(data))

            val value = {
                val cp = pipe.readGlyph()
                check(cp.value in Hex.valid)
                cp.value
            }

            do {
                val upperHex: Int = (Hex.hex2bin[value()]!!.toInt() shl 4)
                val lowerHex: Int = Hex.hex2bin[value()]!!
                writeByte((upperHex or lowerHex and 0xFF).toByte())
            } while(pipe.eofReached())
            pipe.close()

            bin.limitAt(position)
            bin
        }
    }
}