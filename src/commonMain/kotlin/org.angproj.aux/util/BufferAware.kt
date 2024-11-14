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

    private inline fun <reified R : Any> getLong(
        res: ByteArray, pos: Int
    ): Long = I.joinLong<R>(getInt<R>(res, pos + 4), getInt<R>(res, pos))

    private inline fun <reified R : Any> getInt(
        res: ByteArray, pos: Int
    ): Int = I.joinInt<R>(getShort<R>(res, pos + 2), getShort<R>(res, pos))

    private inline fun <reified R : Any> getShort(
        res: ByteArray, pos: Int
    ): Short = I.joinShort<R>(res[pos + 1], res[pos])

    private inline fun <reified R : Any> setLong(res: ByteArray, pos: Int, value: Long) {
        setInt<R>(res, pos + 4, I.upperLong<R>(value))
        setInt<R>(res, pos, I.lowerLong<R>(value))
    }

    private inline fun <reified R : Any> setInt(res: ByteArray, pos: Int, value: Int) {
        setShort<R>(res, pos + 2, I.upperInt<R>(value))
        setShort<R>(res, pos, I.lowerInt<R>(value))
    }

    private inline fun <reified R : Any> setShort(res: ByteArray, pos: Int, value: Short) {
        res[pos + 1] = I.upperShort<R>(value)
        res[pos] = I.lowerShort<R>(value)
    }

    public fun ByteArray.readShortAt(offset: Int): Short = getShort<Unit>(this, offset)
    public fun ByteArray.readUShortAt(offset: Int): UShort = I.convS2US<Unit>(getShort<Unit>(this, offset))
    public fun ByteArray.readIntAt(offset: Int): Int = getInt<Unit>(this, offset)
    public fun ByteArray.readUIntAt(offset: Int): UInt = I.convI2UI<Unit>(getInt<Unit>(this, offset))
    public fun ByteArray.readLongAt(offset: Int): Long = getLong<Unit>(this, offset)
    public fun ByteArray.readULongAt(offset: Int): ULong = I.convL2UL<Unit>(getLong<Unit>(this, offset))
    public fun ByteArray.readFloatAt(offset: Int): Float = I.convI2F<Unit>(getInt<Unit>(this, offset))
    public fun ByteArray.readDoubleAt(offset: Int): Double = I.convL2D<Unit>(getLong<Unit>(this, offset))

    public fun ByteArray.writeShortAt(offset: Int, value: Short) {
        setShort<Unit>(this, offset, value)
    }

    public fun ByteArray.writeUShortAt(offset: Int, value: UShort) {
        setShort<Unit>(this, offset, I.convUS2S<Unit>(value))
    }

    public fun ByteArray.writeIntAt(offset: Int, value: Int) {
        setInt<Unit>(this, offset, value)
    }

    public fun ByteArray.writeUIntAt(offset: Int, value: UInt) {
        setInt<Unit>(this, offset, I.convUI2I<Unit>(value))
    }

    public fun ByteArray.writeLongAt(offset: Int, value: Long) {
        setLong<Unit>(this, offset, value)
    }

    public fun ByteArray.writeULongAt(offset: Int, value: ULong) {
        setLong<Unit>(this, offset, I.convUL2L<Unit>(value))
    }

    public fun ByteArray.writeFloatAt(offset: Int, value: Float) {
        setInt<Unit>(this, offset, I.convF2I<Unit>(value))
    }

    public fun ByteArray.writeDoubleAt(offset: Int, value: Double) {
        setLong<Unit>(this, offset, I.convD2L<Unit>(value))
    }

    private inline fun <reified R : Any> getRevLong(
        res: ByteArray, pos: Int
    ): Long = I.joinLong<R>(getRevInt<R>(res, pos), getRevInt<R>(res, pos + 4))

    private inline fun <reified R : Any> getRevInt(
        res: ByteArray, pos: Int
    ): Int = I.joinInt<R>(getRevShort<R>(res, pos), getRevShort<R>(res, pos + 2))

    private inline fun <reified R : Any> getRevShort(
        res: ByteArray, pos: Int
    ): Short = I.joinShort<R>(res[pos], res[pos + 1])

    private inline fun <reified R : Any> setRevLong(res: ByteArray, pos: Int, value: Long) {
        setRevInt<R>(res, pos, I.upperLong<R>(value))
        setRevInt<R>(res, pos + 4, I.lowerLong<R>(value))
    }

    private inline fun <reified R : Any> setRevInt(res: ByteArray, pos: Int, value: Int) {
        setRevShort<R>(res, pos, I.upperInt<R>(value))
        setRevShort<R>(res, pos + 2, I.lowerInt<R>(value))
    }

    private inline fun <reified R : Any> setRevShort(res: ByteArray, pos: Int, value: Short) {
        res[pos] = I.upperShort<R>(value)
        res[pos + 1] = I.lowerShort<R>(value)
    }

    public fun ByteArray.readRevShortAt(offset: Int): Short = getRevShort<Unit>(this, offset)
    public fun ByteArray.readRevUShortAt(offset: Int): UShort = I.convS2US<Unit>(getRevShort<Unit>(this, offset))
    public fun ByteArray.readRevIntAt(offset: Int): Int = getRevInt<Unit>(this, offset)
    public fun ByteArray.readRevUIntAt(offset: Int): UInt = I.convI2UI<Unit>(getRevInt<Unit>(this, offset))
    public fun ByteArray.readRevLongAt(offset: Int): Long = getRevLong<Unit>(this, offset)
    public fun ByteArray.readRevULongAt(offset: Int): ULong = I.convL2UL<Unit>(getRevLong<Unit>(this, offset))
    public fun ByteArray.readRevFloatAt(offset: Int): Float = I.convI2F<Unit>(getRevInt<Unit>(this, offset))
    public fun ByteArray.readRevDoubleAt(offset: Int): Double = I.convL2D<Unit>(getRevLong<Unit>(this, offset))

    public fun ByteArray.writeRevShortAt(offset: Int, value: Short) {
        setRevShort<Unit>(this, offset, value)
    }

    public fun ByteArray.writeRevUShortAt(offset: Int, value: UShort) {
        setRevShort<Unit>(this, offset, I.convUS2S<Unit>(value))
    }

    public fun ByteArray.writeRevIntAt(offset: Int, value: Int) {
        setRevInt<Unit>(this, offset, value)
    }

    public fun ByteArray.writeRevUIntAt(offset: Int, value: UInt) {
        setRevInt<Unit>(this, offset, I.convUI2I<Unit>(value))
    }

    public fun ByteArray.writeRevLongAt(offset: Int, value: Long) {
        setRevLong<Unit>(this, offset, value)
    }

    public fun ByteArray.writeRevULongAt(offset: Int, value: ULong) {
        setRevLong<Unit>(this, offset, I.convUL2L<Unit>(value))
    }

    public fun ByteArray.writeRevFloatAt(offset: Int, value: Float) {
        setRevInt<Unit>(this, offset, I.convF2I<Unit>(value))
    }

    public fun ByteArray.writeRevDoubleAt(offset: Int, value: Double) {
        setRevLong<Unit>(this, offset, I.convD2L<Unit>(value))
    }
}

public object BufferAwareContext : BufferAware

public inline fun <reified T> withBufferAware(block: BufferAwareContext.() -> T): T = BufferAwareContext.block()

public inline fun <reified T> withBufferAware(
    array: ByteArray, block: BufferAwareContext.(array: ByteArray) -> T
): T = BufferAwareContext.block(array)