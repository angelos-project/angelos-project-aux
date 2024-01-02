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

import kotlin.math.max
import kotlinx.cinterop.*
import platform.posix.*

internal actual fun getCurrentEndian(): Endian = when(Platform.isLittleEndian) {
    true -> Endian.LITTLE
    else -> Endian.BIG
}

internal actual fun unixEpoch(): Long = memScoped {
    val tv = nativeHeap.alloc<timeval>()
    gettimeofday(tv.ptr, null)
    val timestamp: Long = 1000 * tv.tv_sec + tv.tv_usec / 1000
    free(tv.ptr)
    return timestamp
}

private var entropyCounter: Long = 1

internal actual fun epochEntropy(): Long  = memScoped {
    entropyCounter = max(1, entropyCounter + 1)
    val tv = nativeHeap.alloc<timeval>()
    gettimeofday(tv.ptr, null)
    val timestamp = tv.tv_sec * 1000
    val nanos = tv.tv_usec * 1000
    val entropy = -(timestamp + nanos + 1).rotateRight(53) xor
            (timestamp - nanos - 1).inv().rotateLeft(53) * entropyCounter
    free(tv.ptr)
    return@memScoped entropy
}