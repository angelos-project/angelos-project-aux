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

import org.angproj.aux.io.Reader
import org.angproj.aux.util.BufferSize
import org.angproj.aux.util.floorMod
import org.angproj.aux.util.writeLongAt
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
public object SecureFeed: Reader {
    private val ROUNDS_64K: Int = BufferSize._64K.size
    private val ROUNDS_128K: Int = BufferSize._128K.size

    private var counter: Long = 0
    private var entropy: Long = 0
    private var mask: Long = 0
    private var state: LongArray = LongArray(9)
    private var next: Int = 0

    init {
        entropy = SecureEntropy.readLong()
        entropy()
        next()
        repeat(9) { cycle() }
    }

    private fun next() { next = ROUNDS_128K + entropy.mod(ROUNDS_64K) }
    private fun entropy() { state[8] = -state[8].inv() xor entropy }

    private fun cycle() {
        if(counter > next) {
            entropy = SecureEntropy.readLong()
            next()
            entropy()
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

    private fun require(length: Int) {
        require(length.floorMod(64) == 0) { "Length must be divisible by 64." }
        require(length <= BufferSize._8K.size) { "Length must not surpass 8 Kilobytes."}
    }

    private fun fill(data: ByteArray) {
        (data.indices step 64).forEach { offset ->
            cycle()
            (0 until 64 step Long.SIZE_BYTES).forEach { index ->
                data.writeLongAt(offset + index, state[index / 8] xor mask) } }
    }

    override fun read(length: Int): ByteArray {
        require(length)
        return ByteArray(length).also { fill(it) }
    }

    override fun read(data: ByteArray): Int {
        require(data.size)
        fill(data)
        return data.size
    }
}