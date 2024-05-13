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
package org.angproj.aux.buf

import org.angproj.aux.util.BinHex
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

abstract class AbstractBufferTypeTest {

    val testDataAtomic = "fedcba9876543210f0e1d2c3b4a596870123456789abcdef"

    val testByte: Byte = 0B1000_0001.toByte()
    val testShort: Short = 0x1221
    val testInt: Int = 0x11223344
    val testLong: Long = 0x1122334455667711

    fun <T, E: AbstractBufferType<T>>bufferRWOutbound(tVal: T, prep: (size: Int) -> E) {
        val b = BinHex.decodeToBin(testDataAtomic)
        val m = prep(b.size)

        m[0]
        assertFailsWith<IllegalArgumentException> {
            m[-1]
        }

        m[m.size-1] // Won't crash
        assertFailsWith<IllegalArgumentException> {
            m[m.size] // Must throw
        }

        m[0] = tVal
        assertFailsWith<IllegalArgumentException> {
            m[-1] = tVal
        }

        m[m.size-1] = tVal // Won't crash
        assertFailsWith<IllegalArgumentException> {
            m[m.size] = tVal // Must throw
        }
    }

    fun <T, E: AbstractBufferType<T>>bufferReadWrite(tVal: T, prep: (size: Int) -> E) {
        val m = prep(16)

        (0 until m.size).forEach { m[it] = tVal }
        (0 until m.size).forEach { assertEquals(m[it], tVal) }
    }

    inline fun <T, C: List<T>, reified E: AbstractBufferType<T>>tryCopyOfRange(prep: (size: Int) -> E, comp: (size: Int) -> C) {
        /*val a = comp(16)
        val m = prep(16)


        (0 until m.size).forEach { m[it] = a[it] }
        (0 until m.size).forEach { assertEquals(m[it], a[it]) }


        (0 until 8).forEach { from ->
            val c = m.copyOfRange(from, 16)
            (0 until c.size).forEach {
                assertEquals(c[it], m[from + it]) }
        }


        (8 until 16).forEach { from ->
            val c = m.copyOfRange(0, from)
            (0 until c.size).forEach { assertEquals(c[it], m[it]) }
        }


        (0 until 8).forEach { from ->
            val c = m.copyOfRange(from, from + 8)
            (0 until c.size).forEach { assertEquals(c[it], m[from + it]) }
        }

        val c0 = m.copyOfRange(1, m.size-1)


        (0 until c0.size).forEach {
            assertEquals(c0[it], m[1 + it])
        }


        val c1 = c0.copyOfRange(1, c0.size-1)

        (0 until c1.size).forEach {
            assertEquals(c1[it], c0[1 + it])
        }*/
    }
}
