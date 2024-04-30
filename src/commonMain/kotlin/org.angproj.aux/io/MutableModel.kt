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

public class MutableModel(
    size: Int, idxOff: Int, idxEnd: Int
) : Model(size, idxOff, idxEnd), MutableSegment {

    public constructor(size: Int) : this(size, 0, size)

    override fun create(size: Int, idxOff: Int, idxEnd: Int): MutableModel = MutableModel(size, idxOff, idxEnd)

    override fun copyOf(): MutableModel {
        TODO("Not yet implemented")
    }

    override fun copyOfRange(idxFrom: Int, idxTo: Int): MutableModel = copyOfRange2(idxFrom, idxTo) as MutableModel

    override fun setByte(index: Int, value: Byte) {
        index.checkRangeByte<Reify>()
        val idx = index + idxOff
        val pos = idx / ByteString.longSize
        data[pos] = data[pos].wholeByte<Reify>(idx % ByteString.longSize, value)
    }

    override fun setShort(index: Int, value: Short) {
        index.checkRangeShort<Reify>()
        val idx = index + idxOff
        var pos = idx / ByteString.longSize
        when(val offset = idx % ByteString.longSize) {
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
        var pos = idx / ByteString.longSize
        when(val offset = idx % ByteString.longSize) {
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
        var pos = idx / ByteString.longSize
        when(val offset = idx % ByteString.longSize) {
            0 -> data[pos] = value
            else -> {
                data[pos] = data[pos++].sideLongLeft<Reify>(offset, value)
                data[pos] = data[pos].sideLongRight<Reify>(offset, value)
            }
        }
    }

    /**
     * Sets the high bytes at the end of the model, or the left side of a variable split between two models.
     * */
    public fun leftSideSet(offset: Int, size: Int, value: Long) {
        val index = this.size - ByteString.longSize
        setLong(index, getLong(index).setLeftSide(offset, size, value))
    }

    /**
     * Sets the low bytes at the beginning of the model, or the right side of a variable split between two models.
     * */
    public fun rightSideSet(offset: Int, size: Int, value: Long) {
        val index = this.size - ByteString.longSize
        setLong(index, getLong(index).setRightSide(offset, size, value))
    }
}