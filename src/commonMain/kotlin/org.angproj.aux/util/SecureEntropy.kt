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

import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
public object SecureEntropy {

    private var entropy: Long = 0xFFF73E99668196E9uL.toLong()
    private var counter: Long = 1

    init {
        val start = ByteArray(16) { cycle().toByte() }
        entropy = start.readLongAt(0)
        counter = start.readLongAt(8).absoluteValue
    }

    private fun cycle(): Long {
        counter = max(1, counter + 1)
        val (timestamp, nanos) = epochEntropy()
        entropy = -(entropy + timestamp).rotateRight(53) xor
                (entropy - nanos).inv().rotateLeft(53) * -counter
        return entropy
    }

    public fun getEntropy(entropy: ByteArray) {
        require(entropy.size <= 1024)
        (0..entropy.lastIndex).forEach { entropy[it] = cycle().toByte() }
    }
}