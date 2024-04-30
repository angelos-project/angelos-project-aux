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

public open class Model protected constructor(
    size: Int, idxOff: Int, idxEnd: Int
): Segment(size, typeSize, idxOff, idxEnd) {

    public constructor(size: Int) : this(size, 0, size)

    init {
        // Must be BYTE
        require(typeSize == TypeSize.BYTE)
    }

    protected val data: LongArray = LongArray(length / TypeSize.long)

    override fun create(size: Int, idxOff: Int, idxEnd: Int): Model = Model(size, idxOff, idxEnd)

    override fun copyOf(): Model {
        TODO("Not yet implemented")
    }

    override fun copyOfRange(idxFrom: Int, idxTo: Int): Model = copyOfRange2(idxFrom, idxTo)

    protected fun copyOfRange2(idxFrom: Int, idxTo: Int): Model {
        val factor = TypeSize.long / idxSize.size
        val newIdxOff = (idxOff + idxFrom) % factor
        val newSize = idxTo - idxFrom
        val newIdxEnd = newIdxOff + newSize
        val baseIdx = (idxOff + idxFrom) - newIdxOff

        val copy = create(newSize, newIdxOff, newIdxEnd)

        val basePtr = baseIdx / TypeSize.long
        val copyPtr = 0

        (0 until copy.length / TypeSize.long).forEach {
            copy.data[copyPtr + it] = data[basePtr + it]
        }
        return copy
    }

    override fun getByte(index: Int): Byte {
        index.checkRangeByte<Reify>()
        val idx = index + idxOff
        return data[idx / ByteString.longSize].fullByte<Reify>(idx % ByteString.longSize)
    }

    override fun getShort(index: Int): Short {
        index.checkRangeShort<Reify>()
        val idx = index + idxOff
        val pos = idx / ByteString.longSize
        return when(val offset = idx % ByteString.longSize) {
            7 -> data[pos].joinShort<Reify>(data[pos+1])
            else -> data[pos].fullShort<Reify>(offset)
        }
    }

    override fun getInt(index: Int): Int {
        index.checkRangeInt<Reify>()
        val idx = index + idxOff
        val pos = idx / ByteString.longSize
        return when(val offset = idx % ByteString.longSize) {
            in 0..<5 -> data[pos].fullInt<Reify>(offset)
            else -> data[pos].joinInt<Reify>(offset, data[pos+1])
        }
    }

    override fun getLong(index: Int): Long {
        index.checkRangeLong<Reify>()
        val idx = index + idxOff
        val pos = idx / ByteString.longSize
        return when(val offset = idx % ByteString.longSize) {
            0 -> data[pos]
            else -> data[pos].joinLong<Reify>(offset, data[pos+1])
        }
    }

    /**
     * Gets the high bytes from the end of the model, or the left side of a variable split between two models.
     * */
    public fun leftSideGet(offset: Int, size: Int): Long = getLong(
        this.size - ByteString.longSize).getLeftSide(offset, size)

    /**
     * Gets the low bytes from the beginning of the model, or the right side of a variable split between two models.
     * */
    public fun rightSideGet(offset: Int, size: Int): Long = getLong(
        0).getRightSide(offset, size)

    public override fun close() { }

    public companion object {
        public val typeSize: TypeSize = TypeSize.BYTE
    }
}