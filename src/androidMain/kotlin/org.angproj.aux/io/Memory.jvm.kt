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

import org.angproj.aux.mem.MemoryManager
import org.angproj.aux.res.Manager
import org.angproj.aux.res.allocateMemory
import org.angproj.aux.res.Memory as Chunk

import sun.misc.Unsafe
import java.lang.ref.Cleaner.Cleanable

@Suppress(
    "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"
)
public actual open class Memory internal actual constructor(
    size: Int, mem: MemoryManager<Memory>
) : AbstractMemory(size, mem) {

    /*private var _closed = false

    override val isOpen: Boolean
        get() = !_closed*/

    actual final override val data: Chunk = allocateMemory(size)
    protected val ptr: Long = data.ptr

    // Disabled for min SDK 30
    // private val cleanable: Cleanable = Manager.cleaner.register(this) { data.dispose() }
    actual override fun close() {
        super.close {
            //cleanable.clean() // Always let the memory manager deal with this
            memCtx.recycle(this)
        }
    }

    /*override fun close() {
        if(isOpen) {
            cleanable.clean()
            _closed = true
        }
    }*/

    actual override fun getByte(index: Int): Byte {
        index.checkRangeByte<Unit>()
        return unsafe.getByte(ptr + index)
    }

    actual override fun getShort(index: Int): Short {
        index.checkRangeShort<Unit>()
        return unsafe.getShort(ptr + index)
    }

    actual override fun getInt(index: Int): Int {
        index.checkRangeInt<Unit>()
        return unsafe.getInt(ptr + index)
    }

    actual override fun getLong(index: Int): Long {
        index.checkRangeLong<Unit>()
        return unsafe.getLong(ptr + index)
    }

    actual override fun getRevShort(index: Int): Short = swapShort<Unit>(getShort(index))

    actual override fun getRevInt(index: Int): Int = swapInt<Unit>(getInt(index))

    actual override fun getRevLong(index: Int): Long = swapLong<Unit>(getLong(index))

    actual override fun setByte(index: Int, value: Byte) {
        index.checkRangeByte<Unit>()
        unsafe.putByte(ptr + index, value)
    }

    actual override fun setShort(index: Int, value: Short) {
        index.checkRangeShort<Unit>()
        unsafe.putShort(ptr + index, value)
    }

    actual override fun setInt(index: Int, value: Int) {
        index.checkRangeInt<Unit>()
        unsafe.putInt(ptr + index, value)
    }

    actual override fun setLong(index: Int, value: Long) {
        index.checkRangeLong<Unit>()
        unsafe.putLong(ptr + index, value)
    }

    actual override fun setRevShort(index: Int, value: Short) { setShort(index, swapShort<Unit>(value)) }

    actual override fun setRevInt(index: Int, value: Int) { setInt(index, swapInt<Unit>(value)) }

    actual override fun setRevLong(index: Int, value: Long) { setLong(index, swapLong<Unit>(value)) }

    @Deprecated("Not to be used with memory manager")
    actual override fun create(size: Int, idxLimit: Int): Memory = memCtx.allocate(DataSize.findLowestAbove(size))

    public companion object {
        internal val unsafe: Unsafe = Chunk.unsafe
    }

    //override val size: Int = size
}