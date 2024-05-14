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
import org.angproj.aux.io.TypeSize
import org.angproj.aux.util.KotlinPlatformVariant
import org.angproj.aux.util.getVariant
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.time.measureTime

@OptIn(ExperimentalUnsignedTypes::class)
class UByteBufferTest: AbstractBufferTypeTest() {

    @OptIn(ExperimentalUnsignedTypes::class)
    //@Test
    fun measureReadWriteSpeed() {
        val size = DataSize._16M.size / TypeSize.U_BYTE.size + if(Random.nextBits(1) == 0) TypeSize.long else 0
        var testVal = Random.nextInt().toUByte()
        val array = UByteArray(size)
        val buffer = UByteBuffer(size)

        var idx = 0
        val writeTimeArray = measureTime {
            while(idx < size) {
                array[idx++] = testVal
            }
        }

        idx = 0
        val readTimeArray = measureTime {
            while(idx < size) {
                testVal = array[idx++]
            }
        }

        idx = 0
        val writeTimeBuffer = measureTime {
            while(idx < size) {
                buffer[idx++] = testVal
            }
        }

        idx = 0
        val readTimeBuffer = measureTime {
            while(idx < size) {
                testVal = buffer[idx++]
            }
        }

        println("Kotlin variant: ${KotlinPlatformVariant.getVariant()}")

        println("Reading time:")
        println("Array: $readTimeArray")
        println("Buffer: $readTimeBuffer")

        println("Writing time:")
        println("Array: $writeTimeArray")
        println("Buffer: $writeTimeBuffer")
    }

    val createNew: (size: Int) -> UByteBuffer = { UByteBuffer(it/UByte.SIZE_BYTES) }
    val createComparison: (size: Int) -> Array<UByte> = { Array(it/UByte.SIZE_BYTES) { Random.nextInt().toUByte() } }


    @Test
    fun testBufferRWOutbound() = bufferRWOutbound(testByte.toUByte(), createNew)

    @Test
    fun testBufferReadWrite() = bufferReadWrite(testByte.toUByte(), createNew)

    @Test
    fun testTryCopyInto() {
        val seg1 = createNew(arr1.size)
        val seg2 = createNew(arr2.size)
        val narr1 = createComparison(arr1.size)
        val narr2 = createComparison(arr2.size)


        // Copy and verify that #1 reflect each other
        (0 until seg1.size).forEach { seg1.set(it, narr1.get(it)) }
        (0 until seg1.size).forEach {
            assertEquals(seg1.get(it), narr1.get(it)) }

        // Copy and verify that #2 reflect each other
        (0 until seg2.size).forEach { seg2.set(it, narr2.get(it)) }
        (0 until seg2.size).forEach {
            assertEquals(seg2.get(it), narr2.get(it)) }

        // Prove that a chunk is fully saturated as the reflecting array
        assertFails { println(narr1[seg1.size]) }

        // Copy chunk 2 into the middle of chunk 1
        seg2.copyInto(seg1, seg1.size / 4, 0, seg2.size)
        narr2.copyInto(narr1, narr1.size / 4, 0, narr2.size)
        narr1.indices.forEach { // Verify similarity between the two operations carried out simultaneously
            assertEquals(seg1.get(it), narr1.get(it)) }

        seg1.close()
        seg2.close()
    }
}