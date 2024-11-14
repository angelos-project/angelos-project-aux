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
import org.angproj.aux.res.speedMemCpy
import org.angproj.aux.util.Copy
import org.angproj.aux.res.Memory as Chunk

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public expect open class Memory internal constructor(
    size: Int, mem: MemoryManager<Memory>
): AbstractMemory {

    final override val data: Chunk

    @Deprecated("Not to be used with memory manager")
    override fun create(size: Int, idxLimit: Int): Memory

    override fun getByte(index: Int): Byte

    override fun getShort(index: Int): Short

    override fun getInt(index: Int): Int

    override fun getLong(index: Int): Long

    override fun getRevShort(index: Int): Short

    override fun getRevInt(index: Int): Int

    override fun getRevLong(index: Int): Long

    override fun setByte(index: Int, value: Byte)

    override fun setShort(index: Int, value: Short)

    override fun setInt(index: Int, value: Int)

    override fun setLong(index: Int, value: Long)

    override fun setRevShort(index: Int, value: Short)

    override fun setRevInt(index: Int, value: Int)

    override fun setRevLong(index: Int, value: Long)
}

/**
 * TEMPORARILY FOR TESTING SPEED
 * */
public fun Memory.copyInto(
    destination: Memory, destinationOffset: Int, fromIndex: Int, toIndex: Int
): Int = object : Copy {
    operator fun invoke(): Int {
        check(isOpen && destination.isOpen) { "Closed memory" }
        require(fromIndex, toIndex, destinationOffset, this@copyInto, destination)
        //return speedMemCpy(fromIndex, toIndex, destinationOffset, this@copyInto.data.ptr, destination.data.ptr)
        return innerCopy(fromIndex, toIndex, destinationOffset, this@copyInto, destination)
    }
}()