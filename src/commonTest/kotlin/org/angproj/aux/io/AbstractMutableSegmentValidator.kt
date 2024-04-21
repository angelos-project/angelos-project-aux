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

abstract class AbstractMutableSegmentValidator {

    val testDataAtomic = "fedcba9876543210f0e1d2c3b4a596870123456789abcdef"

    val testByte: Byte = 0B1000_0001.toByte()
    val testShort: Short = 0x1221
    val testInt: Int = 0x11223344
    val testLong: Long = 0x1122334455667711

    fun <E: MutableSegment>byteWriteReadSync(prep: (size: Int) -> E) {
        val b = BinHex.decodeToBin(testDataAtomic)
        val m = prep(b.size)

        b.forEachIndexed { index, byte -> m.setByte(index, byte) }
        b.forEachIndexed { index, byte -> assertEquals(m.getByte(index), byte) }
    }

    fun <E: MutableSegment>shortReadAsync(prep: (size: Int) -> E) {
        val b = BinHex.decodeToBin(testDataAtomic)
        val m = prep(b.size)

        b.forEachIndexed { index, byte -> m.setByte(index, byte) }
        (0 until b.size-1).forEach { index-> assertEquals(m.getShort(index), b.readShortAt(index)) }
    }

    fun <E: MutableSegment>shortWriteAsync(prep: (size: Int) -> E) {
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

    fun <E: MutableSegment>intReadAsync(prep: (size: Int) -> E) {
        val b = BinHex.decodeToBin(testDataAtomic)
        val m = prep(b.size)

        b.forEachIndexed { index, byte -> m.setByte(index, byte) }
        (0 until b.size-3).forEach{ index-> assertEquals(m.getInt(index), b.readIntAt(index)) }
    }

    fun <E: MutableSegment>intWriteAsync(prep: (size: Int) -> E) {
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

    fun <E: MutableSegment>longReadAsync(prep: (size: Int) -> E) {
        val b = BinHex.decodeToBin(testDataAtomic)
        val m = prep(b.size)

        b.forEachIndexed { index, byte -> m.setByte(index, byte) }
        (0 until b.size-7).forEach{ index-> assertEquals(m.getLong(index), b.readLongAt(index)) }
    }

    fun <E: MutableSegment>longWriteAsync(prep: (size: Int) -> E) {
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

    fun debug(b: ByteArray, c: ByteArray, m: MutableSegment, block: () -> Unit) {
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
}