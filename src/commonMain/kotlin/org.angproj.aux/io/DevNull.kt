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
package org.angproj.aux.io

import org.angproj.aux.util.CodePoint

public object DevNull : BinaryWritable, GlyphWritable, PumpWriter {

    private var _count = 0L
    override val count: Long
        get() = _count + _inputCnt

    private var _inputCnt: Long = 0
    override val inputCount: Long
        get() = _inputCnt

    override val inputStale: Boolean = false

    override fun writeByte(value: Byte) { _count += TypeSize.byte}
    override fun writeUByte(value: UByte) { _count += TypeSize.uByte }
    override fun writeShort(value: Short) { _count += TypeSize.short }
    override fun writeUShort(value: UShort) { _count += TypeSize.uShort }
    override fun writeInt(value: Int) { _count += TypeSize.int }
    override fun writeUInt(value: UInt) { _count += TypeSize.uInt }
    override fun writeLong(value: Long) { _count += TypeSize.long }
    override fun writeULong(value: ULong) { _count += TypeSize.uLong }
    override fun writeFloat(value: Float) { _count += TypeSize.float }
    override fun writeDouble(value: Double) { _count += TypeSize.double }

    override fun writeRevShort(value: Short) { _count += TypeSize.short }
    override fun writeRevUShort(value: UShort) { _count += TypeSize.uShort }
    override fun writeRevInt(value: Int) { _count += TypeSize.int }
    override fun writeRevUInt(value: UInt) { _count += TypeSize.uInt }
    override fun writeRevLong(value: Long) { _count += TypeSize.long }
    override fun writeRevULong(value: ULong) { _count += TypeSize.uLong }
    override fun writeRevFloat(value: Float) { _count += TypeSize.float }
    override fun writeRevDouble(value: Double) { _count += TypeSize.double }

    override fun write(data: Segment<*>): Int = data.limit.also { _inputCnt += it }

    override fun writeGlyph(codePoint: CodePoint): Int = codePoint.octetSize().also { _count += it }
}