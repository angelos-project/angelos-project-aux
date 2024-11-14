/**
 * Copyright (c) 2021-2023 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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

internal object I

internal inline fun<reified R: Any> I.joinLong(
    u: Int, l: Int
): Long = ((u.toLong() shl 32 and -0x100000000) or (l.toLong() and 0xFFFFFFFF))

internal inline fun<reified R: Any> I.joinInt(
    u: Short, l: Short
): Int = (u.toInt() shl 16 and -0x10000) or (l.toInt() and 0xFFFF)

internal inline fun<reified R: Any> I.joinShort(
    u: Byte, l: Byte
): Short = ((u.toInt() shl 8 and 0xFF00) or (l.toInt() and 0xFF)).toShort()

internal inline fun<reified R: Any> I.upperLong(value: Long): Int = (value ushr 32).toInt()
internal inline fun<reified R: Any> I.upperInt(value: Int): Short = (value ushr 16).toShort()
internal inline fun<reified R: Any> I.upperShort(value: Short): Byte = (value.toInt() ushr 8).toByte()

internal inline fun<reified R: Any> I.lowerLong(value: Long): Int = value.toInt()
internal inline fun<reified R: Any> I.lowerInt(value: Int): Short = value.toShort()
internal inline fun<reified R: Any> I.lowerShort(value: Short): Byte = value.toByte()

internal inline fun<reified R: Any> I.swapLong(
    value: Long
): Long = joinLong<R>(swapInt<R>(lowerLong<R>(value)), swapInt<R>(upperLong<R>(value)))

internal inline fun<reified R: Any> I.swapInt(
    value: Int
): Int = joinInt<R>(swapShort<R>(lowerInt<R>(value)), swapShort<R>(upperInt<R>(value)))

internal inline fun<reified R: Any> I.swapShort(
    value: Short
): Short = joinShort<R>(lowerShort<R>(value), upperShort<R>(value))

internal inline fun<reified R: Any> I.convL2UL(value: Long): ULong = value.toULong()
internal inline fun<reified R: Any> I.convUL2L(value: ULong): Long = value.toLong()
internal inline fun<reified R: Any> I.convL2D(value: Long): Double = Double.fromBits(value)
internal inline fun<reified R: Any> I.convD2L(value: Double): Long = value.toRawBits()

internal inline fun<reified R: Any> I.convI2UI(value: Int): UInt = value.toUInt()
internal inline fun<reified R: Any> I.convUI2I(value: UInt): Int = value.toInt()
internal inline fun<reified R: Any> I.convI2F(value: Int): Float = Float.fromBits(value)
internal inline fun<reified R: Any> I.convF2I(value: Float): Int = value.toRawBits()

internal inline fun<reified R: Any> I.convS2US(value: Short): UShort = value.toUShort()
internal inline fun<reified R: Any> I.convUS2S(value: UShort): Short = value.toShort()

internal inline fun<reified R: Any> I.convB2UB(value: Byte): UByte = value.toUByte()
internal inline fun<reified R: Any> I.convUB2B(value: UByte): Byte = value.toByte()

/**
 * Imported from angelos-project-buffer package.
 */

/**
 * Reverse byte order of Short.
 *
 * @param value
 * @return
 */
internal fun reverseShort(value: Short): Short = (
        (value.toInt() shl 8 and 0xFF00) or (value.toInt() shr 8 and 0xFF)).toShort()

/**
 * Reverse byte order of Int.
 *
 * @param value
 * @return
 */
internal fun reverseInt(value: Int): Int = (value shl 24 and -0x1000000) or
        (value shl 8 and 0xFF0000) or
        (value shr 8 and 0xFF00) or
        (value shr 24 and 0xFF)

/**
 * Reverse byte order of Long.
 *
 * @param value
 * @return
 */
internal fun reverseLong(value: Long): Long = (value shl 56 and -0x1000000_00000000) or
        (value shl 40 and 0xFF0000_00000000) or
        (value shl 24 and 0xFF00_00000000) or
        (value shl 8 and 0xFF_00000000) or
        (value shr 8 and 0xFF000000) or
        (value shr 24 and 0xFF0000) or
        (value shr 40 and 0xFF00) or
        (value shr 56 and 0xFF)

internal expect fun getCurrentEndian(): Endian