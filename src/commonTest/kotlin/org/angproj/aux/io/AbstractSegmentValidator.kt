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

import org.angproj.aux.buf.copyInto
import org.angproj.aux.buf.copyOfRange
import kotlin.test.assertEquals
import org.angproj.aux.util.*
import kotlin.test.assertFails
import kotlin.test.assertFailsWith

abstract class AbstractSegmentValidator {

    val arr1 = ByteArray(DataSize._128B.size) { (-it - 1).toByte() } // From -1 to -128
    val arr2 = ByteArray(DataSize._64B.size) { it.toByte() } // From 0 to 63
    val testShort: Short = 0x1221
    val testInt: Int = 0x11223344
    val testLong: Long = 0x1122334455667711

    fun <E: Segment>byteWriteReadSync(prep: (size: Int) -> E) {
        val b = arr1.copyOf()
        val m = prep(arr1.size)

        b.forEachIndexed { index, byte -> m.setByte(index, byte) }
        b.forEachIndexed { index, byte -> assertEquals(m.getByte(index), byte) }
    }

    fun <E: Segment>shortReadAsync(prep: (size: Int) -> E) {
        val b = arr1.copyOf()
        val m = prep(arr1.size)

        b.forEachIndexed { index, byte -> m.setByte(index, byte) }
        (0 until b.size-1).forEach { index-> assertEquals(m.getShort(index), b.readShortAt(index)) }
    }

    fun <E: Segment>shortWriteAsync(prep: (size: Int) -> E) {
        val b = arr1.copyOf()
        val m = prep(arr1.size)

        (0 until b.size-1).forEach { index ->
            b.forEachIndexed { jdx, byte -> m.setByte(jdx, byte) }
            val c = b.copyOf()
            c.writeShortAt(index, testShort)
            m.setShort(index, testShort)

            c.forEachIndexed { kdx, byte -> debug(b, c, m) { assertEquals(m.getByte(kdx), byte) } }
        }
    }

    fun <E: Segment>intReadAsync(prep: (size: Int) -> E) {
        val b = arr1.copyOf()
        val m = prep(arr1.size)

        b.forEachIndexed { index, byte -> m.setByte(index, byte) }
        (0 until b.size-3).forEach{ index-> assertEquals(m.getInt(index), b.readIntAt(index)) }
    }

    fun <E: Segment>intWriteAsync(prep: (size: Int) -> E) {
        val b = arr1.copyOf()
        val m = prep(arr1.size)

        (0 until b.size-3).forEach { index ->
            b.forEachIndexed { jdx, byte -> m.setByte(jdx, byte) }
            val c = b.copyOf()
            c.writeIntAt(index, testInt)
            m.setInt(index, testInt)

            c.forEachIndexed { kdx, byte -> debug(b, c, m) { assertEquals(m.getByte(kdx), byte) } }
        }
    }

    fun <E: Segment>longReadAsync(prep: (size: Int) -> E) {
        val b = arr1.copyOf()
        val m = prep(arr1.size)

        b.forEachIndexed { index, byte -> m.setByte(index, byte) }
        (0 until b.size-7).forEach{ index-> assertEquals(m.getLong(index), b.readLongAt(index)) }
    }

    fun <E: Segment>longWriteAsync(prep: (size: Int) -> E) {
        val b = arr1.copyOf()
        val m = prep(arr1.size)

        (0 until b.size-7).forEach { index ->
            b.forEachIndexed { jdx, byte -> m.setByte(jdx, byte) }
            val c = b.copyOf()
            c.writeLongAt(index, testLong)
            m.setLong(index, testLong)

            c.forEachIndexed { kdx, byte -> debug(b, c, m) { assertEquals(m.getByte(kdx), byte) } }
        }
    }

    fun debug(b: ByteArray, c: ByteArray, m: Segment, block: () -> Unit) {
        try {
            block()
        } catch (e: AssertionError) {
            print("B-ORIG: ")
            b.forEach { print(BinHex.encodeToHex(byteArrayOf(it))) }
            println()
            print("C-EDIT: ")
            c.forEach { print(BinHex.encodeToHex(byteArrayOf(it))) }
            println()
            print("M-EDIT: ")
            b.indices.forEach { print(BinHex.encodeToHex(byteArrayOf(m.getByte(it)))) }
            println()

            throw e
        }
    }

    fun <E: Segment>byteRWOutbound(prep: (size: Int) -> E) {
        val m = prep(arr1.size)

        m.getByte(0)
        assertFailsWith<IllegalArgumentException> {
            m.getByte(-1)
        }

        m.getByte(m.size-TypeSize.byte) // Won't crash
        assertFailsWith<IllegalArgumentException> {
            m.getByte(m.size) // Must throw
        }

        m.setByte(0, 1)
        assertFailsWith<IllegalArgumentException> {
            m.setByte(-1, 1)
        }

        m.setByte(m.size-TypeSize.byte, 0) // Won't crash
        assertFailsWith<IllegalArgumentException> {
            m.setByte(m.size, 1) // Must throw
        }
    }

