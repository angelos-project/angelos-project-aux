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
import org.angproj.aux.util.NumberAware
import org.angproj.aux.util.Reifiable
import org.angproj.aux.util.Reify
import kotlin.math.min

public class BinarySource(
    pipe: PushPipe<BinaryType>
): AbstractSource<BinaryType>(pipe), BinaryType, BinaryWritable, NumberAware {

    private inline fun <reified R : Reifiable> storeToSegment(length: Int, maxIter: Int, value: Long)/*: Long*/ {
        //var value = 0L
        repeat(min(length, maxIter)) { cnt ->
            if(pos == seg.limit && _open) pushSegment<Reify>()
            //((value shl 8) or (seg.getByte(pos++).toLong() and 0xff)).also { value = it }
            seg.setByte(pos++, ((value ushr (cnt * 8)) and 0xff).toByte())
        }
        //return value
    }

    private inline fun <reified R : Reifiable> withSegmentByte(value: Byte) {
        when(seg.limit - pos < TypeSize.byte) {
            false -> seg.setByte(pos++, value)//.also { pos += TypeSize.byte }
            else -> storeToSegment<Reify>(TypeSize.byte, TypeSize.byte, value.toLong())
        }
    }

    private inline fun <reified R : Reifiable> withSegmentShort(value: Short) {
        when(seg.limit - pos < TypeSize.short) {
            false -> seg.setShort(pos, value).also { pos += TypeSize.short }
            else -> storeToSegment<Reify>(TypeSize.short, TypeSize.short, value.toLong())
        }
    }

    private inline fun <reified R : Reifiable> withSegmentInt(value: Int) {
        when(seg.limit - pos < TypeSize.int) {
            false -> seg.setInt(pos, value).also { pos += TypeSize.int }
            else -> storeToSegment<Reify>(TypeSize.int, TypeSize.int, value.toLong())
        }
    }

    private inline fun <reified R : Reifiable> withSegmentLong(value: Long) {
        when(seg.limit - pos < TypeSize.long) {
            false -> seg.setLong(pos, value).also { pos += TypeSize.long }
            else -> storeToSegment<Reify>(TypeSize.long, TypeSize.long, value)
        }
    }

    override fun writeByte(value: Byte): Unit = withSegmentByte<Reify>(value)

    override fun writeUByte(value: UByte): Unit = withSegmentByte<Reify>(value.conv2B())

    override fun writeShort(value: Short): Unit = withSegmentShort<Reify>(value)

    override fun writeUShort(value: UShort): Unit = withSegmentShort<Reify>(value.conv2S())

    override fun writeInt(value: Int): Unit = withSegmentInt<Reify>(value)

    override fun writeUInt(value: UInt): Unit = withSegmentInt<Reify>(value.conv2I())

    override fun writeLong(value: Long): Unit = withSegmentLong<Reify>(value)

    override fun writeULong(value: ULong): Unit = withSegmentLong<Reify>(value.conv2L())

    override fun writeFloat(value: Float): Unit = withSegmentInt<Reify>(value.conv2I())

    override fun writeDouble(value: Double): Unit = withSegmentLong<Reify>(value.conv2L())
}