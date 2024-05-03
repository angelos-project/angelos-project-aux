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
import org.angproj.aux.buf.Reify
import org.angproj.aux.res.speedLongGet
import org.angproj.aux.res.speedLongSet
import org.angproj.aux.res.Memory as Chunk

public abstract class AbstractMemory protected constructor(
    size: Int, idxOff: Int, idxEnd: Int
): Segment(size, typeSize, idxOff, idxEnd) {

    init {
        // Must be BYTE
        require(typeSize == TypeSize.BYTE)
    }

    protected abstract val data: Chunk

    abstract override fun create(size: Int, idxOff: Int, idxEnd: Int): AbstractMemory

    override fun speedCopy(ctx: Context): AbstractSpeedCopy {
        val copy = create(ctx.newSize, ctx.newIdxOff, ctx.newIdxEnd)
        val basePtr = getBasePtr(ctx.baseIdx)
        val copyPtr = copy.getPointer()

        (0 until copy.length step TypeSize.long).forEach {
            data.speedLongSet<Reify>(copyPtr + it.toLong(),
                copy.data.speedLongGet<Reify>(basePtr + it.toLong()))
        }
        return copy
    }

    abstract override fun getByte(index: Int): Byte

    abstract override fun getShort(index: Int): Short

    abstract override fun getInt(index: Int): Int

    abstract override fun getLong(index: Int): Long

    public companion object {
        public val typeSize: TypeSize = TypeSize.BYTE
    }
}
