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
import org.angproj.aux.util.chunkLoop
import kotlin.jvm.JvmStatic

public class Model internal constructor(
    size: Int, mem: MemoryManager<Model>
): Segment<Model>(size, mem) {

    /*init {
        // Must be BYTE
        require(typeSize == TypeSize.BYTE)
    }*/

    public fun create(size: Int, idxLimit: Int): Model = Model(size, memCtx)

    override fun close() { super.close { memCtx.recycle(this) } }

    //@PublishedApi
    internal val data: LongArray = LongArray(addMarginInTotalBytes(size))

    override fun getByte(index: Int): Byte {
        index.checkRangeByte<Unit>()
        return data.chunkGet<Unit>(index / TypeSize.long, index % TypeSize.long, TypeSize.byte).toByte()
    }

    override fun getShort(index: Int): Short {
        index.checkRangeShort<Unit>()
        return data.chunkGet<Unit>(index / TypeSize.long, index % TypeSize.long, TypeSize.short).toShort()
    }

    override fun getInt(index: Int): Int {
        index.checkRangeInt<Unit>()
        return data.chunkGet<Unit>(index / TypeSize.long, index % TypeSize.long, TypeSize.int).toInt()
    }

    override fun getLong(index: Int): Long {
        index.checkRangeLong<Unit>()
        return data.chunkGet<Unit>(index / TypeSize.long, index % TypeSize.long, TypeSize.long)
    }

    override fun getRevShort(index: Int): Short = swapShort<Unit>(getShort(index))

    override fun getRevInt(index: Int): Int = swapInt<Unit>(getInt(index))

    override fun getRevLong(index: Int): Long = swapLong<Unit>(getLong(index))

    override fun setByte(index: Int, value: Byte) {
        index.checkRangeByte<Unit>()
        data.chunkSet<Unit>(index / TypeSize.long, index % TypeSize.long, value.toLong(), byteMask, TypeSize.byte)
    }

    override fun setShort(index: Int, value: Short) {
        index.checkRangeShort<Unit>()
        data.shortSet<Unit>(index / TypeSize.long, index % TypeSize.long, value.toLong(), shortMask, TypeSize.short)
    }

    override fun setInt(index: Int, value: Int) {
        index.checkRangeInt<Unit>()
        data.chunkSet<Unit>(index / TypeSize.long, index % TypeSize.long, value.toLong(), intMask, TypeSize.int)
    }

    override fun setLong(index: Int, value: Long) {
        index.checkRangeLong<Unit>()
        data.chunkSet<Unit>(index / TypeSize.long, index % TypeSize.long, value, longMask, TypeSize.long)
    }

    override fun setRevShort(index: Int, value: Short) { setShort(index, swapShort<Unit>(value)) }

    override fun setRevInt(index: Int, value: Int) { setInt(index, swapInt<Unit>(value)) }

    override fun setRevLong(index: Int, value: Long) { setLong(index, swapLong<Unit>(value)) }

    private inline fun <reified R: Any> LongArray.chunkGet(off: Int, idx: Int, size: Int): Long = ((
            get(off) ushr (idx * TypeSize.long)) or if(idx > TypeSize.long - size) ((
            get(off + 1) and (-1L shl ((idx - size) * TypeSize.long)).inv()) shl ((
            TypeSize.long - idx) * TypeSize.long)) else 0)

    private inline fun <reified R: Any> LongArray.chunkSet(off: Int, idx: Int, value: Long, mask: Long, size: Int) {
        val pos = idx * TypeSize.long
        set(off, (get(off) and (mask shl pos).inv()) or (value shl pos))
        if(idx > TypeSize.long - size) set(off + 1, ((
                get(off + 1) and (-1L shl ((idx - size) * TypeSize.long))) or (
                value ushr ((TypeSize.long - idx) * TypeSize.long))))
    }

    private inline fun <reified R: Any> LongArray.shortSet(off: Int, idx: Int, value: Long, mask: Long, size: Int) {
        val pos = idx * TypeSize.long
        set(off, (get(off) and (mask shl pos).inv()) or (value shl pos))
        if(idx > TypeSize.long - size) set(off + 1, ((get(off + 1) and 0xff.inv()) or (value ushr TypeSize.long)))
    }

    @Deprecated("To disappear")
    private fun Long.getLeftSide(offset: Int, size: Int): Long = (
            this shl ((size - TypeSize.long - offset) * 8))

    @Deprecated("To disappear")
    private fun Long.getRightSide(offset: Int, size: Int): Long = (
            this ushr ((TypeSize.long - size - TypeSize.long - offset) * 8))

    @Deprecated("To disappear")
    private fun Long.setLeftSide(offset: Int, size: Int, value: Long): Long {
        val pos = (size - (TypeSize.long - offset)) * 8
        val mask = 0xffffffffffffffffuL.toLong() ushr ((TypeSize.long - size) * 8)
        return ((mask ushr pos).inv() and this) or (value ushr pos)
    }

    @Deprecated("To disappear")
    private fun Long.setRightSide(offset: Int, size: Int, value: Long): Long {
        val pos = (TypeSize.long - size - offset) * 8
        val mask = 0xffffffffffffffffuL.toLong() ushr ((TypeSize.long - size) * 8)
        return ((mask shl pos).inv() and this) or (value shl pos)
    }

    /**
     * Gets the high bytes from the end of the model, or the left side of a variable split between two models.
     * */
    @Deprecated("To disappear")
    public fun leftSideGet(offset: Int, size: Int): Long = getLong(
        this.size - TypeSize.long).getLeftSide(offset, size)

    /**
     * Gets the low bytes from the beginning of the model, or the right side of a variable split between two models.
     * */
    @Deprecated("To disappear")
    public fun rightSideGet(offset: Int, size: Int): Long = getLong(
        0).getRightSide(offset, size)

    /**
     * Sets the high bytes at the end of the model, or the left side of a variable split between two models.
     * */
    @Deprecated("To disappear")
    public fun leftSideSet(offset: Int, size: Int, value: Long) {
        val index = this.size - TypeSize.long
        setLong(index, getLong(index).setLeftSide(offset, size, value))
    }

    /**
     * Sets the low bytes at the beginning of the model, or the right side of a variable split between two models.
     * */
    @Deprecated("To disappear")
    public fun rightSideSet(offset: Int, size: Int, value: Long) {
        val index = this.size - TypeSize.long
        setLong(index, getLong(index).setRightSide(offset, size, value))
    }

    public companion object {
        //public val typeSize: TypeSize = TypeSize.BYTE

        public const val longMask: Long = -1
        public const val intMask: Long = 0xFFFFFFFF
        public const val shortMask: Long = 0xFFFF
        public const val byteMask: Long = 0xFF

        @JvmStatic
        public fun addMarginInTotalBytes(indexCount: Int): Int {
            require(indexCount >= 0)
            val remainder = indexCount % TypeSize.long
            return (indexCount + if(remainder == 0) 0 else TypeSize.long - remainder) / TypeSize.long
        }
    }
}

//@PublishedApi
internal inline fun Model.innerCopy(dest: Model, destOff: Int, idxFrom: Int, idxTo: Int) {
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

    val index = chunkLoop<Unit>(0, length, TypeSize.long) {
        dest.setLong(
            destOff + it,
            getLong(idxFrom + it)
        )
    }
    chunkLoop<Unit>(index, length, TypeSize.byte) {
        dest.setByte(
            destOff + it,
            getByte(idxFrom + it)
        )
    }
}