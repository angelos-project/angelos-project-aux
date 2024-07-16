/**
 * Copyright (c) 2022-2024 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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

import junit.framework.TestCase.assertEquals
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.ByteBuffer as JavaByteBuffer
import kotlin.test.Test
import kotlin.test.assertNotEquals

class JvmByteArrayTest: BufferAware {
    /*private val refChar: Char = 'Ö'

    private val refShort: Short = 0B1010101_10101010

    private val refInt: Int = 0B1010101_10101010_10101010_10101010

    private val refLong: Long = 0B1010101_10101010_10101010_10101010_10101010_10101010_10101010_10101010L

    private val refFloat: Float = 23.43585F
    private val refDouble: Double = -0.892384774029876*/

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
        val buf = JavaByteBuffer.wrap(array)
        buf.order(ByteOrder.nativeOrder())
        buf.putShort(0, refShort)
        assertEquals(array.readShortAt(0), refShort)
    }

    @Test
    fun char() {
        val array = ByteArray(8)
        val buf = JavaByteBuffer.wrap(array)
        buf.order(ByteOrder.nativeOrder())
        buf.putChar(0, refChar)
        assertEquals(array.readCharAt(0), refChar)
    }

    @Test
    fun int() {
        val array = ByteArray(8)
        val buf = JavaByteBuffer.wrap(array)
        buf.order(ByteOrder.nativeOrder())
        buf.putInt(0, refInt)
        assertEquals(array.readIntAt(0), refInt)
    }

    @Test
    fun long() {
        val array = ByteArray(8)
        val buf = JavaByteBuffer.wrap(array)
        buf.order(ByteOrder.nativeOrder())
        buf.putLong(0, refLong)
        assertEquals(array.readLongAt(0), refLong)
    }

    @Test
    fun float() {
        val array = ByteArray(8)
        val buf = JavaByteBuffer.wrap(array)
        buf.order(ByteOrder.nativeOrder())
        buf.putFloat(0, refFloat)
        assertEquals(array.readFloatAt(0).toRawBits(), refFloat.toRawBits())
    }

    @Test
    fun double() {
        val array = ByteArray(8)
        val buf = JavaByteBuffer.wrap(array)
        buf.order(ByteOrder.nativeOrder())
        buf.putDouble(0, refDouble)
        assertEquals(array.readDoubleAt(0), refDouble)
    }
}