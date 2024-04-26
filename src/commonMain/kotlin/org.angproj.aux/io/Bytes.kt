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

import org.angproj.aux.buf.SpeedCopy

public open class Bytes(size: Int): Segment(size, typeSize) {

    final override val length: Int = SpeedCopy.addMarginInTotalBytes(size, typeSize)

    protected val data: ByteArray = ByteArray(length)

    override fun getByte(index: Int): Byte {
        if(index !in 0..<size) throw IllegalArgumentException("Out of bounds.")
        return data[index + idxOff]
    }

    override fun getShort(index: Int): Short {
        if(index !in 0..<(size-1)) throw IllegalArgumentException("Out of bounds.")
        return data.getShort(index + idxOff)
    }

    override fun getInt(index: Int): Int {
        if(index !in 0..<(size-3)) throw IllegalArgumentException("Out of bounds.")
        return data.getInt(index + idxOff)
    }

    override fun getLong(index: Int): Long {
        if(index !in 0..<(size-7)) throw IllegalArgumentException("Out of bounds.")
        return data.getLong(index + idxOff)
    }

    private fun ByteArray.getShort(index: Int): Short = (
            (this[index + 1].toInt() shl 8 and 0xff00) or
                    (this[index].toInt() and 0xff)).toShort()

    private fun ByteArray.getInt(index: Int): Int = (
            (this.getShort(index + 2).toInt() shl 16 and 0xffff0000.toInt()) or
                    (this.getShort(index).toInt() and 0xffff))

    private fun ByteArray.getLong(index: Int): Long = (
            (this.getInt(index + 4).toLong() shl 32 and 0xffffffff00000000u.toLong()) or
                    (this.getInt(index).toLong() and 0xffffffff))

    private fun ByteArray.revGetShort(index: Int): Short = (
            (this[index].toInt() shl 8 and 0xff00) or
                    (this[index + 1].toInt() and 0xff)).toShort()

    private fun ByteArray.revGetInt(index: Int): Int = (
            (this.revGetShort(index).toInt() shl 16 and 0xffff0000.toInt()) or
                    (this.revGetShort(index + 2).toInt() and 0xffff))

    private fun ByteArray.revGetLong(index: Int): Long = (
            (this.revGetInt(index).toLong() shl 32 and 0xffffffff00000000u.toLong()) or
                    (this.revGetInt(index + 4).toLong() and 0xffffffff))

    override fun close() {}

    override fun speedLongGet(idx: Int): Long {
        TODO("Not yet implemented")
    }

    override fun speedLongSet(idx: Int, value: Long) {
        TODO("Not yet implemented")
    }

    public companion object {
        public val typeSize: TypeSize = TypeSize.BYTE
    }

}