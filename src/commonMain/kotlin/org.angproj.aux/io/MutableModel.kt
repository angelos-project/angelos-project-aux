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

public class MutableModel(size: Int) : Model(size), MutableSegment {
    override fun setByte(index: Int, value: Byte) {
        if(index !in 0..<size) throw IllegalArgumentException("Out of bounds.")
        val idx = index + idxOff
        val pos = idx / ByteString.longSize
        data[pos] = data[pos].wholeByte(idx % ByteString.longSize, value)
    }

    override fun setShort(index: Int, value: Short) {
        if(index !in 0..<(size-1)) throw IllegalArgumentException("Out of bounds.")
        val idx = index + idxOff
        var pos = idx / ByteString.longSize
        when(val offset = idx % ByteString.longSize) {
            7 -> {
                data[pos] = data[pos++].sideShortLeft(value)
                data[pos] = data[pos].sideShortRight(value)
            }
            else -> data[pos] = data[pos].wholeShort(offset, value)
        }
    }

    override fun setInt(index: Int, value: Int) {
        if(index !in 0..<(size-3)) throw IllegalArgumentException("Out of bounds.")
        val idx = index + idxOff
        var pos = idx / ByteString.longSize
        when(val offset = idx % ByteString.longSize) {
            in 0..<5 -> data[pos] = data[pos].wholeInt(offset, value)
            else -> {
                data[pos] = data[pos++].sideIntLeft(offset, value)
                data[pos] = data[pos].sideIntRight(offset, value)
            }
        }
    }

    override fun setLong(index: Int, value: Long) {
        if(index !in 0..<(size-7)) throw IllegalArgumentException("Out of bounds.")
        val idx = index + idxOff
        var pos = idx / ByteString.longSize
        when(val offset = idx % ByteString.longSize) {
            0 -> data[pos] = value
            else -> {
                data[pos] = data[pos++].sideLongLeft(offset, value)
                data[pos] = data[pos].sideLongRight(offset, value)
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