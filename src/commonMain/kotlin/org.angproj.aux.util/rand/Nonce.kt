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
package org.angproj.aux.util.rand

import org.angproj.aux.util.Epoch
import kotlin.jvm.JvmStatic
import kotlin.math.max
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
public object Nonce {
    private var counter: Long = 1
    private var reseed: Long = Epoch.getEpochMilliSecs()
    private var entropy: Long = Epoch.entropy()
    private var nlfMask: Long = 0
    private var s0: Long = 0
    private var s1: Long = 0
    private var s2: Long = 0
    private var s3: Long = 0

    init {
        repeat(16) { round() }
    }

    private fun round() {

        if(counter.mod(10_000) == 0) {
            val seed = Epoch.getEpochMilliSecs()
            if(seed != reseed) {
                entropy = -(Epoch.entropy() - seed).rotateRight(2) xor
                        (entropy + reseed).inv().rotateLeft(17) * counter
                reseed = seed
            }
        }

        nlfMask = (entropy and nlfMask and s0) xor ((s1 and s2) * 2) xor (s3 * 4)

        val temp = -(s1 - entropy).rotateRight(2) xor (s1 + counter).inv().rotateLeft(17)
        s1 = -(s2 + entropy).rotateRight(2) xor (s2 - counter).inv().rotateLeft(17)
        s2 = -(s3 - counter).rotateRight(2) xor (s3 + entropy).inv().rotateLeft(17)
        s3 = -(s0 + counter).rotateRight(2) xor (s0 - entropy).inv().rotateLeft(17)
        s0 = temp

        counter = max(1, counter + 1)
    }

    @JvmStatic
    public fun someEntropy(): LongArray {
        round()
        return longArrayOf(s0 xor nlfMask, s1 xor nlfMask, s2 xor nlfMask, s3 xor nlfMask)
    }

    @JvmStatic
    public fun someEntropy(entropy: ByteArray) {
        entropy.indices.forEach {
            entropy[it] = (when(it.mod(4)) {
                0 -> {
                    round()
                    s0
                }
                1 -> s1
                2 -> s2
                3 -> s3
                else -> 0
            } xor nlfMask).toByte()
        }
    }
}