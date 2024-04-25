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

    private val createNew: (size: Int) -> ByteBuffer = { ByteBuffer(it) }

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
}