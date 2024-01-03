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

public class EntropyRandom: AbstractBufferedRandom() {

    public val identifier: String
        get() = name

    override fun intialize() {
        instantiated = true
    }

    override fun initialize() {
        TODO("Not yet implemented")
    }

    override fun finalize() {
        instantiated = false
    }

    override fun getRawLong(): Long = epochEntropy()

    public companion object {
        public const val name: String = "EntropyRandom-SystemClock"
    }
}
