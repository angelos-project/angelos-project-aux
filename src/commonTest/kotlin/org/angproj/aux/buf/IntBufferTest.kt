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
import org.angproj.aux.util.readIntAt
import kotlin.random.Random
import kotlin.test.Test
import kotlin.time.measureTime

class IntBufferTest: AbstractBufferTypeValidator<Int>() {

    init {
        tSize = TypeSize.int
        arr1 = Array(_arr1.size / tSize) { _arr1.readIntAt(it * tSize) }
        arr2 = Array(_arr2.size / tSize) { _arr2.readIntAt(it * tSize) }
    }

    //@Test
    fun measureReadWriteSpeed() {
        val size = DataSize._16M.size / TypeSize.INT.size + if(Random.nextBits(1) == 0) TypeSize.long else 0
        var testVal = Random.nextInt()
        val array = IntArray(size)
        val buffer = IntBuffer(size)

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

    val createNew: (size: Int) -> IntBuffer = { IntBuffer(it) }
    val createComparison: (size: Int) -> Array<Int> = { Array(it) { Random.nextInt() } }


    @Test
    fun testBufferRWOutbound() = bufferRWOutbound(testInt, createNew)

    @Test
    fun testBufferReadWrite() = bufferReadWrite(testInt, createNew)

    @Test
    fun testTryCopyInto() = tryCopyInto(createNew)

    @Test
    fun testTryCopyOfRange() = tryCopyOfRange(createNew)

    @Test
    fun testTryCopyOf() = tryCopyOf(createNew)
}