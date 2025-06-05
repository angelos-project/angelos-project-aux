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

public typealias Noop = () -> Unit

public fun noop(): Unit = Unit

/**
 * Read Short at offset.
 *
 * @param offset
 * @return
 */
@Deprecated("Use UtilityAware instead.", ReplaceWith("UtilityAware"))
public fun ByteArray.readShortAt(offset: Int): Short = withUtility(this) { it.readShortAt(offset) }

/**
 * Read UShort at offset.
 *
 * @param offset
 * @return
 */
@Deprecated("Use UtilityAware instead.", ReplaceWith("UtilityAware"))
public fun ByteArray.readUShortAt(offset: Int): UShort = withUtility(this) { it.readUShortAt(offset) }

/**
 * Read Char at offset.
 *
 * @param offset
 * @return
 */
@Deprecated("Use UtilityAware instead.", ReplaceWith("UtilityAware"))
public fun ByteArray.readCharAt(offset: Int): Char = readShortAt(offset).toInt().toChar()

/**
 * Read Int at offset.
 *
 * @param offset
 * @return
 */
@Deprecated("Use UtilityAware instead.", ReplaceWith("UtilityAware"))
public fun ByteArray.readIntAt(offset: Int): Int = withUtility(this) { it.readIntAt(offset) }

/**
 * Read UInt at offset.
 *
 * @param offset
 * @return
 */
@Deprecated("Use UtilityAware instead.", ReplaceWith("UtilityAware"))
public fun ByteArray.readUIntAt(offset: Int): UInt = withUtility(this) { it.readUIntAt(offset) }

/**
 * Read Long at offset.
 *
 * @param offset
 * @return
 */
@Deprecated("Use UtilityAware instead.", ReplaceWith("UtilityAware"))
public fun ByteArray.readLongAt(offset: Int): Long = withUtility(this) { it.readLongAt(offset) }

/**
 * Read ULong at offset.
 *
 * @param offset
 * @return
 */
@Deprecated("Use UtilityAware instead.", ReplaceWith("UtilityAware"))
public fun ByteArray.readULongAt(offset: Int): ULong = withUtility(this) { it.readULongAt(offset) }

/**
 * Read Float at offset.
 *
 * @param offset
 * @return
 */
@Deprecated("Use UtilityAware instead.", ReplaceWith("UtilityAware"))
public fun ByteArray.readFloatAt(offset: Int): Float = withUtility(this) { it.readFloatAt(offset) }

/**
 * Read Double at offset.
 *
 * @param offset
 * @return
 */
@Deprecated("Use UtilityAware instead.", ReplaceWith("UtilityAware"))
public fun ByteArray.readDoubleAt(offset: Int): Double = withUtility(this) { it.readDoubleAt(offset) }

/**
 * Write Short at offset.
 *
 * @param offset
 * @param value
 */
@Deprecated("Use UtilityAware instead.", ReplaceWith("UtilityAware"))
public fun ByteArray.writeShortAt(offset: Int, value: Short): Unit = withUtility(this) { it.writeShortAt(offset, value) }

/**
 * Write UShort at offset.
 *
 * @param offset
 * @param value
 */
@Deprecated("Use UtilityAware instead.", ReplaceWith("UtilityAware"))
public fun ByteArray.writeUShortAt(offset: Int, value: UShort): Unit = withUtility(this) { it.writeUShortAt(offset, value) }

    /**
 * Write Char at offset.
 *
 * @param offset
 * @param value
 */
    @Deprecated("Use UtilityAware instead.", ReplaceWith("UtilityAware"))
public fun ByteArray.writeCharAt(offset: Int, value: Char): Unit = writeShortAt(offset, value.code.toShort())

/**
 * Write Int at offset.
 *
 * @param offset
 * @param value
 */
@Deprecated("Use UtilityAware instead.", ReplaceWith("UtilityAware"))
public fun ByteArray.writeIntAt(offset: Int, value: Int): Unit = withUtility(this) { it.writeIntAt(offset, value) }

    /**
 * Write UInt at offset.
 *
 * @param offset
 * @param value
 */
    @Deprecated("Use UtilityAware instead.", ReplaceWith("UtilityAware"))
