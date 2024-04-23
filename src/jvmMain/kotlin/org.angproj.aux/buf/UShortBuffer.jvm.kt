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
package org.angproj.aux.buf

import sun.misc.Unsafe
import org.angproj.aux.res.Memory as Chunk

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public actual class UShortBuffer actual constructor(size: Int) : AbstractBufferType<UShort>(size) {

    actual override operator fun get(index: Int): UShort {
        if (index !in 0..<size) throw IllegalArgumentException("Out of bounds.")
        return unsafe.getShort(ptr + (index + idxOff) * idxSize.size).toUShort()
    }

    actual override operator fun set(index: Int, value: UShort) {
        if (index !in 0..<size) throw IllegalArgumentException("Out of bounds.")
        unsafe.putShort(ptr + (index + idxOff) * idxSize.size, value.toShort())
    }

    internal companion object {
        internal val unsafe: Unsafe = Chunk.unsafe
    }
}