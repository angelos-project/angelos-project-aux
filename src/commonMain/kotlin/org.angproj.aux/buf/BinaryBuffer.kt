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
import org.angproj.aux.util.NumberAware


public class BinaryBuffer internal constructor(
    segment: Segment, view: Boolean = false
): FlowBuffer(segment, view), BinaryReadable, BinaryWritable, NumberAware {

    public constructor(size: Int) : this(Bytes(size))

    public constructor(size: DataSize = DataSize._4K) : this(size.size)

    override fun create(segment: Segment): BinaryBuffer = BinaryBuffer(segment)

    private inline fun <reified E: Any> withinReadLimit(length: Int, action: () -> E): E {
        require(remaining >= length) { "Buffer overflow, limit reached." }
        return action().also { _position += length }
    }

    private inline fun <reified E: Any> withinWriteLimit(length: Int, action: () -> E) {
        require(remaining >= length) { "Buffer overflow, limit reached." }
        action().also { _position += length }
    }

    override fun readByte(): Byte = withinReadLimit(TypeSize.byte) {
        _segment.getByte(_position) }

    override fun readUByte(): UByte = withinReadLimit(TypeSize.uByte) {
        _segment.getByte(_position).conv2uB() }

    override fun readShort(): Short = withinReadLimit(TypeSize.short) {
        _segment.getShort(_position) }

    override fun readUShort(): UShort = withinReadLimit(TypeSize.uShort) {
        _segment.getShort(_position).conv2uS() }

    override fun readInt(): Int = withinReadLimit(TypeSize.int) {
        _segment.getInt(_position) }

    override fun readUInt(): UInt = withinReadLimit(TypeSize.uInt) {
        _segment.getInt(_position).conv2uI() }

    override fun readLong(): Long = withinReadLimit(TypeSize.long) {
        _segment.getLong(_position) }

    override fun readULong(): ULong = withinReadLimit(TypeSize.uLong) {
        _segment.getLong(_position).conv2uL() }

    override fun readFloat(): Float = withinReadLimit(TypeSize.float) {
        _segment.getInt(_position).conv2F() }

    override fun readDouble(): Double = withinReadLimit(TypeSize.double) {
        _segment.getLong(_position).conv2D() }

    override fun writeByte(value: Byte): Unit = withinWriteLimit(TypeSize.byte) {
        _segment.setByte(_position, value) }

    override fun writeUByte(value: UByte): Unit = withinWriteLimit(TypeSize.uByte) {
        _segment.setByte(_position, value.conv2B()) }

    override fun writeShort(value: Short): Unit = withinWriteLimit(TypeSize.short) {
        _segment.setShort(_position, value) }

    override fun writeUShort(value: UShort): Unit = withinWriteLimit(TypeSize.uShort) {
        _segment.setShort(_position, value.conv2S()) }

    override fun writeInt(value: Int): Unit = withinWriteLimit(TypeSize.int) {
        _segment.setInt(_position, value) }

    override fun writeUInt(value: UInt): Unit = withinWriteLimit(TypeSize.uInt) {
        _segment.setInt(_position, value.conv2I()) }

    override fun writeLong(value: Long): Unit = withinWriteLimit(TypeSize.long) {
        _segment.setLong(_position, value) }

    override fun writeULong(value: ULong): Unit = withinWriteLimit(TypeSize.uLong) {
        _segment.setLong(_position, value.conv2L()) }

    override fun writeFloat(value: Float): Unit = withinWriteLimit(TypeSize.float) {
        _segment.setInt(_position, value.conv2I()) }

    override fun writeDouble(value: Double): Unit = withinWriteLimit(TypeSize.long) {
        _segment.setLong(_position, value.conv2L()) }
}

/**
 * For proper copying of a certain sequence of bytes, markAt() and limitAt() has to be set first.
 * */
public fun BinaryBuffer.toBinary(): Binary = binOf(limit - mark).apply {
    this@toBinary.copyInto(this, 0, mark, limit) }