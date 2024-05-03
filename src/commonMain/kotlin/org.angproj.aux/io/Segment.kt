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

import org.angproj.aux.buf.*
import org.angproj.aux.buf.Reifiable

public abstract class Segment(
    size: Int, idxSize: TypeSize, idxOff: Int, idxEnd: Int
): AbstractSpeedCopy(size, idxSize, idxOff, idxEnd), ByteString {

    final override val length: Int = SpeedCopy.addMarginInTotalBytes(idxEnd, idxSize)
    final override val marginSized: Int = SpeedCopy.addMarginByIndexType(idxEnd, idxSize)

    public fun Long.getLeftSide(offset: Int, size: Int): Long = (
            this shl ((size - TypeSize.long - offset) * 8))

    public fun Long.getRightSide(offset: Int, size: Int): Long = (
            this ushr ((TypeSize.long - size - TypeSize.long - offset) * 8))

    public fun Long.setLeftSide(offset: Int, size: Int, value: Long): Long {
        val pos = (size - (TypeSize.long - offset)) * 8
        val mask = 0xffffffffffffffffuL.toLong() ushr ((TypeSize.long - size) * 8)
        return ((mask ushr pos).inv() and this) or (value ushr pos)
    }

    public fun Long.setRightSide(offset: Int, size: Int, value: Long): Long {
        val pos = (TypeSize.long - size - offset) * 8
        val mask = 0xffffffffffffffffuL.toLong() ushr ((TypeSize.long - size) * 8)
        return ((mask shl pos).inv() and this) or (value shl pos)
    }

    public inline fun <reified T: Reifiable>Long.fullByte(offset: Int): Byte = (
            this ushr (offset * 8)).toByte()

    public inline fun <reified T: Reifiable> Long.fullShort(offset: Int): Short = (
            this shr (offset * 8)).toShort()

    public inline fun <reified T: Reifiable> Long.fullInt(offset: Int): Int = (
            this ushr (offset * 8)).toInt()

    public inline fun <reified T: Reifiable> Long.joinShort(other: Long): Short = ((this ushr 56) or (other shl 8)).toShort()

    public inline fun <reified T: Reifiable> Long.joinInt(offset: Int, other: Long): Int = ((
            this ushr (offset * 8)) or ((other and (-1L shl ((
            offset - TypeSize.int) * 8)).inv()) shl ((TypeSize.long - offset) * 8))).toInt()

    public inline fun <reified T: Reifiable> Long.joinLong(offset: Int, other: Long): Long = ((
            this ushr (offset * 8)) or ((other and (-1L shl ((
            offset - TypeSize.long) * 8)).inv()) shl ((8 - offset) * 8)))

    public inline fun <reified T: Reifiable> Long.wholeByte(offset: Int, value: Byte): Long {
        val pos = offset * 8
        return ((0xffL shl pos).inv() and this) or (value.toLong() shl pos)
    }

    public inline fun <reified T: Reifiable> Long.wholeShort(offset: Int, value: Short): Long {
        val pos = offset * 8
        return ((0xffffL shl pos).inv() and this) or (value.toLong() shl pos)
    }

    public inline fun <reified T: Reifiable> Long.wholeInt(offset: Int, value: Int): Long {
        val pos = (offset) * 8
        return ((0xffffffffL shl pos).inv() and this) or (value.toLong() shl pos)
    }

    public inline fun <reified T: Reifiable> Long.sideShortLeft(value: Short): Long = (this and 0x00ffffffffffffff) or (value.toLong() shl 56)

    public inline fun <reified T: Reifiable> Long.sideShortRight(value: Short): Long = ((
            this and 0xff.inv()) or (value.toLong() ushr 8))

    public inline fun <reified T: Reifiable> Long.sideIntLeft(offset: Int, value: Int): Long {
        val pos = offset * 8
        return (this and (-1L shl pos).inv()) or (value.toLong() shl pos)
    }

    public inline fun <reified T: Reifiable> Long.sideIntRight(offset: Int, value: Int): Long = ((
            this and (-1L shl ((offset - TypeSize.int) * 8))) or
            (value.toLong() ushr ((TypeSize.long - offset) * 8)))

    public inline fun <reified T: Reifiable> Long.sideLongLeft(offset: Int, value: Long): Long {
        val pos = offset * 8
        return (this and (-1L shl pos).inv()) or (value shl pos)
    }

    public inline fun <reified T: Reifiable> Long.sideLongRight(offset: Int, value: Long): Long = ((
            this and (-1L shl ((offset - TypeSize.long) * 8))) or
            (value ushr ((TypeSize.long - offset) * 8)))

    public inline fun <reified T: Reifiable> Long.reverse(): Long = (
            this.toInt().reverse<Reify>().toLong() shl 32) or (
            this ushr 32).toInt().reverse<Reify>().toLong()

    public inline fun <reified T: Reifiable> Int.reverse(): Int = (
            this.toShort().reverse<Reify>().toInt() shl 16) or (
            this ushr 16).toShort().reverse<Reify>().toInt()

    public inline fun <reified T: Reifiable> Short.reverse(): Short = (
            (this.toInt() shl 16) or (this.toInt() ushr 16)).toShort()
}

@PublishedApi
internal inline fun <reified S: Segment> S.copyOfRange(idxFrom: Int, idxTo: Int): S = innerCopyOfRange(idxFrom, idxTo)