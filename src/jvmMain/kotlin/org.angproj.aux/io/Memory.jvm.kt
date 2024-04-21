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
package org.angproj.aux.io

import org.angproj.aux.Chunk
import org.angproj.aux.Native
import sun.misc.Unsafe

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public actual open class Memory actual constructor(size: Int) : Segment {

    actual final override val size: Int = size
    protected actual val data: Chunk = Native.allocateChunk(size)
    protected val ptr: Long = data.mem.ptr
    protected val unsafe: Unsafe = Native.unsafe

    actual override fun getByte(index: Int): Byte {
        if(index !in 0..<size) throw IllegalArgumentException("Out of bounds.")
        return unsafe.getByte(ptr + index)
    }

    actual override fun getShort(index: Int): Short {
        if(index !in 0..<(size-1)) throw IllegalArgumentException("Out of bounds.")
        return unsafe.getShort(ptr + index)
    }

    actual override fun getInt(index: Int): Int {
        if(index !in 0..<(size-3)) throw IllegalArgumentException("Out of bounds.")
        return unsafe.getInt(ptr + index)
    }

    actual override fun getLong(index: Int): Long {
        if(index !in 0..<(size-7)) throw IllegalArgumentException("Out of bounds.")
        return unsafe.getLong(ptr + index)
    }
}