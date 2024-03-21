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
import org.angproj.aux.io.DataSize
import org.angproj.aux.util.floorMod
import org.angproj.aux.util.readLongAt
import kotlin.math.absoluteValue
import kotlin.native.concurrent.ThreadLocal
import kotlin.time.TimeSource

@ThreadLocal
public object SecureEntropy : AbstractSponge256(), Reader {

    init {
        revitalize()
    }

    private fun revitalize() {
        var loops: Byte = 16
        val entropy = ByteArray(visibleSize * Long.SIZE_BYTES) {
            val mark = TimeSource.Monotonic.markNow()
            repeat(loops.toInt().absoluteValue) { Unit }
            loops = mark.elapsedNow().inWholeNanoseconds.toByte()
            loops
        }
        (0 until visibleSize).forEach {
            absorb(entropy.readLongAt(it * Long.SIZE_BYTES), it)
        }
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