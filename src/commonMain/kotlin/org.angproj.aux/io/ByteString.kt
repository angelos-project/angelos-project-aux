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

public interface ByteString {

    public val size: Int

    public fun getByte(index: Int): Byte
    public fun getShort(index: Int): Short
    public fun getInt(index: Int): Int
    public fun getLong(index: Int): Long

    public operator fun get(index: Int): Byte = getByte(index)

    public fun Long.fullByte(offset: Int): Byte = (
            this ushr (offset * 8)).toByte()

    public fun Long.fullShort(offset: Int): Short = (
            this shr (offset * 8)).toShort()

    public fun Long.fullInt(offset: Int): Int = (
            this ushr (offset * 8)).toInt()

    public fun Long.joinShort(other: Long): Short = ((this ushr 56) or (other shl 8)).toShort()

    public fun Long.joinInt(offset: Int, other: Long): Int = ((
            this ushr (offset * 8)) or ((other and (-1L shl ((
            offset - intSize) * 8)).inv()) shl ((longSize - offset) * 8))).toInt()

    public fun Long.joinLong(offset: Int, other: Long): Long = ((
            this ushr (offset * 8)) or ((other and (-1L shl ((
            offset - longSize) * 8)).inv()) shl ((8 - offset) * 8)))

    public fun Long.reverse(): Long = (
            this.toInt().reverse().toLong() shl 32) or (
            this ushr 32).toInt().reverse().toLong()

    public fun Int.reverse(): Int = (
            this.toShort().reverse().toInt() shl 16) or (
            this ushr 16).toShort().reverse().toInt()

    public fun Short.reverse(): Short = (
            (this.toInt() shl 16) or (this.toInt() ushr 16)).toShort()

    public companion object {
        public const val byteSize: Int = Byte.SIZE_BYTES
        public const val shortSize: Int = Short.SIZE_BYTES
        public const val intSize: Int = Int.SIZE_BYTES
        public const val longSize: Int = Long.SIZE_BYTES
    }
}