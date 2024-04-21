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
package org.angproj.aux

import kotlin.jvm.JvmInline

public data class Blob<E>(val ptr: Long, val size: Int, internal val unsafe: E) {
}

@JvmInline
public value class Chunk(internal val mem: Blob<*>) {
    internal fun dispose() { Native.freeChunk(this) }

    internal companion object {
        fun allocate(size: Int): Chunk = Native.allocateChunk(size)
    }
}