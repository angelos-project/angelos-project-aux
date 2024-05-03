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

import org.angproj.aux.buf.Reify
import org.angproj.aux.res.Manager
import org.angproj.aux.res.allocateMemory
import org.angproj.aux.res.Memory as Chunk

import sun.misc.Unsafe
import java.lang.ref.Cleaner.Cleanable

@Suppress(
    "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING",
)
public actual open class Memory actual constructor(
    size: Int, idxOff: Int, idxEnd: Int
) : AbstractMemory(size, idxOff, idxEnd) {

    public actual constructor(size: Int) : this(size, 0, size)

    actual final override val data: Chunk = allocateMemory(length)
    protected val ptr: Long = data.ptr + idxOff

    private val cleanable: Cleanable = Manager.cleaner.register(this) { data.dispose() }
    public override fun close() { cleanable.clean() }

    actual override fun getByte(index: Int): Byte {
        index.checkRangeByte<Reify>()
        return unsafe.getByte(ptr + index)
    }

    actual override fun getShort(index: Int): Short {
        index.checkRangeShort<Reify>()
        return unsafe.getShort(ptr + index)
    }

    actual override fun getInt(index: Int): Int {
        index.checkRangeInt<Reify>()
        return unsafe.getInt(ptr + index)
    }

    actual override fun getLong(index: Int): Long {
        index.checkRangeLong<Reify>()
        return unsafe.getLong(ptr + index)
    }

    actual override fun create(size: Int, idxOff: Int, idxEnd: Int): Memory = Memory(size, idxOff, idxEnd)

    override fun getPointer(): Long = data.ptr

    public companion object {
        internal val unsafe: Unsafe = Chunk.unsafe
    }
}