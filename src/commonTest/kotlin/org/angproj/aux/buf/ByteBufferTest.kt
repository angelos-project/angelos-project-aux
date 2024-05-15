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

class ByteBufferTest: AbstractBufferTypeValidator<Byte>() {

    init {
        tSize = TypeSize.byte
        arr1 = Array(_arr1.size / Byte.SIZE_BYTES) { _arr1.get(it) }
        arr2 = Array(_arr2.size / Byte.SIZE_BYTES) { _arr2.get(it) }
    }

    //@Test
    fun measureReadWriteSpeed() {
        val size = DataSize._16M.size / TypeSize.BYTE.size + if(Random.nextBits(1) == 0) TypeSize.long else 0
        var testVal = Random.nextInt().toByte()
        val array = ByteArray(size)
        val buffer = ByteBuffer(size)

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

    val createNew: (size: Int) -> ByteBuffer = { ByteBuffer(it / Byte.SIZE_BYTES) }
    val createComparison: (size: Int) -> List<Byte> = { ByteArray(it) { Random.nextInt().toByte() }.toList() }

    @Test
    fun testBufferRWOutbound() = bufferRWOutbound(testByte, createNew)

    @Test
    fun testBufferReadWrite() = bufferReadWrite(testByte, createNew)

    @Test
    fun testTryCopyInto() = tryCopyInto(createNew)

    @Test
    fun testTryCopyOfRange() = tryCopyOfRange(createNew)

    @Test
    fun testTryCopyOf() = tryCopyOf(createNew)
}