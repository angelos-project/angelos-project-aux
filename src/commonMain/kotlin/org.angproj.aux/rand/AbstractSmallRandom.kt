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

import org.angproj.aux.util.readLongAt

/**
 * Fast little random that is Monte Carlo proof.
 * */
public abstract class AbstractSmallRandom(iv: ByteArray = byteArrayOf()) {

    private var state: Long = InitializationVector.IV_CA35.iv
    private var state1: Long = InitializationVector.IV_CA53.iv

    init {
        when {
            iv.isEmpty() -> scramble()
            iv.size >= 16 -> reseed(iv)
            else -> error("Faulty initialization vector!")
        }
    }

    private fun scramble() {
        repeat(8) { round() }
    }

    protected fun reseed(seed: ByteArray) {
        require(seed.size == 16) { "Wrong seed size" }
        state = InitializationVector.IV_CA35.iv xor seed.readLongAt(0)
        state1 = InitializationVector.IV_CA53.iv xor seed.readLongAt(Long.SIZE_BYTES)
        scramble()
    }

    protected fun round(): Int {
        val temp = -state.inv() * 5
        state = -state1.inv() * 13
        state1 = temp.rotateLeft(32)
        return state.toInt() xor state1.toInt()
    }

}