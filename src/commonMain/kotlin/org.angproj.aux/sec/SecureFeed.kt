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
import org.angproj.aux.rand.AbstractSponge512
import org.angproj.aux.util.BufferSize
import org.angproj.aux.util.DataBuffer
import org.angproj.aux.util.floorMod
import org.angproj.aux.util.readLongAt
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
public object SecureFeed : AbstractSponge512(), Reader {
    private val ROUNDS_64K: Int = BufferSize._64K.size
    private val ROUNDS_128K: Int = BufferSize._128K.size

    private var next: Int = 0

    init {
        revitalize()
        scramble()
    }

    private fun revitalize() {
        SecureEntropy.read(32).also { entropy ->
            (entropy.indices step Long.SIZE_BYTES).forEach {
                absorb(entropy.readLongAt(it), it / Long.SIZE_BYTES)
            }
        }
    }

    private fun cycle() {
        if (counter > next) {
            next = ROUNDS_128K + state[0].mod(ROUNDS_64K)
            revitalize()
            counter = 1
        }
        round()
    }

    private fun require(length: Int) {
        require(length.floorMod(64) == 0) { "Length must be divisible by 54." }
        require(length <= BufferSize._8K.size) { "Length must not surpass 8 Kilobyte." }
    }

    private fun fill(data: ByteArray) {
        val buffer = DataBuffer(data)
        repeat(data.size / 64) { _ ->
            repeat(accessibleSize) { pos ->
                buffer.writeLong(squeeze(pos)) }
            cycle()
        }
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