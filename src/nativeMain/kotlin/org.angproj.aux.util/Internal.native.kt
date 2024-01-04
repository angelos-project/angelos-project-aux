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

internal actual fun epochEntropy(): Pair<Long, Long> = memScoped {
    val tv = nativeHeap.alloc<timeval>()
    gettimeofday(tv.ptr, null)
    val timestamp = tv.tv_sec * 1000
    val nanos = tv.tv_usec.toLong() * 1000
    free(tv.ptr)
    Pair(
        timestamp,
        nanos
    )
}