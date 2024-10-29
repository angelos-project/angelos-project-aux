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
import org.angproj.aux.res.allocateMemory
import org.angproj.aux.res.Memory as Chunk

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public actual open class Memory internal actual constructor(
    size: Int, mem: MemoryManager<Memory>
) : AbstractMemory(size, mem) {

    actual final override val data: Chunk = allocateMemory(size)

    override fun close() { super.close { memCtx.recycle(this) } }

    actual override fun getByte(index: Int): Byte {
        throw UnsupportedOperationException("No access to native memory.")
    }

    actual override fun getShort(index: Int): Short {
        throw UnsupportedOperationException("No access to native memory.")
    }

    actual override fun getInt(index: Int): Int {
        throw UnsupportedOperationException("No access to native memory.")
    }

    actual override fun getLong(index: Int): Long {
        throw UnsupportedOperationException("No access to native memory.")
    }

    actual override fun setByte(index: Int, value: Byte) {
        throw UnsupportedOperationException("No access to native memory.")
    }

    actual override fun setShort(index: Int, value: Short) {
        throw UnsupportedOperationException("No access to native memory.")
    }

    actual override fun setInt(index: Int, value: Int) {
        throw UnsupportedOperationException("No access to native memory.")
    }

    actual override fun setLong(index: Int, value: Long) {
        throw UnsupportedOperationException("No access to native memory.")
    }

    @Deprecated("Not to be used with memory manager")
    actual override fun create(size: Int, idxLimit: Int): Memory = memCtx.allocate(DataSize.findLowestAbove(size))

    //override val size: Int = size
}