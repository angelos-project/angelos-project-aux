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

import org.angproj.aux.io.DataSize
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith

@Suppress("UNCHECKED_CAST")
abstract class AbstractBufferTypeValidator<T> {

    val _arr1 = ByteArray(DataSize._128B.size) { (-it - 1).toByte() } // From -1 to -128
    val _arr2 = ByteArray(DataSize._64B.size) { it.toByte() } // From 0 to 63

    var tSize: Int = 0
    lateinit var arr1: Array<T>
    lateinit var arr2: Array<T>

    val testByte: Byte = 0B1000_0001.toByte()
    val testShort: Short = 0x1221
    val testInt: Int = 0x11223344
    val testLong: Long = 0x1122334455667711

    fun <T, E: AbstractBufferType<T>>bufferRWOutbound(tVal: T, prep: (size: Int) -> E) {
        val m = prep(arr1.size)

        m[0]
        assertFailsWith<IllegalArgumentException> { m[-1] }

        m[m.size-1] // Won't crash
        assertFailsWith<IllegalArgumentException> { m[m.size] } // Must throw

        m[0] = tVal
        assertFailsWith<IllegalArgumentException> { m[-1] = tVal }

        m[m.size-1] = tVal // Won't crash
        assertFailsWith<IllegalArgumentException> { m[m.size] = tVal } // Must throw
    }

    fun <T, E: AbstractBufferType<T>>bufferReadWrite(tVal: T, prep: (size: Int) -> E) {
        val m = prep(arr1.size)

        (0 until m.size).forEach { m[it] = tVal }
        (0 until m.size).forEach { assertEquals(m[it], tVal) }
    }

    inline fun <T, E: AbstractBufferType<T>>tryCopyInto(prep: (size: Int) -> E) {
        val arr1 = arr1.copyOf()
        val arr2 = arr2.copyOf()
        val seg1 = prep(arr1.size)
        val seg2 = prep(arr2.size)

        // Copy and verify that #1 reflect each other
        (0 until seg1.size).forEach { seg1[it] = arr1[it] as T }
        (0 until seg1.size).forEach {
            assertEquals(seg1[it], arr1[it] as T) }

        // Copy and verify that #2 reflect each other
        (0 until seg2.size).forEach { seg2[it] = arr2[it] as T }
        (0 until seg2.size).forEach {
            assertEquals(seg2[it], arr2[it] as T) }

        // Prove that a chunk is fully saturated as the reflecting array
        assertFails { println(arr1[seg1.size]) }

        // Copy chunk 2 into the middle of chunk 1
        seg2.copyInto(seg1, 32, 0, 64)
        arr2.copyInto(arr1, 32, 0, 64)
        arr1.indices.forEach { // Verify similarity between the two operations carried out simultaneously
            assertEquals(seg1[it], arr1[it] as T) }

        seg1.close()
        seg2.close()
    }

    inline fun <T, E: AbstractBufferType<T>>tryCopyOfRange(prep: (size: Int) -> E) {
        val arr1 = arr1.copyOf()
        val seg1 = prep(arr1.size)
        (0 until seg1.size).forEach { seg1[it] = arr1[it] as T }

        val seg2 = seg1.copyOfRange(32, 96)
        val arr = arr1.copyOfRange(32, 96)

        arr.indices.forEach { // Verify similarity between the two operations carried out simultaneously
            assertEquals(seg2[it], arr[it] as T) }

        seg1.close()
        seg2.close()
    }

    inline fun <T, E: AbstractBufferType<T>>tryCopyOf(prep: (size: Int) -> E) {
        val arr1 = arr1.copyOf()
        val seg1 = prep(arr1.size)
        (0 until seg1.size).forEach { seg1[it] = arr1[it] as T }

        val seg2 = seg1.copyOf()
        val arr = arr1.copyOf()

        arr.indices.forEach { // Verify similarity between the two operations carried out simultaneously
            assertEquals(seg2[it], arr[it] as T) }

        seg1.close()
        seg2.close()
    }
}
