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

import kotlin.random.Random as KotlinRandom

public abstract class AbstractRandom(private var seed: Long) : KotlinRandom()  {
    override fun nextBits(bitCount: Int): Int {
        seed = -seed.rotateRight(2) xor (seed+1).inv().rotateLeft(17)
        return (seed and (0xffffffff shl bitCount).inv()).toInt()
    }
}

public class SimpleRandom(seed: Long) : AbstractRandom(seed)

public object Random: AbstractRandom(Nonce.getNonce(true).readLongAt(0)) {
    public fun getRandom(seed: Long = Random.nextLong()): SimpleRandom = SimpleRandom(seed)
}