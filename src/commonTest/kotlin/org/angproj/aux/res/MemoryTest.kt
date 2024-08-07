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
package org.angproj.aux.res

import org.angproj.aux.io.DataSize
import org.angproj.aux.util.Reify
import org.angproj.aux.util.ifNotJs
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith
import kotlin.time.measureTime

class MemoryTest {
    @Test
    fun testAllocateMemory(): Unit = ifNotJs {
        // Validates that zero length fails
        assertFailsWith<IllegalArgumentException> ("Zero length is invalid.") {
            allocateMemory(0).dispose()
        }

        // Validates that 1Gb + 1 length fails
        assertFailsWith<IllegalArgumentException> ("1 Gig+ length is invalid.") {
            allocateMemory(DataSize._1G.size + 1).dispose()
        }
    }

    @Test
    fun testIllegalArgument(): Unit = ifNotJs {
        val mem1 = allocateMemory(DataSize._128B.size)
        val mem2 = allocateMemory(DataSize._64B.size)

        // Validates that negative length fails
        assertFailsWith<IllegalArgumentException> ("Negative length is invalid.") {
            mem1.copyInto<Reify>(mem2, 0, 0, -1)
        }

        // Validates that negative start index fails
        assertFailsWith<IllegalArgumentException> ("Negative start index is invalid.") {
            mem1.copyInto<Reify>(mem2, 0, -1, 0)
        }

        // Validates that negative destination offset fails
        assertFailsWith<IllegalArgumentException> ("Negative destination offset is invalid.") {
            mem1.copyInto<Reify>(mem2, -1, 0, 0)
        }

        // Validates that end index escape fails
        assertFailsWith<IllegalArgumentException> ("End index breach is invalid.") {
            mem2.copyInto<Reify>(mem1, 0, 0 ,65)
        }

        // Validates that destination end escape fails
        assertFailsWith<IllegalArgumentException> ("Destination offset end breach is invalid.") {
            mem2.copyInto<Reify>(mem1, 64, 0 ,65)
        }

        // Validates that destination end 2 escape fails
        assertFailsWith<IllegalArgumentException> ("Destination offset end 2 breach is invalid.") {
            mem2.copyInto<Reify>(mem1, 65, 0 ,64)
        }

        mem1.dispose()
        mem2.dispose()
    }

    @Test
    fun testCopyInto(): Unit = ifNotJs {
        // Allocate two memory chunks
        val mem1 = allocateMemory(DataSize._128B.size)
        val mem2 = allocateMemory(DataSize._64B.size)
        // Allocate equal reference arrays reflecting the chunks
        val arr1 = ByteArray(mem1.size) { (-it - 1).toByte() } // From -1 to -128
        val arr2 = ByteArray(mem2.size) { it.toByte() } // From 0 to 63

        // Copy and verify that #1 reflect each other
        (0 until mem1.size).forEach { mem1.speedByteSet<Reify>(it.toLong(), arr1[it]) }
        (0 until mem1.size).forEach {
            assertEquals(mem1.speedByteGet<Reify>(it.toLong()), arr1[it]) }

        // Copy and verify that #2 reflect each other
        (0 until mem2.size).forEach { mem2.speedByteSet<Reify>(it.toLong(), arr2[it]) }
        (0 until mem2.size).forEach {
            assertEquals(mem2.speedByteGet<Reify>(it.toLong()), arr2[it]) }

        // Prove that a chunk is fully saturated as the reflecting array
        assertFails { println(arr1[mem1.size]) }

        // Copy chunk 2 into the middle of chunk 1
        mem2.copyInto<Reify>(mem1, 32, 0, 64)
        arr2.copyInto(arr1, 32, 0, 64)
        arr1.indices.forEach { // Verify similarity between the two operations carried out simultaneously
            assertEquals(mem1.speedByteGet<Reify>(it.toLong()), arr1[it]) }

        mem1.dispose()
        mem2.dispose()
    }

    @Test
    fun testCopyInto2(): Unit = ifNotJs {
        // Allocate two memory chunks
        val mem1 = allocateMemory(DataSize._128B.size)
        val mem2 = allocateMemory(DataSize._64B.size)
        // Allocate equal reference arrays reflecting the chunks
        val arr1 = ByteArray(mem1.size) { (-it - 1).toByte() } // From -1 to -128
        val arr2 = ByteArray(mem2.size) { it.toByte() } // From 0 to 63

        // Copy and verify that #1 reflect each other
        (0 until mem1.size).forEach { mem1.setByte(it, arr1[it]) }
        (0 until mem1.size).forEach {
            assertEquals(mem1.getByte(it), arr1[it]) }

        // Copy and verify that #2 reflect each other
        (0 until mem2.size).forEach { mem2.setByte(it, arr2[it]) }
        (0 until mem2.size).forEach {
            assertEquals(mem2.getByte(it), arr2[it]) }

        // Prove that a chunk is fully saturated as the reflecting array
        assertFails { println(arr1[mem1.size]) }

        // Copy chunk 2 into the middle of chunk 1
        mem2.copyInto<Reify>(mem1, 32, 0, 64)
        arr2.copyInto(arr1, 32, 0, 64)
        arr1.indices.forEach { // Verify similarity between the two operations carried out simultaneously
            assertEquals(mem1.getByte(it), arr1[it]) }

        mem1.dispose()
        mem2.dispose()
    }

    // @Test
    fun testSpeedMeasure() {
        val mem1 = allocateMemory(DataSize._128M.size)
        val mem2 = allocateMemory(DataSize._128M.size)

        var time = measureTime { mem1.copyInto<Reify>(mem2, 0, 0, mem1.size) }
        println(time)

        val arr1 = ByteArray(DataSize._128M.size)
        val arr2 = ByteArray(DataSize._128M.size)

        time = measureTime { arr1.copyInto(arr2) }
        println(time)
    }
}