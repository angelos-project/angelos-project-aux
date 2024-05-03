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

public abstract class AbstractModel protected constructor(
    size: Int, idxOff: Int, idxEnd: Int
): Segment(size, typeSize, idxOff, idxEnd) {

    init {
        // Must be BYTE
        require(typeSize == TypeSize.BYTE)
    }

    protected val data: LongArray = LongArray(length / TypeSize.long)

    abstract override fun create(size: Int, idxOff: Int, idxEnd: Int): AbstractModel

    override fun speedCopy(ctx: Context): AbstractModel {
        val copy = create(ctx.newSize, ctx.newIdxOff, ctx.newIdxEnd)

        val baseOffset = ctx.baseIdx / TypeSize.long

        (0 until copy.length / TypeSize.long).forEach {
            copy.data[it] = data[baseOffset + it]
        }
        return copy
    }

    override fun getByte(index: Int): Byte {
        index.checkRangeByte<Reify>()
        val idx = index + idxOff
        return data[idx / TypeSize.long].fullByte<Reify>(idx % TypeSize.long)
    }

    override fun getShort(index: Int): Short {
        index.checkRangeShort<Reify>()
        val idx = index + idxOff
        val pos = idx / TypeSize.long
        return when(val offset = idx % TypeSize.long) {
            7 -> data[pos].joinShort<Reify>(data[pos+1])
            else -> data[pos].fullShort<Reify>(offset)
        }
    }

    override fun getInt(index: Int): Int {
        index.checkRangeInt<Reify>()
        val idx = index + idxOff
        val pos = idx / TypeSize.long
        return when(val offset = idx % TypeSize.long) {
            in 0..<5 -> data[pos].fullInt<Reify>(offset)
            else -> data[pos].joinInt<Reify>(offset, data[pos+1])
        }
    }

    override fun getLong(index: Int): Long {
        index.checkRangeLong<Reify>()
        val idx = index + idxOff
        val pos = idx / TypeSize.long
        return when(val offset = idx % TypeSize.long) {
            0 -> data[pos]
            else -> data[pos].joinLong<Reify>(offset, data[pos+1])
        }
    }

    override fun setByte(index: Int, value: Byte) {
        index.checkRangeByte<Reify>()
        val idx = index + idxOff
        val pos = idx / TypeSize.long
        data[pos] = data[pos].wholeByte<Reify>(idx % TypeSize.long, value)
    }

    override fun setShort(index: Int, value: Short) {
        index.checkRangeShort<Reify>()
        val idx = index + idxOff
        var pos = idx / TypeSize.long
        when(val offset = idx % TypeSize.long) {
            7 -> {
                data[pos] = data[pos++].sideShortLeft<Reify>(value)
                data[pos] = data[pos].sideShortRight<Reify>(value)
            }
            else -> data[pos] = data[pos].wholeShort<Reify>(offset, value)
        }
    }

    override fun setInt(index: Int, value: Int) {
        index.checkRangeInt<Reify>()
        val idx = index + idxOff
        var pos = idx / TypeSize.long
        when(val offset = idx % TypeSize.long) {
            in 0..<5 -> data[pos] = data[pos].wholeInt<Reify>(offset, value)
            else -> {
                data[pos] = data[pos++].sideIntLeft<Reify>(offset, value)
                data[pos] = data[pos].sideIntRight<Reify>(offset, value)
            }
        }
    }

    override fun setLong(index: Int, value: Long) {
        index.checkRangeLong<Reify>()
        val idx = index + idxOff
        var pos = idx / TypeSize.long
        when(val offset = idx % TypeSize.long) {
            0 -> data[pos] = value
            else -> {
                data[pos] = data[pos++].sideLongLeft<Reify>(offset, value)
                data[pos] = data[pos].sideLongRight<Reify>(offset, value)
            }
        }
    }

    /**
     * Gets the high bytes from the end of the model, or the left side of a variable split between two models.
     * */
    public fun leftSideGet(offset: Int, size: Int): Long = getLong(
        this.size - TypeSize.long).getLeftSide(offset, size)

    /**
     * Gets the low bytes from the beginning of the model, or the right side of a variable split between two models.
     * */
    public fun rightSideGet(offset: Int, size: Int): Long = getLong(
        0).getRightSide(offset, size)

    /**
     * Sets the high bytes at the end of the model, or the left side of a variable split between two models.
     * */
    public fun leftSideSet(offset: Int, size: Int, value: Long) {
        val index = this.size - TypeSize.long
        setLong(index, getLong(index).setLeftSide(offset, size, value))
    }

    /**
     * Sets the low bytes at the beginning of the model, or the right side of a variable split between two models.
     * */
    public fun rightSideSet(offset: Int, size: Int, value: Long) {
        val index = this.size - TypeSize.long
        setLong(index, getLong(index).setRightSide(offset, size, value))
    }

    public companion object {
        public val typeSize: TypeSize = TypeSize.BYTE
    }
}