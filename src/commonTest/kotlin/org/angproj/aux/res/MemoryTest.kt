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
import org.angproj.aux.io.Segment
import org.angproj.aux.io.address
import org.angproj.aux.io.copyInto
import org.angproj.aux.io.memBinOf
import org.angproj.aux.mem.*
import org.angproj.aux.util.Uuid4
import org.angproj.aux.util.ifJvmOrNative
import org.angproj.aux.util.useWith
import kotlin.random.Random
import kotlin.test.*
import kotlin.time.Duration
import kotlin.time.measureTime

class MemoryTest {
    @Test
    fun testAllocateMemory(): Unit = ifJvmOrNative {
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
    fun testIllegalArgument(): Unit = ifJvmOrNative {
        val mem1 = allocateMemory(DataSize._128B.size)
        val mem2 = allocateMemory(DataSize._64B.size)

        // Validates that negative length fails
        assertFailsWith<IllegalArgumentException> ("Negative length is invalid.") {
            mem1.copyInto<Unit>(mem2, 0, 0, -1)
        }

        // Validates that negative start index fails
        assertFailsWith<IllegalArgumentException> ("Negative start index is invalid.") {
            mem1.copyInto<Unit>(mem2, 0, -1, 0)
        }

        // Validates that negative destination offset fails
        assertFailsWith<IllegalArgumentException> ("Negative destination offset is invalid.") {
            mem1.copyInto<Unit>(mem2, -1, 0, 0)
        }

        // Validates that end index escape fails
        assertFailsWith<IllegalArgumentException> ("End index breach is invalid.") {
            mem2.copyInto<Unit>(mem1, 0, 0 ,65)
        }

        // Validates that destination end escape fails
        assertFailsWith<IllegalArgumentException> ("Destination offset end breach is invalid.") {
            mem2.copyInto<Unit>(mem1, 64, 0 ,65)
        }

        // Validates that destination end 2 escape fails
        assertFailsWith<IllegalArgumentException> ("Destination offset end 2 breach is invalid.") {
            mem2.copyInto<Unit>(mem1, 65, 0 ,64)
        }

        mem1.dispose()
        mem2.dispose()
    }

    /*@Test
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
            assertEquals(mem1.speedByteGet<Reify>(it.toLong()), arr1[it]) } // Memory segment broken here, index 32 is not same

        mem1.dispose()
        mem2.dispose()
    }*/

    @Test
    fun testCopyInto2(): Unit = ifJvmOrNative {
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
        mem2.copyInto<Unit>(mem1, 32, 0, 64)
        arr2.copyInto(arr1, 32, 0, 64)
        arr1.indices.forEach { // Verify similarity between the two operations carried out simultaneously
            assertEquals(mem1.getByte(it), arr1[it]) } // Memory segment broken here, index 32 is not same

        mem1.dispose()
        mem2.dispose()
    }

    //@Test
    fun testSpeedMeasure() = ifJvmOrNative {
        val vol = DataSize._128M
            val mem1 = allocateMemory(vol.size)
            val mem2 = allocateMemory(vol.size)

            var time = measureTime { mem1.copyInto<Unit>(mem2, 0, 0, mem1.size) }
            println(time)

            val arr1 = ByteArray(vol.size)
            val arr2 = ByteArray(vol.size)

            time = measureTime { arr1.copyInto(arr2) }
            println(time)
    }

    private fun testRunRand(seg: Segment<*>, loops: Int): Duration = BufMgr.asWrap(seg) {
        var time = measureTime {  }
        repeat(loops) {
            time += measureTime { Uuid4.read(this@asWrap._segment) } // P128 Small Random
        }
        time / loops
    }

    //@Test
    fun testRandomMeasure() = ifJvmOrNative {
        val vol = DataSize._64M
        val iter = 128
        repeat(4) {
            val time = when(it) {
                0 -> testRunRand(MemoryFree.allocate(vol.size), iter)
                1 -> testRunRand(Default.allocate(vol.size), iter)
                2 -> testRunRand(ModelMem.allocate(vol.size), iter)
                else -> {
                    val arr1 = ByteArray(vol.size)
                    var time = measureTime {  }
                    repeat(iter) {
                        time += measureTime { Random.nextBytes(arr1) } // Stdlib Kotlin Default Random
                    }
                    time / iter
                }
            }
            println("1 Gigabyte in ${time.times(DataSize._1G.size / vol.size)}")
        }
    }

    //@Test
    fun testCopyMeasure() = ifJvmOrNative {
        val vol = DataSize._64M
        val loops = 128

        val barr1 = ByteArray(vol.size)
        val barr2 = ByteArray(vol.size)
        var time = measureTime {  }
        repeat(loops) {
            println(it)
            Random.nextBytes(barr1)
            time += measureTime { barr1.copyInto(barr2, 0, 0, barr1.size) }
            assertContentEquals(barr1, barr2)
        }
        time /= loops
        println("1 Gigabyte in ${time.times(DataSize._1G.size / vol.size)}")

        val mem1 = MemoryFree.allocate(vol.size)
        val mem2 = MemoryFree.allocate(vol.size)

        time = measureTime {  }
        repeat(loops) {
            println(it)
            Uuid4.read(mem1)
            println("Mem 1 " + mem1.getLong(0))
            time += measureTime { mem1.copyInto(mem2, 0, 0, mem1.limit) }
            //assertEquals(mem1.hashCode(), mem2.hashCode())
            println("Mem 2 " + mem2.getLong(0))
        }
        time /= loops
        println("1 Gigabyte in ${time.times(DataSize._1G.size / vol.size)}")
    }

    @Test
    fun testAddress() = ifJvmOrNative {
        memBinOf(DataSize._1K.size).useWith {
            println(it.address())
        }
    }

    /*@Test
    fun testFailAddress() = ifJvmOrNative {
        binOf(DataSize._1K.size).useWith {
            assertFailsWith<IllegalStateException> { it.address() }
        }
    }*/
}