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
import org.angproj.aux.io.SizeMode
import org.angproj.aux.rand.AbstractSponge256
import org.angproj.aux.io.DataSize
import org.angproj.aux.util.epochEntropy
import org.angproj.aux.util.floorMod
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
public object SecureEntropy : AbstractSponge256(), Reader {

    init {
        revitalize()
    }

    private fun revitalize() {
        val (timestamp, nanos) = epochEntropy()
        absorb(timestamp, 0)
        absorb(nanos, 1)
        scramble()
    }

    private fun require(length: Int) {
        require(length.floorMod(byteSize) == 0) { "Length must be divisible by $byteSize." }
        require(length <= DataSize._1K.size) { "Length must not surpass 1 Kilobyte." }
    }

    override fun read(length: Int): ByteArray {
        require(length)
        return ByteArray(length).also { fill(it) { round() } }
    }

    override fun read(data: ByteArray): Int {
        require(data.size)
        fill(data) { round() }
        return data.size
    }
}