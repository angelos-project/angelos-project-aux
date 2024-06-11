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

import org.angproj.aux.io.BinaryWritable
import org.angproj.aux.io.TypeSize
import org.angproj.aux.util.Reifiable
import org.angproj.aux.util.Reify
import kotlin.math.min

public class BinarySource(pipe: PushPipe<BinaryType>): AbstractSource<BinaryType>(pipe), BinaryType, BinaryWritable {

    private inline fun <reified R : Reifiable> storeToSegment(length: Int, maxIter: Int): Long {
        var value = 0L
        repeat(min(length, maxIter)) {
            if(pos == seg.limit && _open) pushSegment<Reify>()
            ((value shl 8) or (seg.getByte(pos++).toLong() and 0xff)).also { value = it }
        }
        return value
    }

    private inline fun <reified R : Reifiable> withSegmentByte(value: Byte) {
        when(seg.limit - pos < TypeSize.byte) {
            true -> seg.setByte(pos, value).also { pos += TypeSize.byte }
            else -> storeToSegment<Reify>(TypeSize.byte, TypeSize.byte).toByte()
        }
    }

    private inline fun <reified R : Reifiable> withSegmentShort(value: Short) {
        when(seg.limit - pos < TypeSize.short) {
            true -> seg.setShort(pos, value).also { pos += TypeSize.short }
            else -> storeToSegment<Reify>(TypeSize.short, TypeSize.short).toShort()
        }
    }

    private inline fun <reified R : Reifiable> withSegmentInt(value: Int) {
        when(seg.limit - pos < TypeSize.int) {
            true -> seg.setInt(pos, value).also { pos += TypeSize.int }
            else -> storeToSegment<Reify>(TypeSize.int, TypeSize.int).toInt()
        }
    }

    private inline fun <reified R : Reifiable> withSegmentLong(value: Long) {
        when(seg.limit - pos < TypeSize.long) {
            true -> seg.setLong(pos, value).also { pos += TypeSize.long }
            else -> storeToSegment<Reify>(TypeSize.long, TypeSize.long)
        }
    }

    override fun writeByte(value: Byte): Unit = withSegmentByte<Reify>(value)

    override fun writeUByte(value: UByte): Unit = withSegmentByte<Reify>(value.toByte())

    override fun writeShort(value: Short): Unit = withSegmentShort<Reify>(value)

    override fun writeUShort(value: UShort): Unit = withSegmentShort<Reify>(value.toShort())

    override fun writeInt(value: Int): Unit = withSegmentInt<Reify>(value)

    override fun writeUInt(value: UInt): Unit = withSegmentInt<Reify>(value.toInt())

    override fun writeLong(value: Long): Unit = withSegmentLong<Reify>(value)

    override fun writeULong(value: ULong): Unit = withSegmentLong<Reify>(value.toLong())

    override fun writeFloat(value: Float): Unit = withSegmentInt<Reify>(value.toBits())

    override fun writeDouble(value: Double): Unit = withSegmentLong<Reify>(value.toBits())
}