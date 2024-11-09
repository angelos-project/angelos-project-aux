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

    private inline fun<reified R: Any> getLong(res: ByteArray, pos: Int): Long = withNumeral {
        joinLong<R>(
            getInt<R>(res, pos + 4),
            getInt<R>(res, pos)
        )
    }
    private inline fun<reified R: Any> getInt(res: ByteArray, pos: Int): Int = withNumeral {
        joinInt<R>(
            getShort<R>(res, pos + 2),
            getShort<R>(res, pos)
        )
    }
    private inline fun<reified R: Any> getShort(res: ByteArray, pos: Int): Short = withNumeral {
        joinShort<R>(
            res[pos + 1],
            res[pos]
        )
    }

    private inline fun<reified R: Any> setLong(res: ByteArray, pos: Int, value: Long): Unit = withNumeral {
        setInt<R>(res, pos + 4, upperLong<R>(value))
        setInt<R>(res, pos, lowerLong<R>(value))
    }

    private inline fun<reified R: Any> setInt(res: ByteArray, pos: Int, value: Int): Unit = withNumeral {
        setShort<R>(res, pos + 2, upperInt<R>(value))
        setShort<R>(res, pos, lowerInt<R>(value))
    }

    private inline fun<reified R: Any> setShort(res: ByteArray, pos: Int, value: Short): Unit = withNumeral {
        res[pos + 1] = upperShort<R>(value)
        res[pos] = lowerShort<R>(value)
    }

    public fun ByteArray.readShortAt(offset: Int): Short = withNumeral(this) { getShort<Unit>(it, offset) }
    public fun ByteArray.readUShortAt(offset: Int): UShort = withNumeral(this) { getShort<Unit>(it, offset).convS2US<Unit>() }
    public fun ByteArray.readIntAt(offset: Int): Int = withNumeral(this) { getInt<Unit>(it, offset) }
    public fun ByteArray.readUIntAt(offset: Int): UInt = withNumeral(this) { getInt<Unit>(it, offset).convI2UI<Unit>() }
    public fun ByteArray.readLongAt(offset: Int): Long = withNumeral(this) { getLong<Unit>(it, offset) }
    public fun ByteArray.readULongAt(offset: Int): ULong = withNumeral(this) { getLong<Unit>(it, offset).convL2UL<Unit>() }
    public fun ByteArray.readFloatAt(offset: Int): Float = withNumeral(this) { getInt<Unit>(it, offset).convI2F<Unit>() }
    public fun ByteArray.readDoubleAt(offset: Int): Double = withNumeral(this) { getLong<Unit>(it, offset).convL2D<Unit>() }

    public fun ByteArray.writeShortAt(offset: Int, value: Short): Unit = withNumeral(this) { setShort<Unit>(it, offset, value) }
    public fun ByteArray.writeUShortAt(offset: Int, value: UShort): Unit = withNumeral(this) { setShort<Unit>(it, offset, value.convUS2S<Unit>()) }
    public fun ByteArray.writeIntAt(offset: Int, value: Int): Unit = withNumeral(this) { setInt<Unit>(it, offset, value) }
    public fun ByteArray.writeUIntAt(offset: Int, value: UInt): Unit = withNumeral(this) { setInt<Unit>(it, offset, value.convUI2I<Unit>()) }
    public fun ByteArray.writeLongAt(offset: Int, value: Long): Unit = withNumeral(this) { setLong<Unit>(it, offset, value) }
    public fun ByteArray.writeULongAt(offset: Int, value: ULong): Unit = withNumeral(this) { setLong<Unit>(it, offset, value.convUL2L<Unit>()) }
    public fun ByteArray.writeFloatAt(offset: Int, value: Float): Unit = withNumeral(this) { setInt<Unit>(it, offset, value.convF2I<Unit>()) }
    public fun ByteArray.writeDoubleAt(offset: Int, value: Double): Unit = withNumeral(this) { setLong<Unit>(it, offset, value.convD2L<Unit>()) }

    private inline fun<reified R: Any> getRevLong(res: ByteArray, pos: Int): Long = withNumeral {
        joinLong<R>(
            getRevInt<R>(res, pos),
            getRevInt<R>(res, pos + 4)
        )
    }
    private inline fun<reified R: Any> getRevInt(res: ByteArray, pos: Int): Int = withNumeral {
        joinInt<R>(
            getRevShort<R>(res, pos),
            getRevShort<R>(res, pos + 2)
        )
    }
    private inline fun<reified R: Any> getRevShort(res: ByteArray, pos: Int): Short = withNumeral {
        joinShort<R>(
            res[pos],
            res[pos + 1]
        )
    }

    private inline fun<reified R: Any> setRevLong(res: ByteArray, pos: Int, value: Long): Unit = withNumeral {
        setRevInt<R>(res, pos, upperLong<R>(value))
        setRevInt<R>(res, pos + 4, lowerLong<R>(value))
    }

    private inline fun<reified R: Any> setRevInt(res: ByteArray, pos: Int, value: Int): Unit = withNumeral {
        setRevShort<R>(res, pos, upperInt<R>(value))
        setRevShort<R>(res, pos + 2, lowerInt<R>(value))
    }

    private inline fun<reified R: Any> setRevShort(res: ByteArray, pos: Int, value: Short): Unit = withNumeral {
        res[pos] = upperShort<R>(value)
        res[pos + 1] = lowerShort<R>(value)
    }

    public fun ByteArray.readRevShortAt(offset: Int): Short = withNumeral(this) { getRevShort<Unit>(it, offset) }
    public fun ByteArray.readRevUShortAt(offset: Int): UShort = withNumeral(this) { getRevShort<Unit>(it, offset).convS2US<Unit>() }
    public fun ByteArray.readRevIntAt(offset: Int): Int = withNumeral(this) { getRevInt<Unit>(it, offset) }
    public fun ByteArray.readRevUIntAt(offset: Int): UInt = withNumeral(this) { getRevInt<Unit>(it, offset).convI2UI<Unit>() }
    public fun ByteArray.readRevLongAt(offset: Int): Long = withNumeral(this) { getRevLong<Unit>(it, offset) }
    public fun ByteArray.readRevULongAt(offset: Int): ULong = withNumeral(this) { getRevLong<Unit>(it, offset).convL2UL<Unit>() }
    public fun ByteArray.readRevFloatAt(offset: Int): Float = withNumeral(this) { getRevInt<Unit>(it, offset).convI2F<Unit>() }
    public fun ByteArray.readRevDoubleAt(offset: Int): Double = withNumeral(this) { getRevLong<Unit>(it, offset).convL2D<Unit>() }

    public fun ByteArray.writeRevShortAt(offset: Int, value: Short): Unit = withNumeral(this) { setRevShort<Unit>(it, offset, value) }
    public fun ByteArray.writeRevUShortAt(offset: Int, value: UShort): Unit = withNumeral(this) { setRevShort<Unit>(it, offset, value.convUS2S<Unit>()) }
    public fun ByteArray.writeRevIntAt(offset: Int, value: Int): Unit = withNumeral(this) { setRevInt<Unit>(it, offset, value) }
    public fun ByteArray.writeRevUIntAt(offset: Int, value: UInt): Unit = withNumeral(this) { setRevInt<Unit>(it, offset, value.convUI2I<Unit>()) }
    public fun ByteArray.writeRevLongAt(offset: Int, value: Long): Unit = withNumeral(this) { setRevLong<Unit>(it, offset, value) }
    public fun ByteArray.writeRevULongAt(offset: Int, value: ULong): Unit = withNumeral(this) { setRevLong<Unit>(it, offset, value.convUL2L<Unit>()) }
    public fun ByteArray.writeRevFloatAt(offset: Int, value: Float): Unit = withNumeral(this) { setRevInt<Unit>(it, offset, value.convF2I<Unit>()) }
    public fun ByteArray.writeRevDoubleAt(offset: Int, value: Double): Unit = withNumeral(this) { setRevLong<Unit>(it, offset, value.convD2L<Unit>()) }
}

public object BufferAwareContext: BufferAware

public inline fun<reified T> withBufferAware(block: BufferAwareContext.() -> T): T = BufferAwareContext.block()

public inline fun<reified T> withBufferAware(
    array: ByteArray, block: BufferAwareContext.(array: ByteArray) -> T
): T = BufferAwareContext.block(array)