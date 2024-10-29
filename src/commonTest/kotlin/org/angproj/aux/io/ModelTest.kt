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
package org.angproj.aux.io

import org.angproj.aux.mem.ModelMem
import org.angproj.aux.util.BinHex
import org.angproj.aux.util.EndianAware
import kotlin.test.Test
import kotlin.test.assertEquals

class ModelTest: AbstractSegmentValidator(), EndianAware {

    private val createNew: (size: Int) -> Model = { ModelMem.allocate(it) }

    @Test
    fun testByteWriteReadSync() = byteWriteReadSync(createNew)

    @Test
    fun testShortReadAsync() = shortReadAsync(createNew)

    @Test
    fun testShortWriteAsync() = shortWriteAsync(createNew)

    @Test
    fun testIntReadAsync() = intReadAsync(createNew)

    @Test
    fun testIntWriteAsync() = intWriteAsync(createNew)

    @Test
    fun testLongReadAsync() = longReadAsync(createNew)

    @Test
    fun testLongWriteAsync() = longWriteAsync(createNew)

    @Test
    fun testByteRWOutbound() = byteRWOutbound(createNew)

    @Test
    fun testShortRWOutbound() = shortRWOutbound(createNew)

    @Test
    fun testIntRWOutbound() = intRWOutbound(createNew)

    @Test
    fun testLongRWOutbound() = longRWOutbound(createNew)

    @Test
    fun testTryCopyInto() = tryCopyInto(createNew)

    @Test
    fun testTryCopyOfRange() = tryCopyOfRange(createNew)

    @Test
    fun testTryCopyOf() = tryCopyOf(createNew)

    @Test
    fun testOneSize() {
        val seg = ModelMem.allocate(9)
        println(seg.size)
        println(seg.limit)
        seg.close()
    }

    @Test
    fun makeTrix() {

        fun LongArray.byteSave(off: Int, value: Long, mask: Long, size: Int) {
            val pos = off * TypeSize.long
            set(0, (get(0) and (mask shl pos).inv()) or (value shl pos))
            if(off > TypeSize.long - size) set(1, ((get(1) and (-1L shl ((off - size) * TypeSize.long))) or (value ushr ((TypeSize.long - off) * TypeSize.long))))
        }

        fun LongArray.shortSave(off: Int, value: Long, mask: Long, size: Int) {
            val pos = off * TypeSize.long
            set(0, (get(0) and (mask shl pos).inv()) or (value shl pos))
            if(off > TypeSize.long - size) set(1, ((get(1) and 0xff.inv()) or (value ushr 8)))
        }

        fun LongArray.byteLoad(off: Int, mask: Long, size: Int): Long = ((
                get(0) ushr (off * 8)) or if(off > TypeSize.long - size) ((
                get(1) and (-1L shl ((off - size) * 8)).inv()) shl ((8 - off) * 8)) else 0)

        val buf = longArrayOf(0xFEDCBA9876543211uL.toLong(), 0xFEDCBA9876543211uL.toLong())

        println("==== ==== ==== ==== ==== ==== ==== ====")
        (0 until 8).forEach {
            val cpy = buf.copyOf()
            cpy.byteSave(it, 0xFFFFFFFF11111111uL.toLong(), longMask, TypeSize.long)
            val load = cpy.byteLoad(it, longMask, TypeSize.long)
            println(BinHex.encodeToHex(cpy.asLittleByteArray().reversedArray()).uppercase())
            println(load.toULong().toString(16).uppercase())
        }
        println("==== ==== ==== ==== ==== ==== ==== ====")
        (0 until 8).forEach {
            val cpy = buf.copyOf()
            cpy.byteSave(it, 0xFFFF1111, intMask, TypeSize.int)
            val load = cpy.byteLoad(it, intMask, TypeSize.int)
            println(BinHex.encodeToHex(cpy.asLittleByteArray().reversedArray()).uppercase())
            println(load.toUInt().toString(16).uppercase())
        }
        println("==== ==== ==== ==== ==== ==== ==== ====")
        (0 until 8).forEach {
            val cpy = buf.copyOf()
            cpy.shortSave(it, 0xFF11, shortMask, TypeSize.short)
            val load = cpy.byteLoad(it, shortMask, TypeSize.short)
            println(BinHex.encodeToHex(cpy.asLittleByteArray().reversedArray()).uppercase())
            println(load.toUShort().toString(16).uppercase())
        }
        println("==== ==== ==== ==== ==== ==== ==== ====")
        (0 until 8).forEach {
            val cpy = buf.copyOf()
            cpy.byteSave(it, 0xF1, byteMask, TypeSize.byte)
            val load = cpy.byteLoad(it, byteMask, TypeSize.byte)
            println(BinHex.encodeToHex(cpy.asLittleByteArray().reversedArray()).uppercase())
            println(load.toUByte().toString(16).uppercase())
        }
        println("==== ==== ==== ==== ==== ==== ==== ====")

        val origStr = "FEDCBA9876543211FEDCBA9876543211"

        val cmpStr = "${buf[0].toULong().toString(16).uppercase()}${buf[1].toULong().toString(16).uppercase()}"
        println(origStr)
        println(cmpStr)
        assertEquals(cmpStr, origStr)
    }

    companion object {
        const val longMask: Long = -1
        const val intMask: Long = 0xFFFFFFFF
        const val shortMask: Long = 0xFFFF
        const val byteMask: Long = 0xFF
    }
}