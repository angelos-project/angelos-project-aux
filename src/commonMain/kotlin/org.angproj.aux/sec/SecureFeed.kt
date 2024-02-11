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
package org.angproj.aux.sec

import org.angproj.aux.util.readLongAt
import org.angproj.aux.util.writeLongAt
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
public object SecureFeed {
    private var counter: Long = 0
    private var entropy: Long = 0
    private var mask: Long = 0
    private var state: LongArray = LongArray(9)

    private val data = ByteArray(8)

    init {
        (0 until 8).forEach { state[it] = SecureEntropy.getEntropy() }
        entropy = SecureEntropy.getEntropy()
        repeat(9) { cycle() }
    }

    private fun cycle() {
        counter++

        if(counter > 131072) {
            entropy = SecureEntropy.getEntropy()
            state[8] = state[8] xor entropy
            counter = 1
        }

        val s0 = state[0] xor state[3] xor state[6]
        val s1 = state[1] xor state[4] xor state[7]
        val s2 = state[2] xor state[5] xor state[8]

        val temp = state[0]
        state[0] = -state[1].rotateLeft(17)
        state[1] = state[2].inv().rotateLeft(2)
        state[2] = -state[3].rotateLeft(13)
        state[3] = state[4].inv().rotateLeft(7)
        state[4] = -state[5].rotateLeft(11)
        state[5] = state[6].inv().rotateLeft(5)
        state[6] = -state[7].rotateLeft(19)
        state[7] = state[8].inv().rotateLeft(3)
        state[8] = -(temp + counter).inv().rotateLeft(23)

        mask = (mask and entropy and s0 and s1 and s2) xor
                ((state[8] and state[7] and state[6] and state[5]) * 2) xor
                ((state[4] and state[3] and state[2]) * 4) xor
                ((state[1] and state[0]) * 8) xor
                (counter.inv() * 16)

        state[0] = state[0] xor s0
        state[1] = state[1] xor s0
        state[2] = state[2] xor s0
        state[3] = state[3] xor s1
        state[4] = state[4] xor s1
        state[5] = state[5] xor s1
        state[6] = state[6] xor s2
        state[7] = state[7] xor s2
        state[8] = state[8] xor s2
    }

    public fun getFeed(buf: ByteArray, offset: Int = 0) {
        require(buf.size >= 64 + offset)
        cycle()
        (0 until 8).forEach { index -> buf.writeLongAt(offset + index * 8, state[index] xor mask) }
    }
}