    fun <E: Segment>shortRWOutbound(prep: (size: Int) -> E) {
        val m = prep(arr1.size)

        m.getShort(0)
        assertFailsWith<IllegalArgumentException> {
            m.getShort(-1)
        }

        m.getShort(m.size-TypeSize.short) // Won't crash
        assertFailsWith<IllegalArgumentException> {
            m.getShort(m.size-1) // Must throw
        }

        m.setShort(0, 1)
        assertFailsWith<IllegalArgumentException> {
            m.setShort(-1, 1)
        }

        m.setShort(m.size-TypeSize.short, 0) // Won't crash
        assertFailsWith<IllegalArgumentException> {
            m.setShort(m.size-1, 1) // Must throw
        }
    }

    fun <E: Segment>intRWOutbound(prep: (size: Int) -> E) {
        val m = prep(arr1.size)

        m.getInt(0)
        assertFailsWith<IllegalArgumentException> {
            m.getInt(-1)
        }

        m.getInt(m.size-TypeSize.int) // Won't crash
        assertFailsWith<IllegalArgumentException> {
            m.getInt(m.size-3) // Must throw
        }

        m.setInt(0, 1)
        assertFailsWith<IllegalArgumentException> {
            m.setInt(-1, 1)
        }

        m.setInt(m.size-TypeSize.int, 0) // Won't crash
        assertFailsWith<IllegalArgumentException> {
            m.setInt(m.size-3, 1) // Must throw
        }
    }

    fun <E: Segment>longRWOutbound(prep: (size: Int) -> E) {
        val m = prep(arr1.size)

        m.getLong(0)
        assertFailsWith<IllegalArgumentException> {
            m.getLong(-1)
        }

        m.getLong(m.size-TypeSize.long) // Won't crash
        assertFailsWith<IllegalArgumentException> {
            m.getLong(m.size-7) // Must throw
        }

        m.setLong(0, 1)
        assertFailsWith<IllegalArgumentException> {
            m.setLong(-1, 1)
        }

        m.setLong(m.size-TypeSize.long, 0) // Won't crash
        assertFailsWith<IllegalArgumentException> {
            m.setLong(m.size-7, 1) // Must throw
        }
    }

    inline fun <reified E: Segment>tryCopyInto(prep: (size: Int) -> E) {
        val seg1 = prep(arr1.size)
        val seg2 = prep(arr2.size)

        // Copy and verify that #1 reflect each other
        (0 until seg1.size).forEach { seg1.setByte(it, arr1[it]) }
        (0 until seg1.size).forEach {
            assertEquals(seg1.getByte(it), arr1[it]) }

        // Copy and verify that #2 reflect each other
        (0 until seg2.size).forEach { seg2.setByte(it, arr2[it]) }
        (0 until seg2.size).forEach {
            assertEquals(seg2.getByte(it), arr2[it]) }

        // Prove that a chunk is fully saturated as the reflecting array
        assertFails { println(arr1[seg1.size]) }

        // Copy chunk 2 into the middle of chunk 1
        seg2.copyInto(seg1, 32, 0, 64)
        arr2.copyInto(arr1, 32, 0, 64)
        arr1.indices.forEach { // Verify similarity between the two operations carried out simultaneously
            assertEquals(seg1.getByte(it), arr1[it]) }

        seg1.close()
        seg2.close()
    }

    inline fun <reified E: Segment>tryCopyOfRange(prep: (size: Int) -> E) {
        val seg1 = prep(arr1.size)
        (0 until seg1.size).forEach { seg1.setByte(it, arr1[it]) }

        val seg2 = seg1.copyOfRange(32, 96)
        val arr = arr1.copyOfRange(32, 96)

        arr.indices.forEach { // Verify similarity between the two operations carried out simultaneously
            assertEquals(seg2.getByte(it), arr[it]) }

        seg1.close()
        seg2.close()
    }

    inline fun <reified E: Segment>tryCopyOf(prep: (size: Int) -> E) {
        val seg1 = prep(arr1.size)
        (0 until seg1.size).forEach { seg1.setByte(it, arr1[it]) }

        val seg2 = seg1.copyOf()
        val arr = arr1.copyOf()

        arr.indices.forEach { // Verify similarity between the two operations carried out simultaneously
            assertEquals(seg2.getByte(it), arr[it]) }

        seg1.close()
        seg2.close()
    }
}