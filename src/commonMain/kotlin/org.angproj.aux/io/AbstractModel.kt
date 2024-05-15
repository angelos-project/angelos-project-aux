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

import org.angproj.aux.res.chunkLoop
import org.angproj.aux.util.Reifiable
import org.angproj.aux.util.Reify

public abstract class AbstractModel protected constructor(
    size: Int, idxLimit: Int
): Segment(size, typeSize, idxLimit) {

    init {
        // Must be BYTE
        require(typeSize == TypeSize.BYTE)
    }

    @PublishedApi
    internal val data: LongArray = LongArray(length / TypeSize.long)

    abstract override fun create(size: Int, idxLimit: Int): AbstractModel

    override fun getByte(index: Int): Byte {
        index.checkRangeByte<Reify>()
        val idx = index
        return data[idx / TypeSize.long].fullByte<Reify>(idx % TypeSize.long)
    }

    override fun getShort(index: Int): Short {
        index.checkRangeShort<Reify>()
        val idx = index
        val pos = idx / TypeSize.long
        return when(val offset = idx % TypeSize.long) {
            7 -> data[pos].joinShort<Reify>(data[pos+1])
            else -> data[pos].fullShort<Reify>(offset)
        }
    }

    override fun getInt(index: Int): Int {
        index.checkRangeInt<Reify>()
        val idx = index
        val pos = idx / TypeSize.long
        return when(val offset = idx % TypeSize.long) {
            in 0..<5 -> data[pos].fullInt<Reify>(offset)
            else -> data[pos].joinInt<Reify>(offset, data[pos+1])
        }
    }

    override fun getLong(index: Int): Long {
        index.checkRangeLong<Reify>()
        val idx = index
        val pos = idx / TypeSize.long
        return when(val offset = idx % TypeSize.long) {
            0 -> data[pos]
            else -> data[pos].joinLong<Reify>(offset, data[pos+1])
        }
    }

    override fun setByte(index: Int, value: Byte) {
        index.checkRangeByte<Reify>()
        val idx = index
        val pos = idx / TypeSize.long
        data[pos] = data[pos].wholeByte<Reify>(idx % TypeSize.long, value)
    }

    override fun setShort(index: Int, value: Short) {
        index.checkRangeShort<Reify>()
        val idx = index
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
        val idx = index
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
        val idx = index
        var pos = idx / TypeSize.long
        when(val offset = idx % TypeSize.long) {
            0 -> data[pos] = value
            else -> {
                data[pos] = data[pos++].sideLongLeft<Reify>(offset, value)
                data[pos] = data[pos].sideLongRight<Reify>(offset, value)
            }
        }
    }

    private fun Long.getLeftSide(offset: Int, size: Int): Long = (
            this shl ((size - TypeSize.long - offset) * 8))

    private fun Long.getRightSide(offset: Int, size: Int): Long = (
            this ushr ((TypeSize.long - size - TypeSize.long - offset) * 8))

    private fun Long.setLeftSide(offset: Int, size: Int, value: Long): Long {
        val pos = (size - (TypeSize.long - offset)) * 8
        val mask = 0xffffffffffffffffuL.toLong() ushr ((TypeSize.long - size) * 8)
        return ((mask ushr pos).inv() and this) or (value ushr pos)
    }

    private fun Long.setRightSide(offset: Int, size: Int, value: Long): Long {
        val pos = (TypeSize.long - size - offset) * 8
        val mask = 0xffffffffffffffffuL.toLong() ushr ((TypeSize.long - size) * 8)
        return ((mask shl pos).inv() and this) or (value shl pos)
    }

    private inline fun <reified T: Reifiable>Long.fullByte(offset: Int): Byte = (
            this ushr (offset * 8)).toByte()

    private inline fun <reified T: Reifiable> Long.fullShort(offset: Int): Short = (
            this shr (offset * 8)).toShort()

    private inline fun <reified T: Reifiable> Long.fullInt(offset: Int): Int = (
            this ushr (offset * 8)).toInt()

    private inline fun <reified T: Reifiable> Long.joinShort(other: Long): Short = ((this ushr 56) or (other shl 8)).toShort()

    private inline fun <reified T: Reifiable> Long.joinInt(offset: Int, other: Long): Int = ((
            this ushr (offset * 8)) or ((other and (-1L shl ((
            offset - TypeSize.int) * 8)).inv()) shl ((TypeSize.long - offset) * 8))).toInt()

    private inline fun <reified T: Reifiable> Long.joinLong(offset: Int, other: Long): Long = ((
            this ushr (offset * 8)) or ((other and (-1L shl ((
            offset - TypeSize.long) * 8)).inv()) shl ((8 - offset) * 8)))

    private inline fun <reified T: Reifiable> Long.wholeByte(offset: Int, value: Byte): Long {
        val pos = offset * 8
        return ((0xffL shl pos).inv() and this) or (value.toLong() shl pos)
    }

    private inline fun <reified T: Reifiable> Long.wholeShort(offset: Int, value: Short): Long {
        val pos = offset * 8
        return ((0xffffL shl pos).inv() and this) or (value.toLong() shl pos)
    }

    private inline fun <reified T: Reifiable> Long.wholeInt(offset: Int, value: Int): Long {
        val pos = (offset) * 8
        return ((0xffffffffL shl pos).inv() and this) or (value.toLong() shl pos)
    }

    private inline fun <reified T: Reifiable> Long.sideShortLeft(value: Short): Long = (this and 0x00ffffffffffffff) or (value.toLong() shl 56)

    private inline fun <reified T: Reifiable> Long.sideShortRight(value: Short): Long = ((
            this and 0xff.inv()) or (value.toLong() ushr 8))

    private inline fun <reified T: Reifiable> Long.sideIntLeft(offset: Int, value: Int): Long {
        val pos = offset * 8
        return (this and (-1L shl pos).inv()) or (value.toLong() shl pos)
    }

    private inline fun <reified T: Reifiable> Long.sideIntRight(offset: Int, value: Int): Long = ((
            this and (-1L shl ((offset - TypeSize.int) * 8))) or
            (value.toLong() ushr ((TypeSize.long - offset) * 8)))

    private inline fun <reified T: Reifiable> Long.sideLongLeft(offset: Int, value: Long): Long {
        val pos = offset * 8
        return (this and (-1L shl pos).inv()) or (value shl pos)
    }

    private inline fun <reified T: Reifiable> Long.sideLongRight(offset: Int, value: Long): Long = ((
            this and (-1L shl ((offset - TypeSize.long) * 8))) or
            (value ushr ((TypeSize.long - offset) * 8)))

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

@PublishedApi
internal inline fun<reified T: AbstractModel> T.innerCopy(dest: T, destOff: Int, idxFrom: Int, idxTo: Int) {
    val length = idxTo - idxFrom
    require(idxFrom <= idxTo) {
        "Start index ($idxFrom) is larger than end index ($idxTo)" }
    require(length >= 0) {
        "Length ($length) can not be negative" }
    require(idxFrom in 0..<size) {
        "Start index ($idxFrom) not in memory range" }
    require(idxFrom + length in 0..size) {
        "End index (${idxFrom + length}) outside of memory range" }
    require(destOff in 0..<dest.size) {
        "Destination offset ($destOff) not in memory range" }
    require(destOff + length in 0..dest.size) {
        "End index (${destOff + length}) outside of memory range" }

    val index = chunkLoop<Reify>(0, length, TypeSize.long) {
        dest.setLong(
            destOff + it,
            getLong(idxFrom + it)
        )
    }
    chunkLoop<Reify>(index, length, TypeSize.byte) {
        dest.setByte(
            destOff + it,
            getByte(idxFrom + it)
        )
    }
}