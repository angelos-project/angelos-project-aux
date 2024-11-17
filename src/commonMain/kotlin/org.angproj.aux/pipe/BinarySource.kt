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
import kotlin.math.min

public class BinarySource(
    pipe: PushPipe
): AbstractSource<BinaryType>(pipe), BinaryWritable {

    private inline fun <reified E : Any> storeToSegment(length: Int, maxIter: Int, value: Long) {
        //var value = 0L
        repeat(min(length, maxIter)) { cnt ->
            if(pos == seg.limit) pushSegment<Unit>()
            //((value shl 8) or (seg.getByte(pos++).toLong() and 0xff)).also { value = it }
            seg.setByte(pos++, ((value ushr (cnt * 8)) and 0xff).toByte())
        }
        //return value
    }

    private inline fun <reified E : Any> withSegmentByte(value: Byte) {
        when(seg.limit - pos < TypeSize.byte) {
            false -> seg.setByte(pos++, value)//.also { pos += TypeSize.byte }
            else -> storeToSegment<Unit>(TypeSize.byte, TypeSize.byte, value.toLong())
        }
    }

    private inline fun <reified E : Any> withSegmentShort(value: Short) {
        when(seg.limit - pos < TypeSize.short) {
            false -> seg.setShort(pos, value).also { pos += TypeSize.short }
            else -> storeToSegment<Unit>(TypeSize.short, TypeSize.short, value.toLong())
        }
    }

    private inline fun <reified E : Any> withSegmentInt(value: Int) {
        when(seg.limit - pos < TypeSize.int) {
            false -> seg.setInt(pos, value).also { pos += TypeSize.int }
            else -> storeToSegment<Unit>(TypeSize.int, TypeSize.int, value.toLong())
        }
    }

    private inline fun <reified E : Any> withSegmentLong(value: Long) {
        when(seg.limit - pos < TypeSize.long) {
            false -> seg.setLong(pos, value).also { pos += TypeSize.long }
            else -> storeToSegment<Unit>(TypeSize.long, TypeSize.long, value)
        }
    }

    private inline fun <reified E : Any> withSegmentRevShort(value: Short) {
        when(seg.limit - pos < TypeSize.short) {
            false -> seg.setRevShort(pos, value).also { pos += TypeSize.short }
            else -> storeToSegment<Unit>(TypeSize.short, TypeSize.short, value.toLong())
        }
    }

    private inline fun <reified E : Any> withSegmentRevInt(value: Int) {
        when(seg.limit - pos < TypeSize.int) {
            false -> seg.setRevInt(pos, value).also { pos += TypeSize.int }
            else -> storeToSegment<Unit>(TypeSize.int, TypeSize.int, value.toLong())
        }
    }

    private inline fun <reified E : Any> withSegmentRevLong(value: Long) {
        when(seg.limit - pos < TypeSize.long) {
            false -> seg.setRevLong(pos, value).also { pos += TypeSize.long }
            else -> storeToSegment<Unit>(TypeSize.long, TypeSize.long, value)
        }
    }

    override fun writeByte(value: Byte): Unit = withSegmentByte<Unit>(value)

    override fun writeUByte(value: UByte): Unit = withSegmentByte<Unit>(value.conv2B<Unit>())

    override fun writeShort(value: Short): Unit = withSegmentShort<Unit>(value)

    override fun writeUShort(value: UShort): Unit = withSegmentShort<Unit>(value.conv2S<Unit>())

    override fun writeInt(value: Int): Unit = withSegmentInt<Unit>(value)

    override fun writeUInt(value: UInt): Unit = withSegmentInt<Unit>(value.conv2I<Unit>())

    override fun writeLong(value: Long): Unit = withSegmentLong<Unit>(value)

    override fun writeULong(value: ULong): Unit = withSegmentLong<Unit>(value.conv2L<Unit>())

    override fun writeFloat(value: Float): Unit = withSegmentInt<Unit>(value.conv2I<Unit>())

    override fun writeDouble(value: Double): Unit = withSegmentLong<Unit>(value.conv2L<Unit>())

    override fun writeRevShort(value: Short): Unit = withSegmentRevShort<Unit>(value)

    override fun writeRevUShort(value: UShort): Unit = withSegmentRevShort<Unit>(value.conv2S<Unit>())

    override fun writeRevInt(value: Int): Unit = withSegmentRevInt<Unit>(value)

    override fun writeRevUInt(value: UInt): Unit = withSegmentRevInt<Unit>(value.conv2I<Unit>())

    override fun writeRevLong(value: Long): Unit = withSegmentRevLong<Unit>(value)

    override fun writeRevULong(value: ULong): Unit = withSegmentRevLong<Unit>(value.conv2L<Unit>())

    override fun writeRevFloat(value: Float): Unit = withSegmentRevInt<Unit>(value.conv2I<Unit>())

    override fun writeRevDouble(value: Double): Unit = withSegmentRevLong<Unit>(value.conv2L<Unit>())
}