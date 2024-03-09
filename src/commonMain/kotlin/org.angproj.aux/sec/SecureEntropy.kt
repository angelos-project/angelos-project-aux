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
import org.angproj.aux.util.epochEntropy
import org.angproj.aux.util.floorMod
import org.angproj.aux.util.writeLongAt
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
public object SecureEntropy : Reader {

    private var entropy: Long = 0xFFF73E99668196E9uL.toLong()
    private var counter: Long = 0xFFFF7D5BF9259763uL.toLong()

    private fun cycle(): Long {
        val (timestamp, nanos) = epochEntropy()
        counter++
        entropy = ((-entropy.inv() xor timestamp) * 3) xor ((-entropy.inv() xor nanos) * 5) * -counter.inv()
        return entropy
    }

    private fun require(length: Int) {
        require(length.floorMod(Long.SIZE_BYTES) == 0) { "Length must be divisible by 8." }
        require(length <= BufferSize._1K.size) { "Length must not surpass 1 Kilobyte." }
    }

    private fun fill(data: ByteArray) {
        (data.indices step 8).forEach { index -> data.writeLongAt(index, cycle()) } }

    override fun read(length: Int): ByteArray {
        require(length)
        return ByteArray(length).also { fill(it) }
    }

    override fun read(data: ByteArray): Int {
        require(data.size)
        fill(data)
        return data.size
    }

    public fun readLong(): Long = cycle()
}