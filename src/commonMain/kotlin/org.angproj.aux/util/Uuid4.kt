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

import org.angproj.aux.sec.SecureEntropy
import org.angproj.aux.sec.SecureFeed
import org.angproj.aux.sec.SecureRandom

public class Uuid4 internal constructor(private val uuid: ByteArray) {

    public constructor() : this(generateByteArray())

    init {
        require(uuid.size == 16) { "Wrong size of data!" }
        require(uuid[6].toInt() and 0xf0 == 64) { "Not UUID version 4!" }
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

    internal companion object {

        private val rand = ByteArray(64)
        private var round = 4

        fun generateByteArray(): ByteArray {
            if(round == 4) {
                SecureFeed.getFeed(rand, 0)
                round = 0
            }

            val data = ByteArray(16)
            val start = round * 16
            val end = start + 16
            rand.copyInto(data, 0, start, end)
            round++

            data[6] = data[6].flipOffFlag7()
            data[6] = data[6].flipOnFlag6()
            data[6] = data[6].flipOffFlag5()
            data[6] = data[6].flipOffFlag4()

            return data
        }
    }
}