/**
 * Copyright (c) 2021-2024 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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

public interface BufferAware {

    private inline fun<reified R: Number> getLong(res: ByteArray, pos: Int): Long = withNumeral { joinLong<R>(getInt<R>(res, pos + 4), getInt<R>(res, pos)) }
    private inline fun<reified R: Number> getInt(res: ByteArray, pos: Int): Int = withNumeral { joinInt<R>(getShort<R>(res, pos + 2), getShort<R>(res, pos)) }
    private inline fun<reified R: Number> getShort(res: ByteArray, pos: Int): Short = withNumeral { joinShort<R>(res[pos + 1], res[pos]) }

    private inline fun<reified R: Number> setLong(res: ByteArray, pos: Int, value: Long): Unit = withNumeral {
        setInt<R>(res, pos + 4, upperLong<R>(value))
        setInt<R>(res, pos, lowerLong<R>(value))
    }

    private inline fun<reified R: Number> setInt(res: ByteArray, pos: Int, value: Int): Unit = withNumeral {
        setShort<R>(res, pos + 2, upperInt<R>(value))
        setShort<R>(res, pos, lowerInt<R>(value))
    }

    private inline fun<reified R: Number> setShort(res: ByteArray, pos: Int, value: Short): Unit = withNumeral {
        res[pos + 1] = upperShort<R>(value)
        res[pos] = lowerShort<R>(value)
    }

    public fun ByteArray.readShortAt(offset: Int): Short = withNumeral(this) { getShort<Int>(it, offset) }
    public fun ByteArray.readUShortAt(offset: Int): UShort = withNumeral(this) { getShort<Int>(it, offset).convS2US<Int>() }
    public fun ByteArray.readIntAt(offset: Int): Int = withNumeral(this) { getInt<Int>(it, offset) }
    public fun ByteArray.readUIntAt(offset: Int): UInt = withNumeral(this) { getInt<Int>(it, offset).convI2UI<Int>() }
    public fun ByteArray.readLongAt(offset: Int): Long = withNumeral(this) { getLong<Int>(it, offset) }
    public fun ByteArray.readULongAt(offset: Int): ULong = withNumeral(this) { getLong<Int>(it, offset).convL2UL<Int>() }
    public fun ByteArray.readFloatAt(offset: Int): Float = withNumeral(this) { getInt<Int>(it, offset).convI2F<Int>() }
    public fun ByteArray.readDoubleAt(offset: Int): Double = withNumeral(this) { getLong<Int>(it, offset).convL2D<Int>() }
    public fun ByteArray.writeShortAt(offset: Int, value: Short): Unit = withNumeral(this) { setShort<Int>(it, offset, value) }
    public fun ByteArray.writeUShortAt(offset: Int, value: UShort): Unit = withNumeral(this) { setShort<Int>(it, offset, value.convUS2S<Int>()) }
    public fun ByteArray.writeIntAt(offset: Int, value: Int): Unit = withNumeral(this) { setInt<Int>(it, offset, value) }
    public fun ByteArray.writeUIntAt(offset: Int, value: UInt): Unit = withNumeral(this) { setInt<Int>(it, offset, value.convUI2I<Int>()) }
    public fun ByteArray.writeLongAt(offset: Int, value: Long): Unit = withNumeral(this) { setLong<Int>(it, offset, value) }
    public fun ByteArray.writeULongAt(offset: Int, value: ULong): Unit = withNumeral(this) { setLong<Int>(it, offset, value.convUL2L<Int>()) }
    public fun ByteArray.writeFloatAt(offset: Int, value: Float): Unit = withNumeral(this) { setInt<Int>(it, offset, value.convF2I<Int>()) }
    public fun ByteArray.writeDoubleAt(offset: Int, value: Double): Unit = withNumeral(this) { setLong<Int>(it, offset, value.convD2L<Int>()) }
}

public object BufferAwareContext: BufferAware

public inline fun<reified T> withBufferAware(block: BufferAwareContext.() -> T): T = BufferAwareContext.block()

public inline fun<reified T> withBufferAware(
    array: ByteArray, block: BufferAwareContext.(array: ByteArray) -> T
): T = BufferAwareContext.block(array)