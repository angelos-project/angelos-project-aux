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

/**
 * Imported from angelos-project-buffer package.
 */

/**
 * Read Short at offset.
 *
 * @param offset
 * @return
 */
fun ByteArray.readShortAt(offset: Int): Short = (
        (this[offset + 1].toInt() shl 8 and 0xFF00) or (this[offset + 0].toInt() and 0xFF)
        ).toShort()

/**
 * Read UShort at offset.
 *
 * @param offset
 * @return
 */
fun ByteArray.readUShortAt(offset: Int): UShort = readShortAt(offset).toUShort()

/**
 * Read Char at offset.
 *
 * @param offset
 * @return
 */
fun ByteArray.readCharAt(offset: Int): Char = readShortAt(offset).toInt().toChar()

/**
 * Read Int at offset.
 *
 * @param offset
 * @return
 */
fun ByteArray.readIntAt(offset: Int): Int = (this[offset + 3].toInt() shl 24 and -0x1000000) or
        (this[offset + 2].toInt() shl 16 and 0xFF0000) or
        (this[offset + 1].toInt() shl 8 and 0xFF00) or
        (this[offset + 0].toInt() and 0xFF)

/**
 * Read UInt at offset.
 *
 * @param offset
 * @return
 */
fun ByteArray.readUIntAt(offset: Int): UInt = readIntAt(offset).toUInt()

/**
 * Read Long at offset.
 *
 * @param offset
 * @return
 */
fun ByteArray.readLongAt(offset: Int): Long = (this[offset + 7].toLong() shl 56 and -0x1000000_00000000) or
        (this[offset + 6].toLong() shl 48 and 0xFF0000_00000000) or
        (this[offset + 5].toLong() shl 40 and 0xFF00_00000000) or
        (this[offset + 4].toLong() shl 32 and 0xFF_00000000) or
        (this[offset + 3].toLong() shl 24 and 0xFF000000) or
        (this[offset + 2].toLong() shl 16 and 0xFF0000) or
        (this[offset + 1].toLong() shl 8 and 0xFF00) or
        (this[offset + 0].toLong() and 0xFF)

/**
 * Read ULong at offset.
 *
 * @param offset
 * @return
 */
fun ByteArray.readULongAt(offset: Int): ULong = readLongAt(offset).toULong()

/**
 * Read Float at offset.
 *
 * @param offset
 * @return
 */
fun ByteArray.readFloatAt(offset: Int): Float = Float.fromBits(readIntAt(offset))

/**
 * Read Double at offset.
 *
 * @param offset
 * @return
 */
fun ByteArray.readDoubleAt(offset: Int): Double = Double.fromBits(readLongAt(offset))

/**
 * Write Short at offset.
 *
 * @param offset
 * @param value
 */
fun ByteArray.writeShortAt(offset: Int, value: Short) {
    this[offset + 1] = (value.toInt() shr 8 and 0xFF).toByte()
    this[offset] = (value.toInt() and 0xFF).toByte()
}

/**
 * Write UShort at offset.
 *
 * @param offset
 * @param value
 */
fun ByteArray.writeUShortAt(offset: Int, value: UShort) = writeShortAt(offset, value.toShort())

/**
 * Write Char at offset.
 *
 * @param offset
 * @param value
 */
fun ByteArray.writeCharAt(offset: Int, value: Char) = writeShortAt(offset, value.code.toShort())

/**
 * Write Int at offset.
 *
 * @param offset
 * @param value
 */
fun ByteArray.writeIntAt(offset: Int, value: Int) {
    this[offset + 3] = (value shr 24 and 0xFF).toByte()
    this[offset + 2] = (value shr 16 and 0xFF).toByte()
    this[offset + 1] = (value shr 8 and 0xFF).toByte()
    this[offset] = (value and 0xFF).toByte()
}

/**
 * Write UInt at offset.
 *
 * @param offset
 * @param value
 */
fun ByteArray.writeUIntAt(offset: Int, value: UInt) = writeIntAt(offset, value.toInt())

/**
 * Write Long at offset.
 *
 * @param offset
 * @param value
 */
fun ByteArray.writeLongAt(offset: Int, value: Long) {
    this[offset + 7] = (value shr 56 and 0xFF).toByte()
    this[offset + 6] = (value shr 48 and 0xFF).toByte()
    this[offset + 5] = (value shr 40 and 0xFF).toByte()
    this[offset + 4] = (value shr 32 and 0xFF).toByte()
    this[offset + 3] = (value shr 24 and 0xFF).toByte()
    this[offset + 2] = (value shr 16 and 0xFF).toByte()
    this[offset + 1] = (value shr 8 and 0xFF).toByte()
    this[offset] = (value and 0xFF).toByte()
}

/**
 * Write ULong at offset.
 *
 * @param offset
 * @param value
 */
fun ByteArray.writeULongAt(offset: Int, value: ULong) = writeLongAt(offset, value.toLong())

/**
 * Write Float at offset.
 *
 * @param offset
 * @param value
 */
fun ByteArray.writeFloatAt(offset: Int, value: Float) = writeIntAt(offset, value.toBits())

/**
 * Write Double at offset.
 *
 * @param offset
 * @param value
 */
fun ByteArray.writeDoubleAt(offset: Int, value: Double) = writeLongAt(offset, value.toBits())