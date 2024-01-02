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

import java.nio.ByteOrder
import kotlin.math.max

internal actual fun getCurrentEndian(): Endian = when(ByteOrder.nativeOrder()) {
    ByteOrder.LITTLE_ENDIAN -> Endian.LITTLE
    ByteOrder.BIG_ENDIAN -> Endian.BIG
    else -> Endian.UNKNOWN
}

internal actual fun unixEpoch(): Long {
    return System.currentTimeMillis()
}

private var entropyCounter: Long = 1

internal actual fun epochEntropy(): Long {
    entropyCounter = max(1, entropyCounter + 1)
    val timestamp = System.currentTimeMillis()
    val nanos = System.nanoTime().floorMod(1_000_000_000)
    return -(timestamp + nanos + 1).rotateRight(53) xor
            (timestamp - nanos - 1).inv().rotateLeft(53) * entropyCounter
}