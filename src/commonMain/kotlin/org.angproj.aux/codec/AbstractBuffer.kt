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
package org.angproj.aux.codec

import org.angproj.aux.io.Readable
import org.angproj.aux.io.Writable
import org.angproj.aux.utf.*
import org.angproj.aux.util.*

public abstract class AbstractBuffer: Readable, Writable {

    protected abstract val _data: ByteArray

    public abstract val size: Int

    protected abstract var _position: Int
    public abstract val position: Int

    protected abstract var _limit: Int
    public abstract val limit: Int

    public abstract val remaining: Int

    public abstract fun rewind()

    public abstract fun flip()

    public abstract fun reset()

    public abstract fun resetWithErase()

    protected abstract fun <E> withinReadLimit(length: Int, action: () -> E): E

    protected abstract fun <E> withinWriteLimit(length: Int, action: () -> E)

    override fun readByte(): Byte = withinReadLimit(Byte.SIZE_BYTES) {
        _data[_position] }
    override fun readUByte(): UByte = withinReadLimit(UByte.SIZE_BYTES) {
        _data[_position].toUByte() }
    override fun readChar(): Char = withinReadLimit(Char.SIZE_BYTES) {
        _data.readCharAt(_position) }
    override fun readShort(): Short = withinReadLimit(Short.SIZE_BYTES) {
        _data.readShortAt(_position) }
    override fun readUShort(): UShort = withinReadLimit(UShort.SIZE_BYTES) {
        _data.readUShortAt(_position) }
    override fun readInt(): Int = withinReadLimit(Int.SIZE_BYTES) {
        _data.readIntAt(_position) }
    override fun readUInt(): UInt = withinReadLimit(UInt.SIZE_BYTES) {
        _data.readUIntAt(_position) }
    override fun readLong(): Long = withinReadLimit(Long.SIZE_BYTES) {
        _data.readLongAt(_position) }
    override fun readULong(): ULong = withinReadLimit(ULong.SIZE_BYTES) {
        _data.readULongAt(_position) }
    override fun readFloat(): Float = withinReadLimit(Float.SIZE_BYTES) {
        _data.readFloatAt(_position) }
    override fun readDouble(): Double = withinReadLimit(Double.SIZE_BYTES) {
        _data.readDoubleAt(_position) }

    /*public fun readGlyph(): Glyph {
        val size = _data[_position].glyphSize()
        return withinReadLimit(size) { _data.readGlyphAt(_position, size).escapeInvalid() }
    }*/

    override fun writeByte(value: Byte): Unit = withinWriteLimit(Byte.SIZE_BYTES) {
        _data[_position] = value }
    override fun writeUByte(value: UByte): Unit = withinWriteLimit(UByte.SIZE_BYTES) {
        _data[_position] = value.toByte() }
    override fun writeChar(value: Char): Unit = withinWriteLimit(Char.SIZE_BYTES) {
        _data.writeCharAt(_position, value) }
    override fun writeShort(value: Short): Unit = withinWriteLimit(Short.SIZE_BYTES) {
        _data.writeShortAt(_position, value) }
    override fun writeUShort(value: UShort): Unit =
        withinWriteLimit(UShort.SIZE_BYTES) { _data.writeUShortAt(_position, value) }

    override fun writeInt(value: Int): Unit = withinWriteLimit(Int.SIZE_BYTES) {
        _data.writeIntAt(_position, value) }
    override fun writeUInt(value: UInt): Unit = withinWriteLimit(UInt.SIZE_BYTES) {
        _data.writeUIntAt(_position, value) }
    override fun writeLong(value: Long): Unit = withinWriteLimit(Long.SIZE_BYTES) {
        _data.writeLongAt(_position, value) }
    override fun writeULong(value: ULong): Unit = withinWriteLimit(ULong.SIZE_BYTES) {
        _data.writeULongAt(_position, value) }
    override fun writeFloat(value: Float): Unit = withinWriteLimit(Float.SIZE_BYTES) {
        _data.writeFloatAt(_position, value) }
    override fun writeDouble(value: Double): Unit =
        withinWriteLimit(Double.SIZE_BYTES) { _data.writeDoubleAt(_position, value) }

    /*public fun writeGlyph(glyph: Glyph) {
        val escaped = glyph.escapeInvalid()
        val size = escaped.getSize()
        withinWriteLimit(size) { _data.writeGlyphAt(_position, escaped, size) }
    }*/
}