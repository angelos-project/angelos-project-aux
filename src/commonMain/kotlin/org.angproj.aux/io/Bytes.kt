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

public open class Bytes protected constructor(
    size: Int, idxOff: Int, idxEnd: Int
): Segment(size, typeSize, idxOff, idxEnd) {

    public constructor(size: Int) : this(size, 0, size)

    init {
        // Must be BYTE
        require(Memory.typeSize == TypeSize.BYTE)
    }

    protected val data: ByteArray = ByteArray(length)

    override fun create(size: Int, idxOff: Int, idxEnd: Int): Bytes = Bytes(size, idxOff, idxEnd)

    override fun copyOf(): Bytes {
        TODO("Not yet implemented")
    }

    override fun copyOfRange(idxFrom: Int, idxTo: Int): Bytes = copyOfRange2(idxFrom, idxTo)

    protected fun copyOfRange2(idxFrom: Int, idxTo: Int): Bytes {
        val factor = TypeSize.long / idxSize.size
        //val newIdxOff = idxFrom % factor
        val newIdxOff = (idxOff + idxFrom) % factor
        val newSize = idxTo - idxFrom
        val newIdxEnd = newIdxOff + newSize
        val baseIdx = (idxOff + idxFrom) - newIdxOff

        val copy = create(newSize, newIdxOff, newIdxEnd)

        val basePtr = (baseIdx * idxSize.size) + newIdxOff
        val copyPtr = newIdxOff

        data.copyInto(copy.data, copyPtr, basePtr, basePtr + newSize)
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

    internal inline fun <reified T: Reifiable> ByteArray.getShort(index: Int): Short = (
            (this[index + 1].toInt() shl 8 and 0xff00) or
                    (this[index].toInt() and 0xff)).toShort()

    internal inline fun <reified T: Reifiable> ByteArray.getInt(index: Int): Int = (
            (this.getShort<Reify>(index + 2).toInt() shl 16 and 0xffff0000.toInt()) or
                    (this.getShort<Reify>(index).toInt() and 0xffff))

    internal inline fun <reified T: Reifiable> ByteArray.getLong(index: Int): Long = (
            (this.getInt<Reify>(index + 4).toLong() shl 32 and 0xffffffff00000000u.toLong()) or
                    (this.getInt<Reify>(index).toLong() and 0xffffffff))

    private fun ByteArray.revGetShort(index: Int): Short = (
            (this[index].toInt() shl 8 and 0xff00) or
                    (this[index + 1].toInt() and 0xff)).toShort()

    private fun ByteArray.revGetInt(index: Int): Int = (
            (this.revGetShort(index).toInt() shl 16 and 0xffff0000.toInt()) or
                    (this.revGetShort(index + 2).toInt() and 0xffff))

    private fun ByteArray.revGetLong(index: Int): Long = (
            (this.revGetInt(index).toLong() shl 32 and 0xffffffff00000000u.toLong()) or
                    (this.revGetInt(index + 4).toLong() and 0xffffffff))

    public companion object {
        public val typeSize: TypeSize = TypeSize.BYTE
    }
}