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
package org.angproj.aux.buf

import org.angproj.aux.io.*
import org.angproj.aux.mem.BufMgr
import org.angproj.aux.mem.Default


public class BinaryBuffer internal constructor(
    segment: Segment<*>, view: Boolean = false
): FlowBuffer(segment, view), BinaryReadable, BinaryWritable {

    public constructor(size: Int) : this(Default.allocate(size))

    public constructor(size: DataSize = DataSize._4K) : this(size.size)

    override fun create(segment: Segment<*>): BinaryBuffer = BinaryBuffer(segment)

    override fun readByte(): Byte =
        _segment.getByte(_position).also { _position += TypeSize.byte }

    override fun readUByte(): UByte =
        _segment.getByte(_position).conv2uB().also { _position += TypeSize.uByte }

    override fun readShort(): Short =
        _segment.getShort(_position).also { _position += TypeSize.short }

    override fun readUShort(): UShort =
        _segment.getShort(_position).conv2uS().also { _position += TypeSize.uShort }

    override fun readInt(): Int =
        _segment.getInt(_position).also { _position += TypeSize.int }

    override fun readUInt(): UInt =
        _segment.getInt(_position).conv2uI().also { _position += TypeSize.uInt }

    override fun readLong(): Long =
        _segment.getLong(_position).also { _position += TypeSize.long }

    override fun readULong(): ULong =
        _segment.getLong(_position).conv2uL().also { _position += TypeSize.uLong }

    override fun readFloat(): Float =
        _segment.getInt(_position).conv2F().also { _position += TypeSize.float }

    override fun readDouble(): Double =
        _segment.getLong(_position).conv2D().also { _position += TypeSize.double }

    override fun readRevShort(): Short =
        _segment.getRevShort(_position).also { _position += TypeSize.short }

    override fun readRevUShort(): UShort =
        _segment.getRevShort(_position).conv2uS().also { _position += TypeSize.uShort }

    override fun readRevInt(): Int =
        _segment.getRevInt(_position).also { _position += TypeSize.int }

    override fun readRevUInt(): UInt =
        _segment.getRevInt(_position).conv2uI().also { _position += TypeSize.uInt }

    override fun readRevLong(): Long =
        _segment.getRevLong(_position).also { _position += TypeSize.long }

    override fun readRevULong(): ULong =
        _segment.getRevLong(_position).conv2uL().also { _position += TypeSize.uLong }

    override fun readRevFloat(): Float =
        _segment.getRevInt(_position).conv2F().also { _position += TypeSize.float }

    override fun readRevDouble(): Double =
        _segment.getRevLong(_position).conv2D().also { _position += TypeSize.double }

    override fun writeByte(value: Byte): Unit =
        _segment.setByte(_position, value).also { _position += TypeSize.byte }

    override fun writeUByte(value: UByte): Unit =
        _segment.setByte(_position, value.conv2B()).also { _position += TypeSize.uByte }

    override fun writeShort(value: Short): Unit =
        _segment.setShort(_position, value).also { _position += TypeSize.short }

    override fun writeUShort(value: UShort): Unit =
        _segment.setShort(_position, value.conv2S()).also { _position += TypeSize.uShort }

    override fun writeInt(value: Int): Unit =
        _segment.setInt(_position, value).also { _position += TypeSize.int }

    override fun writeUInt(value: UInt): Unit =
        _segment.setInt(_position, value.conv2I()).also { _position += TypeSize.uInt }

    override fun writeLong(value: Long): Unit =
        _segment.setLong(_position, value).also { _position += TypeSize.long }

    override fun writeULong(value: ULong): Unit =
        _segment.setLong(_position, value.conv2L()).also { _position += TypeSize.uLong }

    override fun writeFloat(value: Float): Unit =
        _segment.setInt(_position, value.conv2I()).also { _position += TypeSize.float }

    override fun writeDouble(value: Double): Unit =
        _segment.setLong(_position, value.conv2L()).also { _position += TypeSize.long }

    override fun writeRevShort(value: Short): Unit =
        _segment.setRevShort(_position, value).also { _position += TypeSize.short }

    override fun writeRevUShort(value: UShort): Unit =
        _segment.setRevShort(_position, value.conv2S()).also { _position += TypeSize.uShort }

    override fun writeRevInt(value: Int): Unit =
        _segment.setRevInt(_position, value).also { _position += TypeSize.int }

    override fun writeRevUInt(value: UInt): Unit =
        _segment.setRevInt(_position, value.conv2I()).also { _position += TypeSize.uInt }

    override fun writeRevLong(value: Long): Unit =
        _segment.setRevLong(_position, value).also { _position += TypeSize.long }

    override fun writeRevULong(value: ULong): Unit =
        _segment.setRevLong(_position, value.conv2L()).also { _position += TypeSize.uLong }

    override fun writeRevFloat(value: Float): Unit =
        _segment.setRevInt(_position, value.conv2I()).also { _position += TypeSize.float }

    override fun writeRevDouble(value: Double): Unit =
        _segment.setRevLong(_position, value.conv2L()).also { _position += TypeSize.long }
}

public fun binaryOf(capacity: Int): BinaryBuffer = BufMgr.binary(capacity)

/**
 * For proper copying of a certain sequence of bytes, markAt() and limitAt() has to be set first.
 * */
public fun BinaryBuffer.toBinary(): Binary = binOf(limit - mark).apply {
    this@toBinary.copyInto(this, 0, mark, limit) }