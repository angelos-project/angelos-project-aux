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
import org.angproj.aux.io.DataSize
import org.angproj.aux.util.floorMod
import org.angproj.aux.util.readLongAt
import kotlin.native.concurrent.ThreadLocal

/**
 * Feed of secure random, revitalized with conditioned secure entropy
 * about every 4th to 12th megabyte for high quality of secure output.
 * */
@ThreadLocal
public object SecureFeed : AbstractSponge512(), Reader {
    private val ROUNDS_64K: Int = DataSize._64K.size
    private val ROUNDS_128K: Int = DataSize._128K.size

    private var next: Int = 0

    init {
        revitalize()
    }

    private fun revitalize() {
        SecureEntropy.read(SecureEntropy.byteSize).also { entropy ->
            (entropy.indices step Long.SIZE_BYTES).forEach {
                absorb(entropy.readLongAt(it), it / Long.SIZE_BYTES)
            }
        }
        scramble()
    }

    private fun cycle() {
        if (counter > next) {
            next = ROUNDS_128K + sponge[0].mod(ROUNDS_64K)
            revitalize()
            counter = 1
        }
        round()
    }

    private fun require(length: Int) {
        require(length.floorMod(byteSize) == 0) { "Length must be divisible by $byteSize." }
        require(length <= DataSize._8K.size) { "Length must not surpass 8 Kilobyte." }
    }

    override fun read(length: Int): ByteArray {
        require(length)
        return ByteArray(length).also { fill(it) { cycle() } }
    }

    override fun read(data: ByteArray): Int {
        require(data.size)
        fill(data) { cycle() }
        return data.size
    }
}