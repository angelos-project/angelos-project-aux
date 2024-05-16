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
import org.angproj.aux.util.readLongAt
import kotlin.random.Random
import kotlin.test.Test
import kotlin.time.measureTime

class LongBufferTest: AbstractBufferTypeValidator<Long>() {

    init {
        tSize = TypeSize.long
        arr1 = Array(_arr1.size / tSize) { _arr1.readLongAt(it * tSize) }
        arr2 = Array(_arr2.size / tSize) { _arr2.readLongAt(it * tSize) }
    }

    //@Test
    fun measureReadWriteSpeed() {
        val size = DataSize._16M.size / TypeSize.LONG.size + if(Random.nextBits(1) == 0) TypeSize.long else 0
        var testVal = Random.nextLong()
        val array = LongArray(size)
        val buffer = LongBuffer(size)

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

    val createNew: (size: Int) -> LongBuffer = { LongBuffer(it) }
    val createComparison: (size: Int) -> Array<Long> = { Array(it) { Random.nextLong() } }


    @Test
    fun testBufferRWOutbound() = bufferRWOutbound(testLong, createNew)

    @Test
    fun testBufferReadWrite() = bufferReadWrite(testLong, createNew)

    @Test
    fun testTryCopyInto() = tryCopyInto(createNew)

    @Test
    fun testTryCopyOfRange() = tryCopyOfRange(createNew)

    @Test
    fun testTryCopyOf() = tryCopyOf(createNew)
}