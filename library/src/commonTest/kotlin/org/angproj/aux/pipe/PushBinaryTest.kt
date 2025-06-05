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
import org.angproj.aux.mem.*
import org.angproj.aux.util.Uuid4
import kotlin.test.*
import kotlin.time.measureTime

class PushBinaryTest : AbstractPushTest() {

    override var debugMode: Boolean = false

    @Test
    override fun testPushManualClose() {
        pushBinaryManualClose(DataSize._4K.size + DataSize._1K.size, DataSize._1K, DataSize._4K)
        pushBinaryManualClose(DataSize._4K.size + DataSize._1K.size + DataSize._512B.size, DataSize._1K, DataSize._4K)
        pushBinaryManualClose(DataSize._4K.size + DataSize._1K.size, DataSize._1K, DataSize._1K)
        pushBinaryManualClose(DataSize._4K.size + DataSize._1K.size + DataSize._512B.size, DataSize._1K, DataSize._1K)
    }

    @Test
    override fun testPushAutoClose() {
        pushBinaryAutomaticClose(DataSize._4K.size, DataSize._1K, DataSize._4K)

        // Buffer bigger than segment, bigger payload
        pushBinaryAutomaticClose(
            DataSize._4K.size + DataSize._1K.size, DataSize._1K, DataSize._4K)
        pushBinaryAutomaticClose(
            DataSize._4K.size + DataSize._1K.size + DataSize._512B.size, DataSize._1K, DataSize._4K)

        // Buffer bigger than segment, smaller payload
        pushBinaryAutomaticClose(
            DataSize._2K.size + DataSize._1K.size, DataSize._1K, DataSize._4K)
        pushBinaryAutomaticClose(
            DataSize._2K.size + DataSize._1K.size + DataSize._512B.size, DataSize._1K, DataSize._4K)

        // Buffer bigger than segment, smaller payload
        pushBinaryAutomaticClose(
            DataSize._2K.size, DataSize._1K, DataSize._4K)
        pushBinaryAutomaticClose(
            DataSize._2K.size + DataSize._512B.size, DataSize._1K, DataSize._4K)

        // Buffer and segment same size, larger payload
        pushBinaryAutomaticClose(
            DataSize._4K.size + DataSize._1K.size, DataSize._1K, DataSize._1K)
        pushBinaryAutomaticClose(
            DataSize._4K.size + DataSize._1K.size + DataSize._512B.size, DataSize._1K, DataSize._1K)
    }

    @Test
    override fun testPushBrokenPump() {
        pushBinaryBroken()
    }

    /**
     * The goal is to pull all data from the BinarySource.
     * */
    @Test
    fun testStreamPush() {

        val len = 42 * 100

        val rand = BufMgr.bin(len).apply { Uuid4.read(this._segment) }
        val canvas = BufMgr.bin(rand.capacity)

        val writeable = Pipe.buildBinaryPushPipe(TestPumpWriter(canvas))
        val time = measureTime {
            rand.wrap {
                while (position < limit) {
                    writeable.writeByte(readByte()) // 1
                    writeable.writeShort(readShort()) // 2, 3
                    writeable.writeInt(readInt()) // 4, 7
                    writeable.writeLong(readLong()) // 8, 15
                    writeable.writeFloat(readFloat()) // 4, 19
                    writeable.writeDouble(readDouble()) // 8, 27
                    writeable.writeUByte(readUByte()) // 1, 28
                    writeable.writeUShort(readUShort()) // 2, 30
                    writeable.writeUInt(readUInt()) // 4, 34
                    writeable.writeULong(readULong()) // 8, 42
                }
            }
        }

        /**
         * See PushTextTest for additional notes
         * */
        assertNotEquals(rand, canvas)
        writeable.flush()
        println(time)
        assertEquals(rand, canvas)
        assertTrue { writeable.isOpen() }
        writeable.close()
        assertFalse { writeable.isOpen() }
    }

    // ========================

    /**
     * Demonstrates that the [write*] functions are appropriate.
     * And that all internal memory is returned at manual close.
     * */
    @Test
    fun testStreamWrite() {

        val len = 42

        val rand = BufMgr.bin(len + 6).apply { Uuid4.read(this._segment) }
        rand.limitAt(len)
        val canvas = BufMgr.bin(rand.capacity - 6)
        canvas.limitAt(len)

        val writeable = Pipe.buildBinaryPushPipe(TestPumpWriter(canvas))
        val time = measureTime {
            rand.wrap {
                writeable.writeByte(readByte()) // 1
                writeable.writeShort(readShort()) // 2, 3
                writeable.writeInt(readInt()) // 4, 7
                writeable.writeLong(readLong()) // 8, 15
                writeable.writeFloat(readFloat()) // 4, 19
                writeable.writeDouble(readDouble()) // 8, 27
                writeable.writeUByte(readUByte()) // 1, 28
                writeable.writeUShort(readUShort()) // 2, 30
                writeable.writeUInt(readUInt()) // 4, 34
                writeable.writeULong(readULong()) // 8, 42
            }
        }

        writeable.flush()
        println(time)
        assertEquals(rand.checkSum(), canvas.checkSum())
        writeable.close()
        assertEquals(writeable.telemetry().usedMem, 0)
    }

