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

import org.angproj.aux.util.writeLongAt
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
public object SecureFeed {
    private var counter: Long = -26
    private var entropy: Long = 0
    private var mask: Long = 0
    private var state: LongArray = LongArray(9)

    init {
        repeat(27) { cycle() }
        entropy = SecureEntropy.getEntropy()
    }

    private fun cycle() {
        if(counter > 131072) {
            entropy = SecureEntropy.getEntropy()
            counter = 1
        }

        val s0 = state[0] xor state[3] xor state[6]
        val s1 = state[1] xor state[4] xor state[7]
        val s2 = state[2] xor state[5] xor state[8]

        val temp = -state[0].inv() * 3
        state[0] = -state[1].inv() * 5
        state[1] = -state[2].inv() * 7
        state[2] = -state[3].inv() * 11
        state[3] = -state[4].inv() * 13
        state[4] = -state[5].inv() * 17
        state[5] = -state[6].inv() * 19
        state[6] = -state[7].inv() * 23
        state[7] = -state[8].inv() * 29
        state[8] = temp

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

        counter++
    }

    public fun getFeed(buf: ByteArray, offset: Int = 0) {
        require(buf.size >= 64 + offset)
        cycle()
        (0 until 8).forEach { index -> buf.writeLongAt(offset + index * 8, state[index] xor mask) }
    }
}