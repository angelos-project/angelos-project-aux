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

import kotlin.test.Test
import kotlin.test.assertEquals

class TypeSizeTest {

    /**
     * BYTE(Byte.SIZE_BYTES),
     * */
    @Test
    fun testByte() {
        assertEquals(TypeSize.BYTE.size, Byte.SIZE_BYTES)
        assertEquals(TypeSize.BYTE.size, TypeSize.byte)
    }

    /**
     * U_BYTE(UByte.SIZE_BYTES),
     * */
    @Test
    fun testUByte() {
        assertEquals(TypeSize.U_BYTE.size, UByte.SIZE_BYTES)
        assertEquals(TypeSize.U_BYTE.size, TypeSize.uByte)
    }

    /**
     * SHORT(Short.SIZE_BYTES),
     * */
    @Test
    fun testShort() {
        assertEquals(TypeSize.SHORT.size, Short.SIZE_BYTES)
        assertEquals(TypeSize.SHORT.size, TypeSize.short)
    }

    /**
     * U_SHORT(UShort.SIZE_BYTES),
     * */
    @Test
    fun testUShort() {
        assertEquals(TypeSize.U_SHORT.size, UShort.SIZE_BYTES)
        assertEquals(TypeSize.U_SHORT.size, TypeSize.uShort)
    }

    /**
     * INT(Int.SIZE_BYTES),
     * */
    @Test
    fun testInt() {
        assertEquals(TypeSize.INT.size, Int.SIZE_BYTES)
        assertEquals(TypeSize.INT.size, TypeSize.int)

    }

    /**
     * U_INT(UInt.SIZE_BYTES),
     * */
    @Test
    fun testUInt() {
        assertEquals(TypeSize.U_INT.size, UInt.SIZE_BYTES)
        assertEquals(TypeSize.U_INT.size, TypeSize.uInt)
    }

    /**
     * LONG(Long.SIZE_BITS),
     * */
    @Test
    fun testLong() {
        assertEquals(TypeSize.LONG.size, Long.SIZE_BYTES)
        assertEquals(TypeSize.LONG.size, TypeSize.long)

    }

    /**
     * U_LONG(ULong.SIZE_BYTES),
     * */
    @Test
    fun testULong() {
        assertEquals(TypeSize.U_LONG.size, ULong.SIZE_BYTES)
        assertEquals(TypeSize.U_LONG.size, TypeSize.uLong)

    }

    /**
     * FLOAT(Float.SIZE_BYTES),
     * */
    @Test
    fun testFloat() {
        assertEquals(TypeSize.FLOAT.size, Float.SIZE_BYTES)
        assertEquals(TypeSize.FLOAT.size, TypeSize.float)

    }

    /**
     * DOUBLE(Double.SIZE_BYTES)
     * */
    @Test
    fun testDouble() {
        assertEquals(TypeSize.DOUBLE.size, Double.SIZE_BYTES)
        assertEquals(TypeSize.DOUBLE.size, TypeSize.double)
    }
}