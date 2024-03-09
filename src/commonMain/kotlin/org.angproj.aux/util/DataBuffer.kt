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

import org.angproj.aux.io.Readable
import org.angproj.aux.io.Writable

public class DataBuffer(data: ByteArray): Readable, Writable {

    public constructor(size: Int = 4096) : this(ByteArray(size))

    private val _data = data
    public fun getArray(): ByteArray = _data

    public val size: Int
        get() = _data.size

    private var _position: Int = 0
    public val position: Int
        get() = _position

    private var _limit: Int = _data.size
    public val limit: Int
        get() = _limit

    public val remaining: Int
        get() = _limit - _position

    public fun rewind() {
        _position = 0
    }

    public fun flip() {
        _limit = _position
        rewind()
    }

    public fun reset(erase: Boolean = true) {
        if(erase) _data.fill(0)
        _position = 0
        _limit = _data.size
    }

    private fun <E> withinLimit(length: Int, action: () -> E): E {
        require(_position <= _limit + length)
        val out = action()
        _position += length
        return out
    }

    override fun readByte(): Byte = withinLimit(Byte.SIZE_BYTES) { _data[_position] }
    override fun readUByte(): UByte = withinLimit(UByte.SIZE_BYTES) { _data[_position].toUByte() }
    override fun readChar(): Char = withinLimit(Char.SIZE_BYTES) { _data.readCharAt(_position) }
    override fun readShort(): Short = withinLimit(Short.SIZE_BYTES) { _data.readShortAt(_position) }
    override fun readUShort(): UShort = withinLimit(UShort.SIZE_BYTES) { _data.readUShortAt(_position) }
    override fun readInt(): Int = withinLimit(Int.SIZE_BYTES) { _data.readIntAt(_position) }
    override fun readUInt(): UInt = withinLimit(UInt.SIZE_BYTES) { _data.readUIntAt(_position) }
    override fun readLong(): Long = withinLimit(Long.SIZE_BYTES) { _data.readLongAt(_position) }
    override fun readULong(): ULong = withinLimit(ULong.SIZE_BYTES) { _data.readULongAt(_position) }
    override fun readFloat(): Float = withinLimit(Float.SIZE_BYTES) { _data.readFloatAt(_position) }
    override fun readDouble(): Double = withinLimit(Double.SIZE_BYTES) { _data.readDoubleAt(_position) }

    override fun writeByte(value: Byte): Unit = withinLimit(Byte.SIZE_BYTES) { _data[_position] = value }
    override fun writeUByte(value: UByte): Unit = withinLimit(UByte.SIZE_BYTES) { _data[_position] = value.toByte() }
    override fun writeChar(value: Char): Unit = withinLimit(Char.SIZE_BYTES) { _data.writeCharAt(_position, value) }
    override fun writeShort(value: Short): Unit = withinLimit(Short.SIZE_BYTES) { _data.writeShortAt(_position, value) }
    override fun writeUShort(value: UShort): Unit = withinLimit(UShort.SIZE_BYTES) { _data.writeUShortAt(_position, value) }
    override fun writeInt(value: Int): Unit = withinLimit(Int.SIZE_BYTES) { _data.writeIntAt(_position, value) }
    override fun writeUInt(value: UInt): Unit = withinLimit(UInt.SIZE_BYTES) { _data.writeUIntAt(_position, value) }
    override fun writeLong(value: Long): Unit = withinLimit(Long.SIZE_BYTES) { _data.writeLongAt(_position, value) }
    override fun writeULong(value: ULong): Unit = withinLimit(ULong.SIZE_BYTES) {_data.writeULongAt(_position, value) }
    override fun writeFloat(value: Float): Unit = withinLimit(Float.SIZE_BYTES) { _data.writeFloatAt(_position, value) }
    override fun writeDouble(value: Double): Unit = withinLimit(Double.SIZE_BYTES) { _data.writeDoubleAt(_position, value) }
}