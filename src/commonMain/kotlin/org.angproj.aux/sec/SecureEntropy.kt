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
import org.angproj.aux.rand.AbstractSponge256
import org.angproj.aux.util.BufferSize
import org.angproj.aux.util.DataBuffer
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
        require(length.floorMod(32) == 0) { "Length must be divisible by 32." }
        require(length <= BufferSize._1K.size) { "Length must not surpass 1 Kilobyte." }
    }

    private fun fill(data: ByteArray) {
        val buffer = DataBuffer(data)
        revitalize()
        repeat(data.size / 32) {
            repeat(visibleSize) { pos ->
                buffer.writeLong(squeeze(pos)) }
            round()
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