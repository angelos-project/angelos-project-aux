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
package org.angproj.aux.pkg.type

import org.angproj.aux.io.Retrievable
import org.angproj.aux.io.Storable
import org.angproj.aux.util.*
import kotlin.jvm.JvmInline

@JvmInline
public value class BlockType(public val block: ByteArray) : Storable, Retrievable {

    public constructor(size: Int) : this(ByteArray(size))

    override fun retrieveByte(position: Int): Byte = block.get(position)

    override fun retrieveUByte(position: Int): UByte = block.get(position).toUByte()

    override fun retrieveChar(position: Int): Char = block.readCharAt(position)

    override fun retrieveShort(position: Int): Short = block.readShortAt(position)

    override fun retrieveUShort(position: Int): UShort = block.readUShortAt(position)

    override fun retrieveInt(position: Int): Int = block.readIntAt(position)

    override fun retrieveUInt(position: Int): UInt = block.readUIntAt(position)

    override fun retrieveLong(position: Int): Long = block.readLongAt(position)

    override fun retrieveULong(position: Int): ULong = block.readULongAt(position)

    override fun retrieveFloat(position: Int): Float = block.readFloatAt(position)

    override fun retrieveDouble(position: Int): Double = block.readDoubleAt(position)

    override fun storeByte(position: Int, value: Byte) { block.set(position, value) }

    override fun storeUByte(position: Int, value: UByte) { block.set(position, value.toByte()) }

    override fun storeChar(position: Int, value: Char) { block.writeCharAt(position, value) }

    override fun storeShort(position: Int, value: Short) { block.writeShortAt(position, value) }

    override fun storeUShort(position: Int, value: UShort) { block.writeUShortAt(position, value) }

    override fun storeInt(position: Int, value: Int) { block.writeIntAt(position, value) }

    override fun storeUInt(position: Int, value: UInt) { block.writeUIntAt(position, value) }

    override fun storeLong(position: Int, value: Long) { block.writeLongAt(position, value) }

    override fun storeULong(position: Int, value: ULong) { block.writeULongAt(position, value) }

    override fun storeFloat(position: Int, value: Float) { block.writeFloatAt(position, value) }

    override fun storeDouble(position: Int, value: Double) { block.writeDoubleAt(position, value) }
}