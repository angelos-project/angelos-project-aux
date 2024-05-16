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
import kotlin.time.measureTime

@OptIn(ExperimentalUnsignedTypes::class)
class UByteBufferTest: AbstractBufferTypeValidator<UByte>() {

    init {
        tSize = TypeSize.uByte
        arr1 = Array(_arr1.size / tSize) { _arr1.get(it * tSize).toUByte() }
        arr2 = Array(_arr2.size / tSize) { _arr2.get(it * tSize).toUByte() }
    }

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

    val createNew: (size: Int) -> UByteBuffer = { UByteBuffer(it) }
    val createComparison: (size: Int) -> Array<UByte> = { Array(it) { Random.nextInt().toUByte() } }

    @Test
    fun testBufferRWOutbound() = bufferRWOutbound(testByte.toUByte(), createNew)

    @Test
    fun testBufferReadWrite() = bufferReadWrite(testByte.toUByte(), createNew)

    @Test
    fun testTryCopyInto() = tryCopyInto(createNew)

    @Test
    fun testTryCopyOfRange() = tryCopyOfRange(createNew)

    @Test
    fun testTryCopyOf() = tryCopyOf(createNew)
}