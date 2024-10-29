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

public class Bytes internal constructor(
    size: Int, mem: MemoryManager<Bytes>
) : Segment<Bytes>(size, mem) {

    /*init {
        // Must be BYTE
        require(typeSize == TypeSize.BYTE)
    }*/

    public fun create(size: Int, idxLimit: Int): Bytes = Bytes(size, memCtx)

    //@PublishedApi
    internal val data: ByteArray = ByteArray(size)

    override fun close() { super.close { memCtx.recycle(this) } }

    override fun getByte(index: Int): Byte {
        index.checkRangeByte<Unit>()
        return data[index]
    }

    override fun getShort(index: Int): Short {
        index.checkRangeShort<Unit>()
        return data.getShort<Unit>(index)
    }

    override fun getInt(index: Int): Int {
        index.checkRangeInt<Unit>()
        return data.getInt<Unit>(index)
    }

    public override fun getLong(index: Int): Long {
        index.checkRangeLong<Unit>()
        return data.getLong<Unit>(index)
    }

    public override fun setByte(index: Int, value: Byte) {
        index.checkRangeByte<Unit>()
        data[index] = value
    }

    public override fun setShort(index: Int, value: Short) {
        index.checkRangeShort<Unit>()
        data.setShort<Unit>(index, value)
    }

    public override fun setInt(index: Int, value: Int) {
        index.checkRangeInt<Unit>()
        data.setInt<Unit>(index, value)
    }

    public override fun setLong(index: Int, value: Long) {
        index.checkRangeLong<Unit>()
        data.setLong<Unit>(index, value)
    }

    private inline fun <reified R : Any> ByteArray.getShort(index: Int): Short = (
            (this[index + 1].toInt() shl 8 and 0xff00) or
                    (this[index].toInt() and 0xff)).toShort()

    private inline fun <reified R : Any> ByteArray.getInt(index: Int): Int = (
            (this.getShort<Unit>(index + 2).toInt() shl 16 and 0xffff0000.toInt()) or
                    (this.getShort<Unit>(index).toInt() and 0xffff))

    private inline fun <reified R : Any> ByteArray.getLong(index: Int): Long = (
            (this.getInt<Unit>(index + 4).toLong() shl 32 and 0xffffffff00000000u.toLong()) or
                    (this.getInt<Unit>(index).toLong() and 0xffffffff))

    private inline fun <reified R : Any> ByteArray.setShort(index: Int, value: Short) {
        this[index + 1] = (value.toInt() ushr 8).toByte()
        this[index] = value.toByte()
    }

    private inline fun <reified R : Any> ByteArray.setInt(index: Int, value: Int) {
        this.setShort<Unit>(index + 2, (value ushr 16).toShort())
        this.setShort<Unit>(index, value.toShort())
    }

    private inline fun <reified R : Any> ByteArray.setLong(index: Int, value: Long) {
        this.setInt<Unit>(index + 4, (value ushr 32).toInt())
        this.setInt<Unit>(index, value.toInt())
    }

    /*public companion object {
        public val typeSize: TypeSize = TypeSize.BYTE
    }*/
}