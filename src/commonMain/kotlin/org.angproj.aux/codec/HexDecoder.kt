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
import org.angproj.aux.pipe.GlyphSink
import org.angproj.aux.util.Hex
import kotlin.math.min


public class HexDecoder /*: Decoder<TextBuffer, Binary>*/ {

    private class TextBufferReader(private val buffer: TextBuffer): PumpReader {
        private var mark = buffer.mark
        private val limit = buffer.limit

        override val outputCount: Long
            get() = (mark - buffer.mark).toLong()

        override val outputStale: Boolean
            get() = limit - mark <= 0

        override fun read(data: Segment<*>): Int {
            val length = min(limit - mark, data.limit)
            if(length > 0) buffer.asBinary().copyInto(data, 0, mark, mark + length)
            mark += length
            return length
        }
    }

    private inline fun <reified E: Any> invalidHex(data: TextBuffer): E {
        error("Invalid Hex value before position " + data.position)
    }

    private inline fun <reified E: Any> invalidHex(pipe: GlyphSink): E {
        pipe.close()
        error("Invalid Hex value at position " + (pipe.count - 1))
    }

    public fun decode(data: TextBuffer): Binary {
        require((data.limit - data.mark).mod(2) == 0) { "Hexadecimals must be divisible by two." }

        val limit = data.limit - data.mark
        val bin = binOf(limit / 2)

        return bin.wrap {
            data.reset() // Resetting position to mark

            /*while (data.position < data.limit) {
                val upperHex = Hex.hex2bin[data.readGlyph().value] ?: invalidHex(data)
                val lowerHex = Hex.hex2bin[data.readGlyph().value] ?: invalidHex(data)
                writeByte((((upperHex shl 4) or lowerHex) and 0xFF).toByte())
            }*/

            val pipe = Pipe.buildTextPullPipe(TextBufferReader(data))
            while(pipe.count < limit) {
                val upperHex = Hex.hex2bin[pipe.readGlyph().value] ?: invalidHex(pipe)
                val lowerHex = Hex.hex2bin[pipe.readGlyph().value] ?: invalidHex(pipe)
                writeByte((((upperHex shl 4) or lowerHex) and 0xFF).toByte())
            }
            pipe.close()

            bin.apply { limitAt(position) }
        }
    }
}