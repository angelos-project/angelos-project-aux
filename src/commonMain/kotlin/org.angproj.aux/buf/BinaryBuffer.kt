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

public class BinaryBuffer private constructor(
    segment: Segment
): Buffer(segment), BinaryReadable, BinaryWritable {

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
        _segment.getByte(_position).toUByte() }

    override fun readShort(): Short = withinReadLimit(TypeSize.short) {
        _segment.getShort(_position) }

    override fun readUShort(): UShort = withinReadLimit(TypeSize.uShort) {
        _segment.getShort(_position).toUShort() }

    override fun readInt(): Int = withinReadLimit(TypeSize.int) {
        _segment.getInt(_position) }

    override fun readUInt(): UInt = withinReadLimit(TypeSize.uInt) {
        _segment.getInt(_position).toUInt() }

    override fun readLong(): Long = withinReadLimit(TypeSize.long) {
        _segment.getLong(_position) }

    override fun readULong(): ULong = withinReadLimit(TypeSize.uLong) {
        _segment.getLong(_position).toULong() }

    override fun readFloat(): Float = withinReadLimit(TypeSize.float) {
        Float.fromBits(_segment.getInt(_position)) }

    override fun readDouble(): Double = withinReadLimit(TypeSize.double) {
        Double.fromBits(_segment.getLong(_position)) }

    override fun writeByte(value: Byte): Unit = withinWriteLimit(TypeSize.byte) {
        _segment.setByte(_position, value) }

    override fun writeUByte(value: UByte): Unit = withinWriteLimit(TypeSize.uByte) {
        _segment.setByte(_position, value.toByte()) }

    override fun writeShort(value: Short): Unit = withinWriteLimit(TypeSize.short) {
        _segment.setShort(_position, value) }

    override fun writeUShort(value: UShort): Unit = withinWriteLimit(TypeSize.uShort) {
        _segment.setShort(_position, value.toShort()) }

    override fun writeInt(value: Int): Unit = withinWriteLimit(TypeSize.int) {
        _segment.setInt(_position, value) }

    override fun writeUInt(value: UInt): Unit = withinWriteLimit(TypeSize.uInt) {
        _segment.setInt(_position, value.toInt()) }

    override fun writeLong(value: Long): Unit = withinWriteLimit(TypeSize.long) {
        _segment.setLong(_position, value) }

    override fun writeULong(value: ULong): Unit = withinWriteLimit(TypeSize.uLong) {
        _segment.setLong(_position, value.toLong()) }

    override fun writeFloat(value: Float): Unit = withinWriteLimit(TypeSize.float) {
        _segment.setInt(_position, value.toBits()) }

    override fun writeDouble(value: Double): Unit = withinWriteLimit(TypeSize.long) {
        _segment.setLong(_position, value.toBits()) }
}