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
package org.angproj.aux.util

public abstract class AbstractBufferAware {

    protected inline fun<reified R: Any> joinLong(
        u: Int, l: Int
    ): Long = ((u.toLong() shl 32 and -0x100000000) or (l.toLong() and 0xFFFFFFFF))
    protected inline fun<reified R: Any> joinInt(
        u: Short, l: Short
    ): Int = (u.toInt() shl 16 and -0x10000) or (l.toInt() and 0xFFFF)
    protected inline fun<reified R: Any> joinShort(
        u: Byte, l: Byte
    ): Short = ((u.toInt() shl 8 and 0xFF00) or (l.toInt() and 0xFF)).toShort()

    protected inline fun<reified R: Any> upperLong(value: Long): Int = (value ushr 32).toInt()
    protected inline fun<reified R: Any> upperInt(value: Int): Short = (value ushr 16).toShort()
    protected inline fun<reified R: Any> upperShort(value: Short): Byte = (value.toInt() ushr 8).toByte()

    protected inline fun<reified R: Any> lowerLong(value: Long): Int = value.toInt()
    protected inline fun<reified R: Any> lowerInt(value: Int): Short = value.toShort()
    protected inline fun<reified R: Any> lowerShort(value: Short): Byte = value.toByte()

    /**
     * Type swapping stuff
     * */
    protected inline fun<reified R: Any> swapLong(
        value: Long
    ): Long = joinLong<R>(swapInt<R>(lowerLong<R>(value)), swapInt<R>(upperLong<R>(value)))
    protected inline fun<reified R: Any> swapInt(
        value: Int)
    : Int = joinInt<R>(swapShort<R>(lowerInt<R>(value)), swapShort<R>(upperInt<R>(value)))
    protected inline fun<reified R: Any> swapShort(
        value: Short
    ): Short = joinShort<R>(lowerShort<R>(value), upperShort<R>(value))

    /**
     * Type conversion stuff
     * */
    protected inline fun<reified R: Any> Long.conv2uL(): ULong = this.toULong()
    protected inline fun<reified R: Any> ULong.conv2L(): Long = this.toLong()
    protected inline fun<reified R: Any> Long.conv2D(): Double = Double.fromBits(this)
    protected inline fun<reified R: Any> Double.conv2L(): Long = this.toRawBits()

    protected inline fun<reified R: Any> Int.conv2uI(): UInt = this.toUInt()
    protected inline fun<reified R: Any> UInt.conv2I(): Int = this.toInt()
    protected inline fun<reified R: Any> Int.conv2F(): Float = Float.fromBits(this)
    protected inline fun<reified R: Any> Float.conv2I(): Int = this.toRawBits()

    protected inline fun<reified R: Any> Short.conv2uS(): UShort = this.toUShort()
    protected inline fun<reified R: Any> UShort.conv2S(): Short = this.toShort()

    protected inline fun<reified R: Any> Byte.conv2uB(): UByte = this.toUByte()
    protected inline fun<reified R: Any> UByte.conv2B(): Byte = this.toByte()

    /**
     * ByteArray read and write stuff
     * */
    protected inline fun <reified R : Any> getLong(
        res: ByteArray, pos: Int
    ): Long = joinLong<R>(getInt<R>(res, pos + 4), getInt<R>(res, pos))

    protected inline fun <reified R : Any> getInt(
        res: ByteArray, pos: Int
    ): Int = joinInt<R>(getShort<R>(res, pos + 2), getShort<R>(res, pos))

    protected inline fun <reified R : Any> getShort(
        res: ByteArray, pos: Int
    ): Short = joinShort<R>(res[pos + 1], res[pos])

    protected inline fun <reified R : Any> setLong(res: ByteArray, pos: Int, value: Long): Unit {
        setInt<R>(res, pos + 4, upperLong<R>(value))
        setInt<R>(res, pos, lowerLong<R>(value))
    }

    protected inline fun <reified R : Any> setInt(res: ByteArray, pos: Int, value: Int): Unit {
        setShort<R>(res, pos + 2, upperInt<R>(value))
        setShort<R>(res, pos, lowerInt<R>(value))
    }

    protected inline fun <reified R : Any> setShort(res: ByteArray, pos: Int, value: Short): Unit {
        res[pos + 1] = upperShort<R>(value)
        res[pos] = lowerShort<R>(value)
    }

    protected inline fun <reified R : Any> getRevShort(
        res: ByteArray, pos: Int
    ): Short = joinShort<R>(res[pos], res[pos + 1])

    protected inline fun <reified R : Any> getRevInt(
        res: ByteArray, pos: Int
    ): Int = joinInt<R>(getRevShort<R>(res, pos), getRevShort<R>(res, pos + 2))

    protected inline fun <reified R : Any> getRevLong(
        res: ByteArray, pos: Int
    ): Long = joinLong<R>(getRevInt<R>(res, pos), getRevInt<R>(res, pos + 4))

    protected inline fun <reified R : Any> setRevLong(res: ByteArray, pos: Int, value: Long) {
        setRevInt<R>(res, pos, upperLong<R>(value))
        setRevInt<R>(res, pos + 4, lowerLong<R>(value))
    }

    protected inline fun <reified R : Any> setRevInt(res: ByteArray, pos: Int, value: Int) {
        setRevShort<R>(res, pos, upperInt<R>(value))
        setRevShort<R>(res, pos + 2, lowerInt<R>(value))
    }

    protected inline fun <reified R : Any> setRevShort(res: ByteArray, pos: Int, value: Short) {
        res[pos] = upperShort<R>(value)
        res[pos + 1] = lowerShort<R>(value)
    }

    /**
     * Public functions
     * */
}