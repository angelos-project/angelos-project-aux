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
import org.angproj.aux.TestInformationStub.refDouble
import org.angproj.aux.TestInformationStub.refFloat
import org.angproj.aux.TestInformationStub.refInt
import org.angproj.aux.TestInformationStub.refLong
import org.angproj.aux.TestInformationStub.refShort
import java.nio.ByteOrder
import java.nio.ByteBuffer as JavaByteBuffer
import kotlin.test.Test

class JvmByteArrayTest: BufferAware {


    @Test
    fun short() {
        val array = ByteArray(8)
        val buf = JavaByteBuffer.wrap(array)
        buf.order(ByteOrder.nativeOrder())
        buf.putShort(0, refShort)
        assertEquals(array.readShortAt(0), refShort)

        array.writeShortAt(0, refShort)
        buf.clear()
        kotlin.test.assertEquals(buf.getShort(0), refShort)
    }

    @Test
    fun shortRev() {
        val array = ByteArray(8)
        val buf = JavaByteBuffer.wrap(array)
        buf.order(if(ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) ByteOrder.BIG_ENDIAN else ByteOrder.LITTLE_ENDIAN)
        buf.putShort(0, refShort)
        assertEquals(array.readRevShortAt(0), refShort)

        array.writeRevShortAt(0, refShort)
        buf.clear()
        kotlin.test.assertEquals(buf.getShort(0), refShort)
    }

    /*@Test
    fun char() {
        val array = ByteArray(8)
        val buf = JavaByteBuffer.wrap(array)
        buf.order(ByteOrder.nativeOrder())
        buf.putChar(0, refChar)
        assertEquals(array.readCharAt(0), refChar)
    }*/

    @Test
    fun int() {
        val array = ByteArray(8)
        val buf = JavaByteBuffer.wrap(array)
        buf.order(ByteOrder.nativeOrder())
        buf.putInt(0, refInt)
        assertEquals(array.readIntAt(0), refInt)

        array.writeIntAt(0, refInt)
        buf.clear()
        kotlin.test.assertEquals(buf.getInt(0), refInt)
    }

    @Test
    fun intRev() {
        val array = ByteArray(8)
        val buf = JavaByteBuffer.wrap(array)
        buf.order(if(ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) ByteOrder.BIG_ENDIAN else ByteOrder.LITTLE_ENDIAN)
        buf.putInt(0, refInt)
        assertEquals(array.readRevIntAt(0), refInt)

        array.writeRevIntAt(0, refInt)
        buf.clear()
        kotlin.test.assertEquals(buf.getInt(0), refInt)
    }

    @Test
    fun long() {
        val array = ByteArray(8)
        val buf = JavaByteBuffer.wrap(array)
        buf.order(ByteOrder.nativeOrder())
        buf.putLong(0, refLong)
        assertEquals(array.readLongAt(0), refLong)

        array.writeLongAt(0, refLong)
        buf.clear()
        kotlin.test.assertEquals(buf.getLong(0), refLong)
    }

    @Test
    fun longRev() {
        val array = ByteArray(8)
        val buf = JavaByteBuffer.wrap(array)
        buf.order(if(ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) ByteOrder.BIG_ENDIAN else ByteOrder.LITTLE_ENDIAN)
        buf.putLong(0, refLong)
        assertEquals(array.readRevLongAt(0), refLong)

        array.writeRevLongAt(0, refLong)
        buf.clear()
        kotlin.test.assertEquals(buf.getLong(0), refLong)
    }

    @Test
    fun float() {
        val array = ByteArray(8)
        val buf = JavaByteBuffer.wrap(array)
        buf.order(ByteOrder.nativeOrder())
        buf.putFloat(0, refFloat)
        assertEquals(array.readFloatAt(0).toRawBits(), refFloat.toRawBits())

        array.writeFloatAt(0, refFloat)
        buf.clear()
        kotlin.test.assertEquals(buf.getFloat(0).toRawBits(), refFloat.toRawBits())
    }

    @Test
    fun floatRev() {
        val array = ByteArray(8)
        val buf = JavaByteBuffer.wrap(array)
        buf.order(if(ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) ByteOrder.BIG_ENDIAN else ByteOrder.LITTLE_ENDIAN)
        buf.putFloat(0, refFloat)
        assertEquals(array.readRevFloatAt(0).toRawBits(), refFloat.toRawBits())

        array.writeRevFloatAt(0, refFloat)
        buf.clear()
        kotlin.test.assertEquals(buf.getFloat(0).toRawBits(), refFloat.toRawBits())
    }

    @Test
    fun double() {
        val array = ByteArray(8)
        val buf = JavaByteBuffer.wrap(array)
        buf.order(ByteOrder.nativeOrder())
        buf.putDouble(0, refDouble)
        assertEquals(array.readDoubleAt(0), refDouble)

        array.writeDoubleAt(0, refDouble)
        buf.clear()
        kotlin.test.assertEquals(buf.getDouble(0).toRawBits(), refDouble.toRawBits())
    }

    @Test
    fun doubleRev() {
        val array = ByteArray(8)
        val buf = JavaByteBuffer.wrap(array)
        buf.order(if(ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) ByteOrder.BIG_ENDIAN else ByteOrder.LITTLE_ENDIAN)
        buf.putDouble(0, refDouble)
        assertEquals(array.readRevDoubleAt(0), refDouble)

        array.writeRevDoubleAt(0, refDouble)
        buf.clear()
        kotlin.test.assertEquals(buf.getDouble(0).toRawBits(), refDouble.toRawBits())
    }
}