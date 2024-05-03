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
import kotlin.random.nextUInt
import kotlin.test.Test
import kotlin.time.measureTime

@OptIn(ExperimentalUnsignedTypes::class)
class UIntBufferTest: AbstractBufferTypeTest() {

    @OptIn(ExperimentalUnsignedTypes::class)
    //@Test
    fun measureReadWriteSpeed() {
        val size = DataSize._16M.size / TypeSize.U_INT.size + if(Random.nextBits(1) == 0) TypeSize.long else 0
        var testVal = Random.nextUInt()
        val array = UIntArray(size)
        val buffer = UIntBuffer(size)

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

    val createNew: (size: Int) -> UIntBuffer = { UIntBuffer(it) }
    val createComparison: (size: Int) -> List<UInt> = { UIntArray(it) { Random.nextInt().toUInt() }.toList() }

    @Test
    fun testBufferRWOutbound() = bufferRWOutbound(testInt.toUInt(), createNew)

    @Test
    fun testBufferReadWrite() = bufferReadWrite(testInt.toUInt(), createNew)

    @Test
    fun testTryCopyOfRange() = tryCopyOfRange(createNew, createComparison)
}