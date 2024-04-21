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

public interface MutableByteString : ByteString {

    public fun setByte(index: Int, value: Byte): Unit
    public fun setShort(index: Int, value: Short): Unit
    public fun setInt(index: Int, value: Int): Unit
    public fun setLong(index: Int, value: Long): Unit

    public operator fun set(index: Int, value: Byte): Unit = setByte(index, value)

    public fun Long.wholeByte(offset: Int, value: Byte): Long {
        val pos = offset * 8
        return ((0xffL shl pos).inv() and this) or (value.toLong() shl pos)
    }

    public fun Long.wholeShort(offset: Int, value: Short): Long {
        val pos = offset * 8
        return ((0xffffL shl pos).inv() and this) or (value.toLong() shl pos)
    }

    public fun Long.wholeInt(offset: Int, value: Int): Long {
        val pos = (offset) * 8
        return ((0xffffffffL shl pos).inv() and this) or (value.toLong() shl pos)
    }

    public fun Long.sideShortLeft(value: Short): Long = (this and 0x00ffffffffffffff) or (value.toLong() shl 56)

    public fun Long.sideShortRight(value: Short): Long = ((
            this and 0xff.inv()) or (value.toLong() ushr 8))

    public fun Long.sideIntLeft(offset: Int, value: Int): Long {
        val pos = offset * 8
        return (this and (-1L shl pos).inv()) or (value.toLong() shl pos)
    }


    public fun Long.sideIntRight(offset: Int, value: Int): Long = ((
            this and (-1L shl ((offset - ByteString.intSize) * 8))) or
            (value.toLong() ushr ((ByteString.longSize - offset) * 8)))

    public fun Long.sideLongLeft(offset: Int, value: Long): Long {
        val pos = offset * 8
        return (this and (-1L shl pos).inv()) or (value shl pos)
    }

    public fun Long.sideLongRight(offset: Int, value: Long): Long = ((
            this and (-1L shl ((offset - ByteString.longSize) * 8))) or
            (value ushr ((ByteString.longSize - offset) * 8)))
}