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

import org.angproj.aux.util.DataBuffer
import org.angproj.aux.util.EndianAware
import org.angproj.aux.util.floorMod

public abstract class AbstractSponge(spongeSize: Int = 0, public val visibleSize: Int = 0) : EndianAware {

    protected var counter: Long = 0
    protected var mask: Long = 0
    protected val sponge: LongArray = LongArray(spongeSize) { InitializationVector.entries[it].iv }
    public val byteSize: Int = visibleSize * Long.SIZE_BYTES

    init {
        require(visibleSize <= spongeSize) {
            "Visible size must be equal or less than the number of sponge variables."
        }
    }

    protected abstract fun round()

    protected fun absorb(value: Long, position: Int) {
        val offset = position.floorMod(visibleSize)
        sponge[offset] = sponge[offset] xor value
    }

    protected abstract fun squeeze(data: DataBuffer)

    protected fun scramble() {
        repeat(sponge.size) { round() }
    }

    protected fun fill(data: ByteArray, cycle: () -> Unit) {
        val buffer = DataBuffer(data)
        repeat(data.size / byteSize) { _ ->
            squeeze(buffer)
            cycle()
        }
    }
}