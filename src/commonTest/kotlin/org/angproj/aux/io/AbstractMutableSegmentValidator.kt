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

import kotlin.test.assertEquals
import org.angproj.aux.util.*
import kotlin.random.Random
import kotlin.test.assertFailsWith

abstract class AbstractMutableSegmentValidator {

    val testDataAtomic = "fedcba9876543210f0e1d2c3b4a596870123456789abcdef"

    val testByte: Byte = 0B1000_0001.toByte()
    val testShort: Short = 0x1221
    val testInt: Int = 0x11223344
    val testLong: Long = 0x1122334455667711

    fun <E: Segment>byteWriteReadSync(prep: (size: Int) -> E) {
        val b = BinHex.decodeToBin(testDataAtomic)
        val m = prep(b.size)

        b.forEachIndexed { index, byte -> m.setByte(index, byte) }
        b.forEachIndexed { index, byte -> assertEquals(m.getByte(index), byte) }
    }

    fun <E: Segment>shortReadAsync(prep: (size: Int) -> E) {
        val b = BinHex.decodeToBin(testDataAtomic)
        val m = prep(b.size)

        b.forEachIndexed { index, byte -> m.setByte(index, byte) }
        (0 until b.size-1).forEach { index-> assertEquals(m.getShort(index), b.readShortAt(index)) }
    }

    fun <E: Segment>shortWriteAsync(prep: (size: Int) -> E) {
        val b = BinHex.decodeToBin(testDataAtomic)
        val m = prep(b.size)

        (0 until b.size-1).forEach { index ->
            b.forEachIndexed { jdx, byte -> m.setByte(jdx, byte) }
            val c = b.copyOf()
            c.writeShortAt(index, testShort)
            m.setShort(index, testShort)

            c.forEachIndexed { kdx, byte -> debug(b, c, m) { assertEquals(m.getByte(kdx), byte) } }
        }
    }

    fun <E: Segment>intReadAsync(prep: (size: Int) -> E) {
        val b = BinHex.decodeToBin(testDataAtomic)
        val m = prep(b.size)

        b.forEachIndexed { index, byte -> m.setByte(index, byte) }
        (0 until b.size-3).forEach{ index-> assertEquals(m.getInt(index), b.readIntAt(index)) }
    }

    fun <E: Segment>intWriteAsync(prep: (size: Int) -> E) {
        val b = BinHex.decodeToBin(testDataAtomic)
        val m = prep(b.size)

        (0 until b.size-3).forEach { index ->
            b.forEachIndexed { jdx, byte -> m.setByte(jdx, byte) }
            val c = b.copyOf()
            c.writeIntAt(index, testInt)
            m.setInt(index, testInt)

            c.forEachIndexed { kdx, byte -> debug(b, c, m) { assertEquals(m.getByte(kdx), byte) } }
        }
    }

    fun <E: Segment>longReadAsync(prep: (size: Int) -> E) {
        val b = BinHex.decodeToBin(testDataAtomic)
        val m = prep(b.size)

        b.forEachIndexed { index, byte -> m.setByte(index, byte) }
        (0 until b.size-7).forEach{ index-> assertEquals(m.getLong(index), b.readLongAt(index)) }
    }

    fun <E: Segment>longWriteAsync(prep: (size: Int) -> E) {
        val b = BinHex.decodeToBin(testDataAtomic)
        val m = prep(b.size)

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
        val b = BinHex.decodeToBin(testDataAtomic)
        val m = prep(b.size)

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
        val b = BinHex.decodeToBin(testDataAtomic)
        val m = prep(b.size)

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
        val b = BinHex.decodeToBin(testDataAtomic)
        val m = prep(b.size)

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
        val b = BinHex.decodeToBin(testDataAtomic)
        val m = prep(b.size)

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

    inline fun <reified E: Segment>tryCopyOfRange(prep: (size: Int) -> E) {
        val a = ByteArray(16)
        val m = prep(16)
        Random.nextBytes(a)

        println("TEST 1")
        (0 until m.size).forEach { m[it] = a[it] }
        (0 until m.size).forEach { assertEquals(m[it], a[it]) }

        println("TEST 2")
        (0 until 8).forEach { from ->
            val c = m.copyOfRange(from, 16)
            (0 until c.size).forEach {
                assertEquals(c[it], m[from + it]) }
        }

        println("TEST 3")
        (8 until 16).forEach { from ->
            val c = m.copyOfRange(0, from)
            (0 until c.size).forEach { assertEquals(c[it], m[it]) }
        }

        println("TEST 4")
        (0 until 8).forEach { from ->
            val c = m.copyOfRange(from, from + 8)
            (0 until c.size).forEach { assertEquals(c[it], m[from + it]) }
        }

        val c0 = m.copyOfRange(1, m.size-1)

        println("TEST 5")
        (0 until c0.size).forEach {
            assertEquals(c0[it], m[1 + it])
        }

        val c1 = c0.copyOfRange(1, c0.size-1)

        println("TEST 6")
        (0 until c1.size).forEach {
            assertEquals(c1[it], c0[1 + it])
        }
    }
}