/**
 * Copyright (c) 2023 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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
package org.angproj.aux.util

import kotlin.jvm.JvmStatic
import kotlin.native.concurrent.ThreadLocal

/**
 * This nonce generator though it contains timestamp seeding and a sequence counter,
 * is not to be considered as cryptographically secure random!
 * */
@ThreadLocal
public object Nonce {
    private var counter: Long = Long.MIN_VALUE
    private var epoch: Long = Epoch.getEpochMilliSecs()

    private var seed1: Long = 0xF069EC9F3E02D799u.toLong()
    private var seed2: Long = 0xC5D12A7F2E67ABC7u.toLong()

    init { repeat(16) { scramble() } }

    private fun scramble() {
        seed2 = -(seed1 + counter).rotateRight(2) xor (seed1 + epoch).inv().rotateLeft(17)
        seed1 = -(seed2 + epoch).rotateRight(2) xor (seed2 + counter).inv().rotateLeft(17)
    }

    @JvmStatic
    public fun reseedWithTimestamp() { epoch = Epoch.getEpochMilliSecs() }

    @JvmStatic
    public fun getFastNonce(): Pair<Long, Long> {
        counter++
        scramble()
        return Pair(seed1, seed2)
    }

    @JvmStatic
    public fun getNonce(withTimestamp: Boolean = false): ByteArray {
        if (withTimestamp) reseedWithTimestamp()
        val pair = getFastNonce()
        return ByteArray(16).also {
            it.writeLongAt(0, pair.first)
            it.writeLongAt(8, pair.second)
        }
    }
}