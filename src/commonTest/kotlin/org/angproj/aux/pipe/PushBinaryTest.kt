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
import org.angproj.aux.io.securelyRandomize
import org.angproj.aux.mem.BufMgr
import kotlin.test.*
import kotlin.time.measureTime

class PushBinaryTest {
    /**
     * The goal is to pull all data from the BinarySource.
     * */
    @Test
    fun testStreamPush() {

        val len = 42 * 100

        val rand = BufMgr.bin(len).apply { securelyRandomize() }
        val canvas = BufMgr.bin(rand.capacity)

        val writeable = Pipe.buildBinaryPushPipe(TestPumpWriter(canvas))
        val time = measureTime {
            rand.wrap {
                do {
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
                } while(position < limit)
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

    @Test
    fun testStreamPushAutoClose() {

        val len = 42 * 300 // Extra length to trigger first error

        val rand = BufMgr.bin(len).apply { securelyRandomize() }
        val canvas = BufMgr.bin(rand.capacity)

        val writeable = Pipe.buildBinaryPushPipe(TestPumpWriter(canvas, true))
        assertFailsWith<UnsupportedOperationException> {
            rand.wrap {
                do {
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
                } while(position < limit)
            }
        }

        /**
         * See PushTextTest for additional notes
         * */
        writeable.flush()
        assertNotEquals(rand, canvas)
        assertFailsWith<UnsupportedOperationException> { writeable.writeByte(123) }
        assertTrue { writeable.isOpen() } // Is this correct?
        writeable.close()
    }

    @Test
    fun testStreamPushManualClose() {

        val len = 42 * 100

        val rand = BufMgr.bin(len).apply { securelyRandomize() }
        val canvas = BufMgr.bin(rand.capacity)

        val writeable = Pipe.buildBinaryPushPipe(TestPumpWriter(canvas))
        rand.wrap {
            do {
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
            } while(position < limit / 2)
        }

        /**
         * See PushTextTest for additional notes
         * */
        writeable.flush()
        assertNotEquals(rand, canvas)
        writeable.close()
        assertFailsWith<UnsupportedOperationException> { writeable.writeByte(123) }
        writeable.flush()
        assertFailsWith<UnsupportedOperationException> { writeable.writeByte(123) }
    }
}