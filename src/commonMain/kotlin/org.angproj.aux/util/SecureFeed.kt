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
package org.angproj.aux.util

import kotlin.math.max

public object SecureFeed {
    private var counter: Long = 0
    private var entropy: Long = 0
    private var mask: Long = 0
    private var state: LongArray = LongArray(8)

    init {
        val start = ByteArray(64)
        SecureEntropy.getEntropy(start)
        (0..15).forEach { state[it] = start.readLongAt(it * 8) }
        repeat(8) { cycle() }
    }

    private fun cycle() {
        counter = max(1, counter + 1)
        if(counter.floorMod(8192) == 1L) {
            val data = ByteArray(8)
            SecureEntropy.getEntropy(data)
            entropy = data.readLongAt(0)
        }

        val s0 = state[0] xor state[2] xor state[5]
        val s1 = state[1] xor state[3] xor state[6]

        state[0] = state[0] and entropy * counter
        state[1] = state[1] and entropy.inv() * counter

        val temp = state[0]
        state[0] = -state[1].rotateLeft(2) * counter
        state[1] = state[3].inv().rotateLeft(3) * counter
        state[3] = -state[4].rotateLeft(5) * counter
        state[4] = state[7].inv().rotateLeft(7) * counter
        state[7] = -state[6].rotateLeft(11) * counter
        state[6] = state[5].inv().rotateLeft(13) * counter
        state[5] = -state[2].rotateLeft(17) * counter
        state[2] = temp.inv().rotateLeft(19) * counter

        mask = (mask and entropy and state[0] and state[1]) xor
                ((state[2] and state[3] and state[4]) * 2) xor
                ((state[5] and state[6]) * 4) xor
                (state[7] * 8)

        state[2] = state[2] xor s0
        state[3] = state[3] xor s0
        state[4] = state[4] xor s0
        state[5] = state[5] xor s1
        state[6] = state[6] xor s1
        state[7] = state[7] xor s1
    }

    public fun getFeed(buf: ByteArray, offset: Int = 0) {
        require(buf.size >= 64 + offset)
        cycle()
        state.forEachIndexed { index, l -> buf.writeLongAt(offset + index * 8, l xor mask) }
    }
}