public fun ByteArray.writeUIntAt(offset: Int, value: UInt): Unit = withUtility(this) { it.writeUIntAt(offset, value) }

/**
 * Write Long at offset.
 *
 * @param offset
 * @param value
 */
@Deprecated("Use UtilityAware instead.", ReplaceWith("UtilityAware"))
public fun ByteArray.writeLongAt(offset: Int, value: Long): Unit = withUtility(this) { it.writeLongAt(offset, value) }

/**
 * Write ULong at offset.
 *
 * @param offset
 * @param value
 */
@Deprecated("Use UtilityAware instead.", ReplaceWith("UtilityAware"))
public fun ByteArray.writeULongAt(offset: Int, value: ULong): Unit = withUtility(this) { it.writeULongAt(offset, value) }

/**
 * Write Float at offset.
 *
 * @param offset
 * @param value
 */
@Deprecated("Use UtilityAware instead.", ReplaceWith("UtilityAware"))
public fun ByteArray.writeFloatAt(offset: Int, value: Float): Unit = withUtility(this) { it.writeFloatAt(offset, value) }

/**
 * Write Double at offset.
 *
 * @param offset
 * @param value
 */
@Deprecated("Use UtilityAware instead.", ReplaceWith("UtilityAware"))
public fun ByteArray.writeDoubleAt(offset: Int, value: Double): Unit = withUtility(this) { it.writeDoubleAt(offset, value) }

/**
 * Convert ShortArray to ByteArray.
 * */
public fun ShortArray.toByteArray(): ByteArray = ByteArray(Short.SIZE_BYTES * this.size).also {
    this.forEachIndexed { idx, value -> it.writeShortAt(idx * Short.SIZE_BYTES, value) }
}


/**
 * Takes a UTF-8 leading octet as a Byte and check what size the multibyte character has.
 * Also works as validation of the first octet in a UTF-8 binary octet sequence.
 */

public fun ByteArray.readGlyphAt(offset: Int): CodePoint = withUnicode(this) {
    var pos = offset
    return readGlyphBlk(size - pos) { it[pos++] }
}

public fun ByteArray.writeGlyphAt(offset: Int, value: CodePoint): Int = withUnicode(this) {
    var pos = offset
    return writeGlyphBlk(value, size - pos) { it2 -> it[pos++] = it2 }
}


/**
 * Convert IntArray to ByteArray.
 * */
@Deprecated("Cease to exist.", ReplaceWith("Nothing"))
public fun IntArray.toByteArray(): ByteArray = ByteArray(Int.SIZE_BYTES * this.size).also {
    this.forEachIndexed { idx, value -> it.writeIntAt(idx * Int.SIZE_BYTES, value) }
}

/**
 * Convert LongArray to ByteArray.
 * */
@Deprecated("Cease to exist.", ReplaceWith("Nothing"))
public fun LongArray.toByteArray(): ByteArray = ByteArray(Long.SIZE_BYTES * this.size).also {
    this.forEachIndexed { idx, value -> it.writeLongAt(idx * Long.SIZE_BYTES, value) }
}

/**
 * Convert FloatArray to ByteArray.
 * */
@Deprecated("Cease to exist.", ReplaceWith("Nothing"))
public fun FloatArray.toByteArray(): ByteArray = ByteArray(Float.SIZE_BYTES * this.size).also {
    this.forEachIndexed { idx, value -> it.writeFloatAt(idx * Float.SIZE_BYTES, value) }
}

/**
 * Convert DoubleArray to ByteArray.
 * */
@Deprecated("Cease to exist.", ReplaceWith("Nothing"))
public fun DoubleArray.toByteArray(): ByteArray = ByteArray(Double.SIZE_BYTES * this.size).also {
    this.forEachIndexed { idx, value -> it.writeDoubleAt(idx * Double.SIZE_BYTES, value) }
}
