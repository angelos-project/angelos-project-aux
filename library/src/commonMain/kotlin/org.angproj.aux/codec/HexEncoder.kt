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
import org.angproj.aux.util.withUtility
import kotlin.math.min


public class HexEncoder /*: Encoder<Binary, TextBuffer>*/ {

    private class BinaryBufferReader(private val buffer: Binary): PumpReader {
        private var mark = 0
        private val limit = buffer.limit

        override val outputCount: Long
            get() = mark.toLong()

        override val outputStale: Boolean
            get() = limit - mark <= 0

        override fun read(data: Segment<*>): Int {
            val length = min(limit - mark, data.limit-1)
            buffer.copyInto(data, 0, mark, mark + length)
            mark += length
            return length
        }
    }

    public fun encode(data: Binary): TextBuffer {
        val limit = data.limit
        val tb = BufMgr.text(limit * 2)
        val pipe = Pipe.buildBinaryPullPipe(BinaryBufferReader(data))

        do {
            withUtility {
                val octet = pipe.readByte()
                tb.write(octet.upperToHex())
                tb.write(octet.lowerToHex())
            }
        } while(pipe.count < limit)
        pipe.close()

        tb.flip()
        return tb
    }
}