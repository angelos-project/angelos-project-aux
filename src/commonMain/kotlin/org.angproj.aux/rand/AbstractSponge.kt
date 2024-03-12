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
package org.angproj.aux.rand

import org.angproj.aux.util.floorMod

public abstract class AbstractSponge(spongeSize: Int = 0, protected val accessibleSize: Int = 0) {

    protected var counter: Long = 0
    protected var mask: Long = 0
    protected val sponge: LongArray = LongArray(spongeSize) { InitializationVector.entries[it].iv }

    init {
        require(accessibleSize <= spongeSize) {
            "Accessible size must be equal or less than the number of states."
        }
    }

    protected abstract fun round()

    protected fun absorb(value: Long, position: Int) {
        val offset = position.floorMod(accessibleSize)
        sponge[offset] = sponge[offset] xor value
    }

    protected fun squeeze(position: Int): Long {
        val offset = position.floorMod(accessibleSize)
        return sponge[offset] xor mask
    }

    protected fun scramble() {
        repeat(sponge.size) { round() }
    }
}