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

import org.angproj.aux.buf.Reifiable
import org.angproj.aux.buf.Reify

public abstract class AbstractBytes protected constructor(
    size: Int, idxOff: Int, idxEnd: Int
): Segment(size, typeSize, idxOff, idxEnd) {

    init {
        // Must be BYTE
        require(typeSize == TypeSize.BYTE)
    }

    protected val data: ByteArray = ByteArray(length)

    abstract override fun create(size: Int, idxOff: Int, idxEnd: Int): AbstractBytes

    override fun speedCopy(ctx: Context): AbstractBytes {
        val copy = create(ctx.newSize, ctx.newIdxOff, ctx.newIdxEnd)

        val baseOffset = (ctx.baseIdx * idxSize.size) + ctx.newIdxOff
        val copyOffset = ctx.newIdxOff

        data.copyInto(copy.data, copyOffset, baseOffset, baseOffset + ctx.newSize)
        return copy
    }

    override fun getByte(index: Int): Byte {
        index.checkRangeByte<Reify>()
        return data[index + idxOff]
    }

    override fun getShort(index: Int): Short {
        index.checkRangeShort<Reify>()
        return data.getShort<Reify>(index + idxOff)
    }

    override fun getInt(index: Int): Int {
        index.checkRangeInt<Reify>()
        return data.getInt<Reify>(index + idxOff)
    }

    public override fun getLong(index: Int): Long {
        index.checkRangeLong<Reify>()
        return data.getLong<Reify>(index + idxOff)
    }

    public override fun setByte(index: Int, value: Byte) {
        index.checkRangeByte<Reify>()
        data[index + idxOff] = value
    }

    public override fun setShort(index: Int, value: Short) {
        index.checkRangeShort<Reify>()
        data.setShort<Reify>(index + idxOff, value)
    }

    public override fun setInt(index: Int, value: Int) {
        index.checkRangeInt<Reify>()
        data.setInt<Reify>(index + idxOff, value)
    }

    public override fun setLong(index: Int, value: Long) {
        index.checkRangeLong<Reify>()
        data.setLong<Reify>(index + idxOff, value)
    }

    internal inline fun <reified T: Reifiable> ByteArray.getShort(index: Int): Short = (
            (this[index + 1].toInt() shl 8 and 0xff00) or
                    (this[index].toInt() and 0xff)).toShort()

    internal inline fun <reified T: Reifiable> ByteArray.getInt(index: Int): Int = (
            (this.getShort<Reify>(index + 2).toInt() shl 16 and 0xffff0000.toInt()) or
                    (this.getShort<Reify>(index).toInt() and 0xffff))

    internal inline fun <reified T: Reifiable> ByteArray.getLong(index: Int): Long = (
            (this.getInt<Reify>(index + 4).toLong() shl 32 and 0xffffffff00000000u.toLong()) or
                    (this.getInt<Reify>(index).toLong() and 0xffffffff))

    internal inline fun <reified T: Reifiable> ByteArray.setShort(index: Int, value: Short) {
        this[index + 1] = (value.toInt() ushr 8).toByte()
        this[index] = value.toByte()
    }

    internal inline fun <reified T: Reifiable> ByteArray.setInt(index: Int, value: Int) {
        this.setShort<Reify>(index + 2, (value ushr 16).toShort())
        this.setShort<Reify>(index, value.toShort())
    }

    internal inline fun <reified T: Reifiable> ByteArray.setLong(index: Int, value: Long)  {
        this.setInt<Reify>(index + 4, (value ushr 32).toInt())
        this.setInt<Reify>(index, value.toInt())
    }

    private fun ByteArray.revGetShort(index: Int): Short = (
            (this[index].toInt() shl 8 and 0xff00) or
                    (this[index + 1].toInt() and 0xff)).toShort()

    private fun ByteArray.revGetInt(index: Int): Int = (
            (this.revGetShort(index).toInt() shl 16 and 0xffff0000.toInt()) or
                    (this.revGetShort(index + 2).toInt() and 0xffff))

    private fun ByteArray.revGetLong(index: Int): Long = (
            (this.revGetInt(index).toLong() shl 32 and 0xffffffff00000000u.toLong()) or
                    (this.revGetInt(index + 4).toLong() and 0xffffffff))

    private fun ByteArray.revSetShort(index: Int, value: Short) {
        this[index] = (value.toInt() ushr 8).toByte()
        this[index + 1] = value.toByte()
    }

    private fun ByteArray.revSetInt(index: Int, value: Int) {
        this.revSetShort(index, (value ushr 16).toShort())
        this.revSetShort(index + 2, value.toShort())
    }

    private fun ByteArray.revSetLong(index: Int, value: Long)  {
        this.revSetInt(index, (value ushr 32).toInt())
        this.revSetInt(index + 4, value.toInt())
    }

    public companion object {
        public val typeSize: TypeSize = TypeSize.BYTE
    }
}