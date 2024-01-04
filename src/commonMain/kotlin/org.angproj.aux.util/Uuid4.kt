/**
 * Copyright (c) 2023 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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
package org.angproj.aux.util

import kotlin.math.max
import kotlin.native.concurrent.ThreadLocal

public class Uuid4 internal constructor() {

    private val uuid: ByteArray = generateByteArray().also {
        // Modifying data to make it a version 4 UUID
        it[6] = it[6].flipOffFlag7()
        it[6] = it[6].flipOnFlag6()
        it[6] = it[6].flipOffFlag5()
        it[6] = it[6].flipOffFlag4()
    }

    private val hex: String by lazy {
        val _1 = BinHex.encodeToHex(uuid.sliceArray(0 until 4))
        val _2 = BinHex.encodeToHex(uuid.sliceArray(4 until 6))
        val _3 = BinHex.encodeToHex(uuid.sliceArray(6 until 8))
        val _4 = BinHex.encodeToHex(uuid.sliceArray(8 until 10))
        val _5 = BinHex.encodeToHex(uuid.sliceArray(10 until 16))
        "$_1-$_2-$_3-$_4-$_5"
    }

    public fun toByteArray(): ByteArray = uuid.copyOf()

    override fun toString(): String = hex

    @ThreadLocal
    internal companion object {

        private var seed: Long = 0xFFF73E99668196E9uL.toLong()
        private var counter: Long = 1
        private var entropy: Long = 0

        private fun epoch() {
            val epoch = epochEntropy()
            entropy = -(epoch.first + epoch.second).rotateRight(19) xor
                    (epoch.first - epoch.second).inv().rotateLeft(19) * counter
        }

        private fun reseed(hash: Int) {
            seed = -(seed - hash).rotateRight(53) xor
                    (seed + hash).inv().rotateLeft(53) * -counter
            counter = max(1, counter + 1)
        }

        fun generateLong(): Long {
            epoch()
            val value = -(seed + entropy).rotateRight(2) xor
                    (seed - entropy).inv().rotateLeft(17) * counter
            reseed(value.hashCode())
            return value
        }

        fun generateByteArray(): ByteArray {
            epoch()
            val data = longArrayOf(
                -(seed + entropy).rotateRight(2) xor
                        (seed - entropy).inv().rotateLeft(17),
                -(seed - entropy).rotateRight(2) xor
                        (seed + entropy).inv().rotateLeft(17),
            ).toByteArray()
            reseed(data.hashCode())

            return data
        }
    }
}