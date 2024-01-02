/**
 * Copyright (c) 2021-2024 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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

import org.khronos.webgl.*
import kotlin.js.Date
import kotlin.math.max

/**
 * Little/big endian test from:
 * https://developers.redhat.com/articles/2021/12/09/how-nodejs-uses-v8-javascript-engine-run-your-code#big_endian_byte_order_on_v8
 */
internal actual fun getCurrentEndian(): Endian {
    val buffer = ArrayBuffer(16)
    val int8View = Int8Array(buffer)
    val int16View = Int16Array(buffer)
    int16View[0] = 5

    return when (int8View[0].toInt()) {
        5 -> Endian.LITTLE
        else -> Endian.BIG
    }
}

internal actual fun unixEpoch(): Long {
    return Date.now().toLong()
}

private var entropyCounter: Long = 1

internal actual fun epochEntropy(): Long {
    entropyCounter = max(1, entropyCounter + 1)
    val timestamp = Date.now().toLong()
    val nanos = Date.now().toLong().floorMod(1_000_000_000)
    return -(timestamp + nanos + 1).rotateRight(53) xor
            (timestamp - nanos - 1).inv().rotateLeft(53) * entropyCounter
}