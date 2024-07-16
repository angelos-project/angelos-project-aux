/**
 * Copyright (c) 2022-2023 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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
package org.angproj.aux.util

import kotlin.test.Test
import kotlin.test.assertEquals

class ByteArrayTest: BufferAware {
    private val refChar: Char = 'Ö'

    private val refLong: Long = 0x3569356935693569L
    private val refULong: ULong = 0xCA96CA96CA96CA96uL

    private val refInt: Int = 0x35693569
    private val refUInt: UInt = 0xCA96CA96u

    private val refShort: Short = 0x3569
    private val refUShort: UShort = 0xCA96u

    private val refFloat: Float = Float.fromBits(refInt)
    private val refDouble: Double = Double.fromBits(refLong)

    @Test
    fun short() {
        val array = ByteArray(8)
        array.writeShortAt(0, refShort)
        assertEquals(array.readShortAt(0), refShort)
    }

    @Test
    fun ushort() {
        val array = ByteArray(8)
        array.writeUShortAt(0, refUShort)
        assertEquals(array.readUShortAt(0), refUShort)
    }

    @Test
    fun char() {
        val array = ByteArray(8)
        array.writeCharAt(0, refChar)
        assertEquals(array.readCharAt(0), refChar)
    }

    @Test
    fun int() {
        val array = ByteArray(8)
        array.writeIntAt(0, refInt)
        assertEquals(array.readIntAt(0), refInt)
    }

    @Test
    fun uint() {
        val array = ByteArray(8)
        array.writeUIntAt(0, refUInt)
        assertEquals(array.readUIntAt(0), refUInt)
    }

    @Test
    fun long() {
        val array = ByteArray(8)
        array.writeLongAt(0, refLong)
        assertEquals(array.readLongAt(0), refLong)
    }

    @Test
    fun ulong() {
        val array = ByteArray(8)
        array.writeULongAt(0, refULong)
        assertEquals(array.readULongAt(0), refULong)
    }

    @Test
    fun float() {
        val array = ByteArray(8)
        array.writeFloatAt(0, refFloat)
        assertEquals(array.readFloatAt(0), refFloat)
    }

    @Test
    fun double() {
        val array = ByteArray(8)
        array.writeDoubleAt(0, refDouble)
        assertEquals(array.readDoubleAt(0), refDouble)
    }
}