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

public open class Model(final override val size: Int): Segment {

    protected val data: LongArray = LongArray(size / ByteString.byteSize)

    override fun getByte(index: Int): Byte {
        if(index !in 0..<size) throw IllegalArgumentException("Out of bounds.")
        return data[index / ByteString.longSize].fullByte(index % ByteString.longSize)
    }

    override fun getShort(index: Int): Short {
        if(index !in 0..<(size-1)) throw IllegalArgumentException("Out of bounds.")
        val pos = index / ByteString.longSize
        return when(val offset = index % ByteString.longSize) {
            7 -> data[pos].joinShort(data[pos+1])
            else -> data[pos].fullShort(offset)
        }
    }

    override fun getInt(index: Int): Int {
        if(index !in 0..<(size-3)) throw IllegalArgumentException("Out of bounds.")
        val pos = index / ByteString.longSize
        return when(val offset = index % ByteString.longSize) {
            in 0..<5 -> data[pos].fullInt(offset)
            else -> data[pos].joinInt(offset, data[pos+1])
        }
    }

    override fun getLong(index: Int): Long {
        if(index !in 0..<(size-7)) throw IllegalArgumentException("Out of bounds.")
        val pos = index / ByteString.longSize
        return when(val offset = index % ByteString.longSize) {
            0 -> data[pos]
            else -> data[pos].joinLong(offset, data[pos+1])
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
}