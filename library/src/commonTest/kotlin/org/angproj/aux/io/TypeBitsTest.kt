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

class TypeBitsTest {

    /**
     * BYTE(Byte.SIZE_BITS),
     * */
    @Test
    fun testByte() {
        assertEquals(TypeBits.BYTE.bits, Byte.SIZE_BITS)
        assertEquals(TypeBits.BYTE.bits, TypeBits.byte)

    }

    /**
     * U_BYTE(UByte.SIZE_BITS),
     * */
    @Test
    fun testUByte() {
        assertEquals(TypeBits.U_BYTE.bits, UByte.SIZE_BITS)
        assertEquals(TypeBits.U_BYTE.bits, TypeBits.uByte)
    }

    /**
     * SHORT(Short.SIZE_BITS),
     * */
    @Test
    fun testShort() {
        assertEquals(TypeBits.SHORT.bits, Short.SIZE_BITS)
        assertEquals(TypeBits.SHORT.bits, TypeBits.short)
    }

    /**
     * U_SHORT(UShort.SIZE_BITS),
     * */
    @Test
    fun testUShort() {
        assertEquals(TypeBits.U_SHORT.bits, UShort.SIZE_BITS)
        assertEquals(TypeBits.U_SHORT.bits, TypeBits.uShort)
    }

    /**
     * INT(Int.SIZE_BITS),
     * */
    @Test
    fun testInt() {
        assertEquals(TypeBits.INT.bits, Int.SIZE_BITS)
        assertEquals(TypeBits.INT.bits, TypeBits.int)
    }

    /**
     * U_INT(UInt.SIZE_BITS),
     * */
    @Test
    fun testUInt() {
        assertEquals(TypeBits.U_INT.bits, UInt.SIZE_BITS)
        assertEquals(TypeBits.U_INT.bits, TypeBits.uInt)
    }

    /**
     * LONG(Long.SIZE_BITS),
     * */
    @Test
    fun testLong() {
        assertEquals(TypeBits.LONG.bits, Long.SIZE_BITS)
        assertEquals(TypeBits.LONG.bits, TypeBits.long) }

    /**
     * U_LONG(ULong.SIZE_BITS),
     * */
    @Test
    fun testULong() {
        assertEquals(TypeBits.U_LONG.bits, ULong.SIZE_BITS)
        assertEquals(TypeBits.U_LONG.bits, TypeBits.uLong)
    }

    /**
     * FLOAT(Float.SIZE_BITS),
     * */
    @Test
    fun testFloat() {
        assertEquals(TypeBits.FLOAT.bits, Float.SIZE_BITS)
        assertEquals(TypeBits.FLOAT.bits, TypeBits.float)
    }

    /**
     * DOUBLE(Double.SIZE_BITS)
     * */
    @Test
    fun testDouble() {
        assertEquals(TypeBits.DOUBLE.bits, Double.SIZE_BITS)
        assertEquals(TypeBits.DOUBLE.bits, TypeBits.double )
    }
}