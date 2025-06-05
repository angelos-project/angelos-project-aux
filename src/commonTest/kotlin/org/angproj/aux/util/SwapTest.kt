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

class SwapTest: UtilityAware {

    private val refLong: Long = 0x3569356935693569L
    private val refULong: ULong = 0xCA96CA96CA96CA96uL

    private val refInt: Int = 0x35693569
    private val refUInt: UInt = 0xCA96CA96u

    private val refShort: Short = 0x3569
    private val refUShort: UShort = 0xCA96u

    private val refFloat: Float = Float.fromBits(refInt)
    private val refDouble: Double = Double.fromBits(refLong)

    /**
     * Testing .swapEndian() on Short.
     *
     */
    @Test
    fun shortSwapEndian() {
        assertEquals(refShort.swapEndian(), 0x6935)
        /*assertEquals(Short.MIN_VALUE.swapEndian().swapEndian(), Short.MIN_VALUE)
        assertEquals(Short.MAX_VALUE.swapEndian().swapEndian(), Short.MAX_VALUE)*/
    }

    /**
     * Testing .swapEndian() on UShort.
     *
     */
    @Test
    fun ushortSwapEndian() {
        assertEquals(refUShort.swapEndian(), 0x96CAu)
        /*assertEquals(UShort.MIN_VALUE.swapEndian().swapEndian(), UShort.MIN_VALUE)
        assertEquals(UShort.MAX_VALUE.swapEndian().swapEndian(), UShort.MAX_VALUE)*/
    }

    /**
     * Testing .swapEndian() on Char.
     *
     */
    /*@Test
    fun charSwapEndian() {
        assertEquals(refChar.swapEndian().swapEndian(), refChar)
    }*/

    /**
     * Testing .swapEndian() on Int.
     *
     */
    @Test
    fun intSwapEndian() {
        assertEquals(refInt.swapEndian(), 0x69356935)
        /*assertEquals(Int.MIN_VALUE.swapEndian().swapEndian(), Int.MIN_VALUE)
        assertEquals(Int.MAX_VALUE.swapEndian().swapEndian(), Int.MAX_VALUE)*/
    }

    /**
     * Testing .swapEndian() on UInt.
     *
     */
    @Test
    fun uintSwapEndian() {
        assertEquals(refUInt.swapEndian(), 0x96CA96CAu)
        /*assertEquals(UInt.MIN_VALUE.swapEndian().swapEndian(), UInt.MIN_VALUE)
        assertEquals(UInt.MAX_VALUE.swapEndian().swapEndian(), UInt.MAX_VALUE)*/
    }

    /**
     * Testing .swapEndian() on Long.
     *
     */
    @Test
    fun longSwapEndian() {
        assertEquals(refLong.swapEndian(), 0x6935693569356935L)
        /*assertEquals(Long.MIN_VALUE.swapEndian().swapEndian(), Long.MIN_VALUE)
        assertEquals(Long.MAX_VALUE.swapEndian().swapEndian(), Long.MAX_VALUE)*/
    }

    /**
     * Testing .swapEndian() on ULong.
     *
     */
    @Test
    fun ulongSwapEndian() {
        assertEquals(refULong.swapEndian(), 0x96CA96CA96CA96CAuL)
        /*assertEquals(ULong.MIN_VALUE.swapEndian().swapEndian(), ULong.MIN_VALUE)
        assertEquals(ULong.MAX_VALUE.swapEndian().swapEndian(), ULong.MAX_VALUE)*/
    }

    /**
     * Testing .swapEndian() on Float.
     *
     */
    @Test
    fun floatSwapEndian() {
        assertEquals(refFloat.swapEndian(), Float.fromBits(refInt.swapEndian()))
        /*assertEquals((Float.MIN_VALUE).swapEndian().swapEndian().toRawBits(), Float.MIN_VALUE.toRawBits()) // <-- Fix KN
        assertEquals((Float.MAX_VALUE).swapEndian().swapEndian().toRawBits(), Float.MAX_VALUE.toRawBits()) // <-- Fix KN/JVM */
    }

    /**
     * Testing .swapEndian() on Double.
     *
     */
    @Test
    fun doubleSwapEndian() {
        assertEquals(refDouble.swapEndian(), Double.fromBits(refLong.swapEndian()))
        /*assertEquals(Double.MIN_VALUE.swapEndian().swapEndian(), Double.MIN_VALUE)
        assertEquals(Double.MAX_VALUE.swapEndian().swapEndian().toRawBits(), Double.MAX_VALUE.toRawBits()) // <-- Fix KN/JVM */
    }
}