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

import org.angproj.aux.util.epochEntropy
import org.angproj.aux.util.readLongAt
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
public object SecureEntropy {

    private var entropy: Long = 0xFFF73E99668196E9uL.toLong()
    private var counter: Long = 0xFFFF7D5BF9259763uL.toLong()

    private fun cycle(): Long {
        val (timestamp, nanos) = epochEntropy()
        counter++
        entropy = ((-entropy.inv() xor timestamp) * 3) xor ((-entropy.inv() xor nanos) * 5) * -counter.inv()
        return entropy
    }

    public fun getEntropy(entropy: ByteArray) {
        require(entropy.size <= 1024)
        (0..entropy.lastIndex).forEach { entropy[it] = cycle().toByte() }
    }

    public fun getEntropy(): Long = cycle()
}