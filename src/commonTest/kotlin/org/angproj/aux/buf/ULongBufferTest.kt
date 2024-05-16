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
import org.angproj.aux.util.readULongAt
import kotlin.random.Random
import kotlin.random.nextULong
import kotlin.test.Test
import kotlin.time.measureTime

@OptIn(ExperimentalUnsignedTypes::class)
class ULongBufferTest: AbstractBufferTypeValidator<ULong>() {

    init {
        tSize = TypeSize.uLong
        arr1 = Array(_arr1.size / tSize) { _arr1.readULongAt(it * tSize) }
        arr2 = Array(_arr2.size / tSize) { _arr2.readULongAt(it * tSize) }
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    //@Test
    fun measureReadWriteSpeed() {
        val size = DataSize._16M.size / TypeSize.U_LONG.size + if(Random.nextBits(1) == 0) TypeSize.long else 0
        var testVal = Random.nextULong()
        val array = ULongArray(size)
        val buffer = ULongBuffer(size)

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

    val createNew: (size: Int) -> ULongBuffer = { ULongBuffer(it) }
    val createComparison: (size: Int) -> Array<ULong> = { Array(it) { Random.nextLong().toULong() } }


    @Test
    fun testBufferRWOutbound() = bufferRWOutbound(testLong.toULong(), createNew)

    @Test
    fun testBufferReadWrite() = bufferReadWrite(testLong.toULong(), createNew)

    @Test
    fun testTryCopyInto() = tryCopyInto(createNew)

    @Test
    fun testTryCopyOfRange() = tryCopyOfRange(createNew)

    @Test
    fun testTryCopyOf() = tryCopyOf(createNew)
}