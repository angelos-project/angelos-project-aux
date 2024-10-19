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

import org.angproj.aux.buf.AbstractSpeedCopy
import org.angproj.aux.util.Reify
import org.angproj.aux.res.Manager
import org.angproj.aux.res.allocateMemory
import org.angproj.aux.res.Memory as Chunk

import sun.misc.Unsafe
import java.lang.ref.Cleaner.Cleanable

@Suppress(
    "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING",
)
public actual open class Memory actual constructor(
    size: Int, idxLimit: Int
) : AbstractMemory(size, idxLimit) {

    public actual constructor(size: Int) : this(size, size)

    private var _closed = false

    override val isOpen: Boolean
        get() = !_closed

    actual final override val data: Chunk = allocateMemory(length)
    protected val ptr: Long = data.ptr

    private val cleanable: Cleanable = Manager.cleaner.register(this) { data.dispose() }
    override fun close() {
        if(isOpen) {
            cleanable.clean()
            _closed = true
        }
    }

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

    actual override fun setByte(index: Int, value: Byte) {
        index.checkRangeByte<Reify>()
        unsafe.putByte(ptr + index, value)
    }

    actual override fun setShort(index: Int, value: Short) {
        index.checkRangeShort<Reify>()
        unsafe.putShort(ptr + index, value)
    }

    actual override fun setInt(index: Int, value: Int) {
        index.checkRangeInt<Reify>()
        unsafe.putInt(ptr + index, value)
    }

    actual override fun setLong(index: Int, value: Long) {
        index.checkRangeLong<Reify>()
        unsafe.putLong(ptr + index, value)
    }

    actual override fun create(size: Int, idxLimit: Int): Memory = Memory(size, idxLimit)

    override fun <T: AbstractSpeedCopy> calculateInto(dest: T, destOff: Int, idxFrom: Int, idxTo: Int) {
        innerCopy(dest as Memory, destOff, idxFrom, idxTo)
    }

    public companion object {
        internal val unsafe: Unsafe = Chunk.unsafe
    }
}