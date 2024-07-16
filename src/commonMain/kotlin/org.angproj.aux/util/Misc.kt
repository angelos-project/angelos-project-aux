/**
 * Copyright (c) 2023-2024 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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

import kotlin.math.absoluteValue

public fun encodeToHex(data: ByteArray): String = BinHex.encodeToHex(data)

public fun decodeFromHex(hex: String): ByteArray = BinHex.decodeToBin(hex)

public fun Int.floorMod(other: Int): Int = this.absoluteValue.mod(other.absoluteValue)
public fun Long.floorMod(other: Long): Long = this.absoluteValue.mod(other.absoluteValue)

public fun bitArrayOf(value: ByteArray): BitArray = BitArray(value)

public inline fun <reified T: Reifiable> chunkLoop(index: Int, length: Int, slice: Int, action: (Int) -> Unit): Int {
    val steps = (length - index) / slice
    val size = steps * slice
    if (steps > 0) (index until (index + size) step slice).forEach { action(it) }
    return index + size
}

public inline fun <reified T: Reifiable> chunkSimple(
    index: Int, length: Int, slice: Int, action: (Int) -> Unit
): Int = when (length - index >= slice) {
    true -> (index + slice).also { action(index) }
    else -> index
}

internal object withNumeral {
    inline fun<reified R: Number> joinLong(u: Int, l: Int): Long = ((u.toLong() shl 32 and -0x100000000) or (l.toLong() and 0xFFFFFFFF))
    inline fun<reified R: Number> joinInt(u: Short, l: Short): Int = (u.toInt() shl 16 and -0x10000) or (l.toInt() and 0xFFFF)
    inline fun<reified R: Number> joinShort(u: Byte, l: Byte): Short = ((u.toInt() shl 8 and 0xFF00) or (l.toInt() and 0xFF)).toShort()

    inline fun<reified R: Number> upperLong(value: Long): Int = (value ushr 32).toInt()
    inline fun<reified R: Number> upperInt(value: Int): Short = (value ushr 16).toShort()
    inline fun<reified R: Number> upperShort(value: Short): Byte = (value.toInt() ushr 8).toByte()

    inline fun<reified R: Number> lowerLong(value: Long): Int = value.toInt()
    inline fun<reified R: Number> lowerInt(value: Int): Short = value.toShort()
    inline fun<reified R: Number> lowerShort(value: Short): Byte = value.toByte()

    inline fun<reified R: Number> swapLong(value: Long): Long = joinLong<R>(swapInt<R>(lowerLong<R>(value)), swapInt<R>(upperLong<R>(value)))
    inline fun<reified R: Number> swapInt(value: Int): Int = joinInt<R>(swapShort<R>(lowerInt<R>(value)), swapShort<R>(upperInt<R>(value)))
    inline fun<reified R: Number> swapShort(value: Short): Short = joinShort<R>(lowerShort<R>(value), upperShort<R>(value))

    inline fun<reified R: Number> Long.convL2UL(): ULong = this.toULong()
    inline fun<reified R: Number> ULong.convUL2L(): Long = this.toLong()
    inline fun<reified R: Number> Long.convL2D(): Double = Double.fromBits(this)
    inline fun<reified R: Number> Double.convD2L(): Long = this.toBits()

    inline fun<reified R: Number> Int.convI2UI(): UInt = this.toUInt()
    inline fun<reified R: Number> UInt.convUI2I(): Int = this.toInt()
    inline fun<reified R: Number> Int.convI2F(): Float = Float.fromBits(this)
    inline fun<reified R: Number> Float.convF2I(): Int = this.toBits()

    inline fun<reified R: Number> Short.convS2US(): UShort = this.toUShort()
    inline fun<reified R: Number> UShort.convUS2S(): Short = this.toShort()

    inline fun<reified R: Number> Byte.convB2UB(): UByte = this.toUByte()
    inline fun<reified R: Number> UByte.convUB2B(): Byte = this.toByte()

    inline operator fun<reified E> invoke(block: withNumeral.() -> E): E = this.block()
    inline operator fun<reified E, T> invoke(it: T, block: withNumeral.(it: T) -> E): E = this.block(it)
}