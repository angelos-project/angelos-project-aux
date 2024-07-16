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

public object BArr {
    public fun interface GetVal<X, T: Number> {
        public operator fun invoke(res: X, pos: Int, noop: Noop) : T
    }

    public fun interface SetVal<X, T: Number> {
        public operator fun invoke(res: X, pos: Int, value: T)
    }

    public val getLong: GetVal<ByteArray, Long> = GetVal {
            res: ByteArray, pos: Int, _: Noop -> joinLong(getInt(res, pos + 4) {}, getInt(res, pos, ) {}) }
    public val getInt: GetVal<ByteArray, Int> = GetVal {
            res: ByteArray, pos: Int, _: Noop -> joinInt(getShort(res, pos + 2) {}, getShort(res, pos) {}) }
    public val getShort: GetVal<ByteArray, Short> = GetVal {
            res: ByteArray, pos: Int, _: Noop -> joinShort(res[pos + 1], res[pos]) }

    public val setLong: SetVal<ByteArray, Long> = SetVal { res: ByteArray, pos: Int, value: Long ->
        setInt(res, pos + 4, upperLong(value))
        setInt(res, pos, lowerLong(value)) }
    public val setInt: SetVal<ByteArray, Int> = SetVal { res: ByteArray, pos: Int, value: Int ->
        setShort(res, pos + 2, upperInt(value))
        setShort(res, pos, lowerInt(value)) }
    public val setShort: SetVal<ByteArray, Short> = SetVal { res: ByteArray, pos: Int, value: Short ->
        res[pos + 1] = upperShort(value)
        res[pos] = lowerShort(value) }
}

/**
 * Read Short at offset.
 *
 * @param offset
 * @return
 */
@Deprecated("Use BufferAware instead.", ReplaceWith("BufferAware"))
public fun ByteArray.readShortAt(offset: Int): Short = BArr.getShort(this, offset) {}

/**
 * Read UShort at offset.
 *
 * @param offset
 * @return
 */
@Deprecated("Use BufferAware instead.", ReplaceWith("BufferAware"))
public fun ByteArray.readUShortAt(offset: Int): UShort = readShortAt(offset).conv2uS()

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
public fun ByteArray.readIntAt(offset: Int): Int = BArr.getInt(this, offset) {}

/**
 * Read UInt at offset.
 *
 * @param offset
 * @return
 */
@Deprecated("Use BufferAware instead.", ReplaceWith("BufferAware"))
public fun ByteArray.readUIntAt(offset: Int): UInt = readIntAt(offset).conv2uI()

/**
 * Read Long at offset.
 *
 * @param offset
 * @return
 */
@Deprecated("Use BufferAware instead.", ReplaceWith("BufferAware"))
public fun ByteArray.readLongAt(offset: Int): Long = BArr.getLong(this, offset) {}

/**
 * Read ULong at offset.
 *
 * @param offset
 * @return
 */
@Deprecated("Use BufferAware instead.", ReplaceWith("BufferAware"))
public fun ByteArray.readULongAt(offset: Int): ULong = readLongAt(offset).conv2uL()

/**
 * Read Float at offset.
 *
 * @param offset
 * @return
 */
@Deprecated("Use BufferAware instead.", ReplaceWith("BufferAware"))
public fun ByteArray.readFloatAt(offset: Int): Float = readIntAt(offset).conv2F()

/**
 * Read Double at offset.
 *
 * @param offset
 * @return
 */
@Deprecated("Use BufferAware instead.", ReplaceWith("BufferAware"))
public fun ByteArray.readDoubleAt(offset: Int): Double = readLongAt(offset).conv2D()

/**
 * Write Short at offset.
 *
 * @param offset
 * @param value
 */
@Deprecated("Use BufferAware instead.", ReplaceWith("BufferAware"))
public fun ByteArray.writeShortAt(offset: Int, value: Short): Unit = BArr.setShort(this, offset, value)

/**
 * Write UShort at offset.
 *
 * @param offset
 * @param value
 */
@Deprecated("Use BufferAware instead.", ReplaceWith("BufferAware"))
public fun ByteArray.writeUShortAt(offset: Int, value: UShort): Unit = writeShortAt(offset, value.conv2S())

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
public fun ByteArray.writeIntAt(offset: Int, value: Int): Unit = BArr.setInt(this, offset, value)

/**
 * Write UInt at offset.
 *
 * @param offset
 * @param value
 */
@Deprecated("Use BufferAware instead.", ReplaceWith("BufferAware"))
public fun ByteArray.writeUIntAt(offset: Int, value: UInt): Unit = writeIntAt(offset, value.conv2I())

/**
 * Write Long at offset.
 *
 * @param offset
 * @param value
 */
@Deprecated("Use BufferAware instead.", ReplaceWith("BufferAware"))
public fun ByteArray.writeLongAt(offset: Int, value: Long): Unit = BArr.setLong(this, offset, value)

/**
 * Write ULong at offset.
 *
 * @param offset
 * @param value
 */
@Deprecated("Use BufferAware instead.", ReplaceWith("BufferAware"))
public fun ByteArray.writeULongAt(offset: Int, value: ULong): Unit = writeLongAt(offset, value.conv2L())

/**
 * Write Float at offset.
 *
 * @param offset
 * @param value
 */
@Deprecated("Use BufferAware instead.", ReplaceWith("BufferAware"))
public fun ByteArray.writeFloatAt(offset: Int, value: Float): Unit = writeIntAt(offset, value.conv2I())

/**
 * Write Double at offset.
 *
 * @param offset
 * @param value
 */
@Deprecated("Use BufferAware instead.", ReplaceWith("BufferAware"))
public fun ByteArray.writeDoubleAt(offset: Int, value: Double): Unit = writeLongAt(offset, value.conv2L())

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
