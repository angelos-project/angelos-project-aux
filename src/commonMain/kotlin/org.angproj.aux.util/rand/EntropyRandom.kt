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
package org.angproj.aux.util.rand

import org.angproj.aux.util.epochEntropy
import kotlin.math.max

public class EntropyRandom: AbstractBufferedRandom() {

    private var counter: Long = 0

    override val identifier: String
        get() = name

    override fun initialize() {
        _instantiated = true
    }

    override fun finalize() {
        _instantiated = false
    }

    override fun getRawLong(): Long {
        counter = max(1, counter + 1)
        val (timestamp, nanos) = epochEntropy()
        return -(timestamp + nanos + 1).rotateRight(53) xor
                (timestamp - nanos - 1).inv().rotateLeft(53) * counter
    }

    public companion object {
        public const val name: String = "EntropyRandom-SystemClock"
    }
}
