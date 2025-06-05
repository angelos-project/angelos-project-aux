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
import org.angproj.aux.io.DataSize
import org.angproj.aux.mem.BufMgr
import org.angproj.aux.util.Uuid4
import kotlin.test.*
import kotlin.time.measureTime

class PullBinaryTest : AbstractPullTest() {

    override var debugMode: Boolean = false


    @Test
    override fun testPullManualClose() {
        pullBinaryManualClose(DataSize._4K.size + DataSize._1K.size, DataSize._1K, DataSize._4K)
        pullBinaryManualClose(DataSize._4K.size + DataSize._1K.size + DataSize._512B.size, DataSize._1K, DataSize._4K)
        pullBinaryManualClose(DataSize._4K.size + DataSize._1K.size, DataSize._1K, DataSize._1K)
        pullBinaryManualClose(DataSize._4K.size + DataSize._1K.size + DataSize._512B.size, DataSize._1K, DataSize._1K)
    }

    @Test
    override fun testPullAutoClose() {
        pullBinaryAutomaticClose(DataSize._4K.size, DataSize._1K, DataSize._4K)

        // Buffer bigger than segment, bigger payload
       pullBinaryAutomaticClose(
           DataSize._4K.size + DataSize._1K.size, DataSize._1K, DataSize._4K)
        pullBinaryAutomaticClose(
            DataSize._4K.size + DataSize._1K.size + DataSize._512B.size, DataSize._1K, DataSize._4K)

        // Buffer bigger than segment, smaller payload
        pullBinaryAutomaticClose(
            DataSize._2K.size + DataSize._1K.size, DataSize._1K, DataSize._4K)
        pullBinaryAutomaticClose(
            DataSize._2K.size + DataSize._1K.size + DataSize._512B.size, DataSize._1K, DataSize._4K)

        // Buffer bigger than segment, smaller payload
        pullBinaryAutomaticClose(
            DataSize._2K.size, DataSize._1K, DataSize._4K)
        pullBinaryAutomaticClose(
            DataSize._2K.size + DataSize._512B.size, DataSize._1K, DataSize._4K)

        // Buffer and segment same size, larger payload
        pullBinaryAutomaticClose(
            DataSize._4K.size + DataSize._1K.size, DataSize._1K, DataSize._1K)
        pullBinaryAutomaticClose(
            DataSize._4K.size + DataSize._1K.size + DataSize._512B.size, DataSize._1K, DataSize._1K)
    }

    @Test
    fun testStreamPullClose() {

        val len = 42 * 100

        val rand = BufMgr.bin(len).apply { Uuid4.read(this._segment) }
        val canvas = BufMgr.bin(rand.capacity)
        println(rand.capacity)

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
        readable.close()
        assertFalse { readable.isOpen() }
        assertFailsWith<PipeException> { readable.readByte() }
    }

    @Test
    fun testStreamPullAutoClose() {

        val len = 42 * 100

        val rand = BufMgr.bin(len).apply { Uuid4.read(this._segment) }
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

        assertEquals(rand, canvas)
        assertFailsWith<StaleException> { readable.readByte() }
        assertFailsWith<StaleException> { readable.readByte() }
        assertTrue { readable.isOpen() } // Still open
        assertTrue { readable.telemetry().pumpOpen } // Still open
        assertFailsWith<StaleException> { readable.readByte() }
        assertFalse { readable.isOpen() } // Closes on third strike
        assertFalse { readable.telemetry().pumpOpen } // Closes on third strike
        assertFailsWith<PipeException> { readable.readByte() }
    }
}