    /**
     * Demonstrating proper single use of [flush] before comparison.
     * And that not using flush leads to incomplete transmission of data.
     * */
    @Test
    fun testSingleFlushBasic() {
        val len = 42 * 100

        val rand = binOf(len).apply { Uuid4.read(this._segment) }
        val canvas = binOf(rand.capacity)

        val writeable = Pipe.buildBinaryPushPipe(TestPumpWriter(canvas))
        measureTime {
            rand.wrap {
                while (position < limit) {
                    writeable.writeByte(readByte()) // 1
                    writeable.writeShort(readShort()) // 2, 3
                    writeable.writeInt(readInt()) // 4, 7
                    writeable.writeLong(readLong()) // 8, 15
                    writeable.writeFloat(readFloat()) // 4, 19
                    writeable.writeDouble(readDouble()) // 8, 27
                    writeable.writeUByte(readUByte()) // 1, 28
                    writeable.writeUShort(readUShort()) // 2, 30
                    writeable.writeUInt(readUInt()) // 4, 34
                    writeable.writeULong(readULong()) // 8, 42
                }
            }
        }.also { println(it) }

        assertNotEquals(rand.checkSum(), canvas.checkSum())
        writeable.flush()
        assertEquals(canvas.checkSum(), rand.checkSum())
        writeable.close()
    }

    /**
     * Simply demonstration reuse of [flush] method over one pipe session with
     * complete correct data at arrival no misses or losses, the only check carried
     * out is [assertEquals]
     * */
    @Test
    fun testMultipleFlushBasic() {
        val len = 42 * 100

        //val runs = listOf(768, 1536, 2304, 3072, 3840, len)
        val runs = listOf(768, 2304, 3840, len)

        val rand = binOf(len).apply { Uuid4.read(this._segment) }
        val canvas = binOf(rand.capacity)

        val writeable = Pipe.buildBinaryPushPipe(TestPumpWriter(canvas))
        measureTime {
            rand.wrap {
                runs.forEach {
                    while (position < it) {
                        writeable.writeByte(readByte()) // 1
                        writeable.writeShort(readShort()) // 2, 3
                        writeable.writeInt(readInt()) // 4, 7
                        writeable.writeLong(readLong()) // 8, 15
                        writeable.writeFloat(readFloat()) // 4, 19
                        writeable.writeDouble(readDouble()) // 8, 27
                        writeable.writeUByte(readUByte()) // 1, 28
                        writeable.writeUShort(readUShort()) // 2, 30
                        writeable.writeUInt(readUInt()) // 4, 34
                        writeable.writeULong(readULong()) // 8, 42
                    }
                    println(writeable.count)
                    writeable.flush()
                }
            }
        }.also { println(it) }

        assertEquals(canvas.checkSum(), rand.checkSum())
        writeable.close()
    }

    /**
     * Proves that after manually closing a pipe, both writing and flushing and writing again,
     * causes a PipeException to be thrown, even if not properly flushing.
     * */
    @Test
    fun testStreamPushManualClose() {

        val len = 42 * 100

        val rand = BufMgr.bin(len).apply { securelyRandomize() }
        val canvas = BufMgr.bin(rand.capacity)

        val writeable = Pipe.buildBinaryPushPipe(TestPumpWriter(canvas))
        rand.wrap {
            while (position < limit) {
                writeable.writeByte(readByte()) // 1
                writeable.writeShort(readShort()) // 2, 3
                writeable.writeInt(readInt()) // 4, 7
                writeable.writeLong(readLong()) // 8, 15
                writeable.writeFloat(readFloat()) // 4, 19
                writeable.writeDouble(readDouble()) // 8, 27
                writeable.writeUByte(readUByte()) // 1, 28
                writeable.writeUShort(readUShort()) // 2, 30
                writeable.writeUInt(readUInt()) // 4, 34
                writeable.writeULong(readULong()) // 8, 42
            }
        }

        writeable.close()
        assertFailsWith<PipeException> { writeable.writeByte(123) }
        assertFailsWith<PipeException> { writeable.flush() }
        assertFailsWith<PipeException> { writeable.writeByte(123) }
    }

    /**
     * Proves automatic closing after three [StaleException]s, in the [PushPipe] example
     * it is required to [flush] real data in order to trigger the exception in comparison
     * to the [PullPipe]. Because of the pushing instead of pulling, the pump is affected
     * only at flush.
     * */
    @Test
    fun testStreamPushAutoClose() {

        val len = 42 * 100 // Extra length to trigger first error

        val rand = BufMgr.bin(len).apply { Uuid4.read(this._segment) }
        val canvas = BufMgr.bin(rand.capacity)

        val writeable = Pipe.buildBinaryPushPipe(TestPumpWriter(canvas))
        rand.wrap {
            while (position < limit) {
                writeable.writeByte(readByte()) // 1
                writeable.writeShort(readShort()) // 2, 3
                writeable.writeInt(readInt()) // 4, 7
                writeable.writeLong(readLong()) // 8, 15
                writeable.writeFloat(readFloat()) // 4, 19
                writeable.writeDouble(readDouble()) // 8, 27
                writeable.writeUByte(readUByte()) // 1, 28
                writeable.writeUShort(readUShort()) // 2, 30
                writeable.writeUInt(readUInt()) // 4, 34
                writeable.writeULong(readULong()) // 8, 42
            }
        }

        assertNotEquals(rand, canvas)
        writeable.flush()
        assertFailsWith<StaleException> {
            writeable.writeByte(123);
            writeable.flush()
        }
        assertFailsWith<StaleException> {
            writeable.writeByte(123);
            writeable.flush()
        }
        assertTrue { writeable.isOpen() } // Still open
        assertTrue { writeable.telemetry().pumpOpen } // Still open
        assertFailsWith<StaleException> {
            writeable.writeByte(123);
            writeable.flush()
        }
        assertTrue { writeable.isOpen() } // Still open
        assertFalse { writeable.telemetry().pumpOpen } // Closes on third strike
        assertFailsWith<PipeException> {
            writeable.writeByte(123);
            writeable.flush()
        }
    }
}
