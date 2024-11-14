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
import org.angproj.aux.res.Memory as Chunk

public abstract class AbstractMemory protected constructor(
    size: Int, mem: MemoryManager<Memory>
): Segment<Memory>(size, mem) {

    /*init {
        // Must be BYTE
        require(typeSize == TypeSize.BYTE)
    }*/

    //@PublishedApi
    internal abstract val data: Chunk

    @Deprecated("Not to be used with memory manager")
    public abstract fun create(size: Int, idxLimit: Int): AbstractMemory

    abstract override fun getByte(index: Int): Byte

    abstract override fun getShort(index: Int): Short

    abstract override fun getInt(index: Int): Int

    abstract override fun getLong(index: Int): Long

    abstract override fun getRevShort(index: Int): Short

    abstract override fun getRevInt(index: Int): Int

    abstract override fun getRevLong(index: Int): Long

    abstract override fun setByte(index: Int, value: Byte)

    abstract override fun setShort(index: Int, value: Short)

    abstract override fun setInt(index: Int, value: Int)

    abstract override fun setLong(index: Int, value: Long)

    abstract override fun setRevShort(index: Int, value: Short)

    abstract override fun setRevInt(index: Int, value: Int)

    abstract override fun setRevLong(index: Int, value: Long)

    /*public companion object {
        public val typeSize: TypeSize = TypeSize.BYTE
    }*/
}

//@PublishedApi
/*internal inline fun<reified T: AbstractMemory> T.innerCopy(dest: T, destOff: Int, idxFrom: Int, idxTo: Int) {
    data.copyInto<Reify>(dest.data, destOff, idxFrom, idxTo)
}*/