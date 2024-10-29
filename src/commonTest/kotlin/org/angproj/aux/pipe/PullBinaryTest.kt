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
package org.angproj.aux.pipe

import org.angproj.aux.buf.wrap
import org.angproj.aux.io.*
import org.angproj.aux.mem.BufMgr
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.time.measureTime

class PullBinaryTest {

    /**
     * The goal is to pull all data from the BinarySource.
     * */
    @Test
    fun testStreamPull() {

        val len = 42 * 100

        val rand = BufMgr.bin(len).apply { securelyRandomize() }
        val canvas = BufMgr.bin(rand.capacity)

        val readable = Pipe.buildBinaryPullPipe(TestPumpReader(rand))
        val time = measureTime {
            canvas.wrap {
                do {
                    writeByte(readable.readByte()) // 1
                    writeShort(readable.readShort()) // 2, 3
                    writeInt(readable.readInt()) // 4, 7
                    writeLong(readable.readLong()) // 8, 15
                    writeFloat(readable.readFloat()) // 4, 19
                    writeDouble(readable.readDouble()) // 8, 27
                    writeUByte(readable.readUByte()) // 1, 28
                    writeUShort(readable.readUShort()) // 2, 30
                    writeUInt(readable.readUInt()) // 4, 34
                    writeULong(readable.readULong()) // 8, 42
                } while(position < limit)
            }
        }

        println(time)
        assertEquals(rand, canvas)
        assertFailsWith<UnsupportedOperationException> { readable.readByte() }
    }

    @Test
    fun testStreamPullClose() {

        val len = 42 * 100

        val rand = BufMgr.bin(len).apply { securelyRandomize() }
        val canvas = BufMgr.bin(rand.capacity)

        val readable = Pipe.buildBinaryPullPipe(TestPumpReader(rand))
        canvas.wrap {
            do {
                writeByte(readable.readByte()) // 1
                writeShort(readable.readShort()) // 2, 3
                writeInt(readable.readInt()) // 4, 7
                writeLong(readable.readLong()) // 8, 15
                writeFloat(readable.readFloat()) // 4, 19
                writeDouble(readable.readDouble()) // 8, 27
                writeUByte(readable.readUByte()) // 1, 28
                writeUShort(readable.readUShort()) // 2, 30
                writeUInt(readable.readUInt()) // 4, 34
                writeULong(readable.readULong()) // 8, 42
            } while(position < limit)
        }

        readable.close()
        assertFailsWith<UnsupportedOperationException> { readable.readByte() }
    }
}