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
@Deprecated("Use BufferAware instead.", ReplaceWith("BufferAware"))
public fun ByteArray.readShortAt(offset: Int): Short = withBufferAware(this) { it.readShortAt(offset) }

/**
 * Read UShort at offset.
 *
 * @param offset
 * @return
 */
@Deprecated("Use BufferAware instead.", ReplaceWith("BufferAware"))
public fun ByteArray.readUShortAt(offset: Int): UShort = withBufferAware(this) { it.readUShortAt(offset) }

/**
 * Read Char at offset.
 *
 * @param offset
 * @return
 */
@Deprecated("Use BufferAware instead.", ReplaceWith("BufferAware"))
public fun ByteArray.readCharAt(offset: Int): Char = readShortAt(offset).toInt().toChar()

/**
 * Read Int at offset.
 *
 * @param offset
 * @return
 */
@Deprecated("Use BufferAware instead.", ReplaceWith("BufferAware"))
public fun ByteArray.readIntAt(offset: Int): Int = withBufferAware(this) { it.readIntAt(offset) }

/**
 * Read UInt at offset.
 *
 * @param offset
 * @return
 */
@Deprecated("Use BufferAware instead.", ReplaceWith("BufferAware"))
public fun ByteArray.readUIntAt(offset: Int): UInt = withBufferAware(this) { it.readUIntAt(offset) }

/**
 * Read Long at offset.
 *
 * @param offset
 * @return
 */
@Deprecated("Use BufferAware instead.", ReplaceWith("BufferAware"))
public fun ByteArray.readLongAt(offset: Int): Long = withBufferAware(this) { it.readLongAt(offset) }

/**
 * Read ULong at offset.
 *
 * @param offset
 * @return
 */
@Deprecated("Use BufferAware instead.", ReplaceWith("BufferAware"))
public fun ByteArray.readULongAt(offset: Int): ULong = withBufferAware(this) { it.readULongAt(offset) }

/**
 * Read Float at offset.
 *
 * @param offset
 * @return
 */
@Deprecated("Use BufferAware instead.", ReplaceWith("BufferAware"))
public fun ByteArray.readFloatAt(offset: Int): Float = withBufferAware(this) { it.readFloatAt(offset) }

/**
 * Read Double at offset.
 *
 * @param offset
 * @return
 */
@Deprecated("Use BufferAware instead.", ReplaceWith("BufferAware"))
public fun ByteArray.readDoubleAt(offset: Int): Double = withBufferAware(this) { it.readDoubleAt(offset) }

/**
 * Write Short at offset.
 *
 * @param offset
 * @param value
 */
@Deprecated("Use BufferAware instead.", ReplaceWith("BufferAware"))
public fun ByteArray.writeShortAt(offset: Int, value: Short): Unit = withBufferAware(this) { it.writeShortAt(offset, value) }

/**
 * Write UShort at offset.
 *
 * @param offset
 * @param value
 */
@Deprecated("Use BufferAware instead.", ReplaceWith("BufferAware"))
public fun ByteArray.writeUShortAt(offset: Int, value: UShort): Unit = withBufferAware(this) { it.writeUShortAt(offset, value) }

    /**
 * Write Char at offset.
 *
 * @param offset
 * @param value
 */
@Deprecated("Use BufferAware instead.", ReplaceWith("BufferAware"))
public fun ByteArray.writeCharAt(offset: Int, value: Char): Unit = writeShortAt(offset, value.code.toShort())

/**
 * Write Int at offset.
 *
 * @param offset
 * @param value
 */
@Deprecated("Use BufferAware instead.", ReplaceWith("BufferAware"))
public fun ByteArray.writeIntAt(offset: Int, value: Int): Unit = withBufferAware(this) { it.writeIntAt(offset, value) }

    /**
 * Write UInt at offset.
 *
 * @param offset
 * @param value
 */
@Deprecated("Use BufferAware instead.", ReplaceWith("BufferAware"))
public fun ByteArray.writeUIntAt(offset: Int, value: UInt): Unit = withBufferAware(this) { it.writeUIntAt(offset, value) }

/**
 * Write Long at offset.
 *
 * @param offset
 * @param value
 */
@Deprecated("Use BufferAware instead.", ReplaceWith("BufferAware"))
public fun ByteArray.writeLongAt(offset: Int, value: Long): Unit = withBufferAware(this) { it.writeLongAt(offset, value) }

/**
 * Write ULong at offset.
 *
 * @param offset
 * @param value
 */
@Deprecated("Use BufferAware instead.", ReplaceWith("BufferAware"))
public fun ByteArray.writeULongAt(offset: Int, value: ULong): Unit = withBufferAware(this) { it.writeULongAt(offset, value) }

/**
 * Write Float at offset.
 *
 * @param offset
 * @param value
 */
@Deprecated("Use BufferAware instead.", ReplaceWith("BufferAware"))
public fun ByteArray.writeFloatAt(offset: Int, value: Float): Unit = withBufferAware(this) { it.writeFloatAt(offset, value) }

/**
 * Write Double at offset.
 *
 * @param offset
 * @param value
 */
@Deprecated("Use BufferAware instead.", ReplaceWith("BufferAware"))
public fun ByteArray.writeDoubleAt(offset: Int, value: Double): Unit = withBufferAware(this) { it.writeDoubleAt(offset, value) }

/**
 * Convert ShortArray to ByteArray.
 * */
public fun ShortArray.toByteArray(): ByteArray = ByteArray(Short.SIZE_BYTES * this.size).also {
    this.forEachIndexed { idx, value -> it.writeShortAt(idx * Short.SIZE_BYTES, value) }
}


/**
 * Convert IntArray to ByteArray.
 * */
public fun IntArray.toByteArray(): ByteArray = ByteArray(Int.SIZE_BYTES * this.size).also {
    this.forEachIndexed { idx, value -> it.writeIntAt(idx * Int.SIZE_BYTES, value) }
}

/**
 * Convert LongArray to ByteArray.
 * */
public fun LongArray.toByteArray(): ByteArray = ByteArray(Long.SIZE_BYTES * this.size).also {
    this.forEachIndexed { idx, value -> it.writeLongAt(idx * Long.SIZE_BYTES, value) }
}

/**
 * Convert FloatArray to ByteArray.
 * */
public fun FloatArray.toByteArray(): ByteArray = ByteArray(Float.SIZE_BYTES * this.size).also {
    this.forEachIndexed { idx, value -> it.writeFloatAt(idx * Float.SIZE_BYTES, value) }
}

/**
 * Convert DoubleArray to ByteArray.
 * */
public fun DoubleArray.toByteArray(): ByteArray = ByteArray(Double.SIZE_BYTES * this.size).also {
    this.forEachIndexed { idx, value -> it.writeDoubleAt(idx * Double.SIZE_BYTES, value) }
}
