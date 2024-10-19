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
import org.angproj.aux.util.CodePoint
import org.angproj.aux.util.EndianAware
import org.angproj.aux.util.withUnicodeAware

public class BinaryWrapper(
    private val bin: Binary,
    private val offset: Int = 0
): EndianAware, BinaryReadable, BinaryWritable, TextReadable, TextWritable {

    init { positionAt(offset) }

    private var _position: Int = 0
    public val position: Int
        get() = _position

    public fun positionAt(newPos: Int) {
        require(newPos in 0..bin.limit)
        _position = newPos
    }

    public val indices: IntRange by lazy { offset until bin.limit }

    public fun flip() {
        bin.limitAt(_position)
        positionAt(offset)
    }

    override fun writeByte(value: Byte): Unit = bin.storeByte(_position, value).also { _position += TypeSize.byte }
    override fun writeUByte(value: UByte): Unit = bin.storeUByte(_position, value).also { _position += TypeSize.uByte }
    override fun writeShort(value: Short): Unit = bin.storeShort(_position, value).also { _position += TypeSize.short }
    override fun writeUShort(value: UShort): Unit = bin.storeUShort(_position, value).also { _position += TypeSize.uShort }
    override fun writeInt(value: Int): Unit = bin.storeInt(_position, value).also { _position += TypeSize.int }
    override fun writeUInt(value: UInt): Unit = bin.storeUInt(_position, value).also { _position += TypeSize.uInt }
    override fun writeLong(value: Long): Unit = bin.storeLong(_position, value).also { _position += TypeSize.long }
    override fun writeULong(value: ULong): Unit = bin.storeULong(_position, value).also { _position += TypeSize.uLong }
    override fun writeFloat(value: Float): Unit = bin.storeFloat(_position, value).also { _position += TypeSize.float }
    override fun writeDouble(value: Double): Unit = bin.storeDouble(_position, value).also { _position += TypeSize.double }

    override fun readByte(): Byte = bin.retrieveByte(_position).also { _position += TypeSize.byte }
    override fun readUByte(): UByte = bin.retrieveUByte(_position).also { _position += TypeSize.uByte }
    override fun readShort(): Short = bin.retrieveShort(_position).also { _position += TypeSize.short }
    override fun readUShort(): UShort = bin.retrieveUShort(_position).also { _position += TypeSize.uShort }
    override fun readInt(): Int = bin.retrieveInt(_position).also { _position += TypeSize.int }
    override fun readUInt(): UInt = bin.retrieveUInt(_position).also { _position += TypeSize.uInt }
    override fun readLong(): Long = bin.retrieveLong(_position).also { _position += TypeSize.long }
    override fun readULong(): ULong = bin.retrieveULong(_position).also { _position += TypeSize.uLong }
    override fun readFloat(): Float = bin.retrieveFloat(_position).also { _position += TypeSize.float }
    override fun readDouble(): Double = bin.retrieveDouble(_position).also { _position += TypeSize.double }

    public operator fun<E: Any> invoke(block: BinaryWrapper.() -> E): E = this.block()

    override fun readGlyph(): CodePoint = withUnicodeAware {
        readGlyphStrm { bin.retrieveByte(_position++) }
    }

    override fun writeGlyph(codePoint: CodePoint): Int = withUnicodeAware {
        writeGlyphStrm(codePoint) { bin.storeByte(_position++, it) }
    }
}

public fun Binary.asWrapped(offset: Int = 0): BinaryWrapper = BinaryWrapper(this, offset)
public fun<E: Any> Binary.wrap(offset: Int = 0, block: BinaryWrapper.() -> E): E = BinaryWrapper(this, offset).block()
public fun Binary.wrap(offset: Int = 0, block: BinaryWrapper.() -> Unit): Binary = also { BinaryWrapper(this, offset).block() }
//public fun BinaryWrapper.asText(): Text = BufMgr

