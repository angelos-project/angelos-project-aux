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
import org.angproj.aux.util.Reifiable
import org.angproj.aux.util.Reify

public abstract class AbstractBytes protected constructor(
    size: Int, idxLimit: Int
): Segment(size, typeSize, idxLimit) {

    init {
        // Must be BYTE
        require(typeSize == TypeSize.BYTE)
    }

    @PublishedApi
    internal val data: ByteArray = ByteArray(length)

    abstract override fun create(size: Int, idxLimit: Int): AbstractBytes

    override fun getByte(index: Int): Byte {
        index.checkRangeByte<Reify>()
        return data[index]
    }

    override fun getShort(index: Int): Short {
        index.checkRangeShort<Reify>()
        return data.getShort<Reify>(index)
    }

    override fun getInt(index: Int): Int {
        index.checkRangeInt<Reify>()
        return data.getInt<Reify>(index)
    }

    public override fun getLong(index: Int): Long {
        index.checkRangeLong<Reify>()
        return data.getLong<Reify>(index)
    }

    public override fun setByte(index: Int, value: Byte) {
        index.checkRangeByte<Reify>()
        data[index] = value
    }

    public override fun setShort(index: Int, value: Short) {
        index.checkRangeShort<Reify>()
        data.setShort<Reify>(index, value)
    }

    public override fun setInt(index: Int, value: Int) {
        index.checkRangeInt<Reify>()
        data.setInt<Reify>(index, value)
    }

    public override fun setLong(index: Int, value: Long) {
        index.checkRangeLong<Reify>()
        data.setLong<Reify>(index, value)
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

@PublishedApi
internal inline fun<reified T: AbstractBytes> T.innerCopy(dest: T, destOff: Int, idxFrom: Int, idxTo: Int) {
    data.copyInto(dest.data, destOff, idxFrom, idxTo)
}