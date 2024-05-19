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
package org.angproj.aux.pipe

import org.angproj.aux.buf.Pump
import org.angproj.aux.io.BinaryReadable
import org.angproj.aux.io.Segment
import org.angproj.aux.io.TypeSize
import org.angproj.aux.io.isNull

public class BinarySink(
    pump: Pump = Pump
): AbstractSink<BinaryType>(pump), BinaryType, BinaryReadable {

    override fun readByte(): Byte = withinReadLimit(TypeSize.byte) { seg.getByte(pos) }

    override fun readUByte(): UByte = withinReadLimit(TypeSize.uByte) { seg.getByte(pos).toUByte() }

    override fun readShort(): Short = withinReadLimit(TypeSize.short) { seg.getShort(pos) }

    override fun readUShort(): UShort = withinReadLimit(TypeSize.uShort) { seg.getShort(pos).toUShort() }

    override fun readInt(): Int = withinReadLimit(TypeSize.int) { seg.getInt(pos) }

    override fun readUInt(): UInt = withinReadLimit(TypeSize.uInt) { seg.getInt(pos).toUInt() }

    override fun readLong(): Long = withinReadLimit(TypeSize.long) { seg.getLong(pos) }

    override fun readULong(): ULong = withinReadLimit(TypeSize.uLong) { seg.getLong(pos).toULong() }

    override fun readFloat(): Float = withinReadLimit(TypeSize.float) { Float.fromBits(seg.getInt(pos)) }

    override fun readDouble(): Double = withinReadLimit(TypeSize.double) { Double.fromBits(seg.getLong(pos)) }

    override fun dispose() {
        if(!seg.isNull()) seg.close()
    }
}