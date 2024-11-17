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

import org.angproj.aux.io.BinaryReadable
import org.angproj.aux.io.TypeBits
import org.angproj.aux.io.TypeSize
import org.angproj.aux.util.NumberAware

public class BinarySink(
    pipe: PullPipe
): AbstractSink<BinaryType>(pipe), BinaryReadable {

    private inline fun <reified E : Any> buildFromSegment(length: Int): Long {
        var value = 0L
        repeat(length) { cnt ->
            if(pos == seg.limit) pullSegment<Unit>()
            value = value or ((seg.getByte(pos++).toLong() and 0xff) shl TypeBits.byte * cnt)
        }
        return value
    }

    private inline fun <reified E : Any> withSegmentByte(): Byte = when(seg.limit - pos < TypeSize.byte) {
        false -> seg.getByte(pos++)
        else -> buildFromSegment<Unit>(TypeSize.byte).toByte()
    }

    private inline fun <reified E : Any> withSegmentShort(): Short = when(seg.limit - pos < TypeSize.short) {
        false -> seg.getShort(pos).also { pos += TypeSize.short }
        else -> buildFromSegment<Unit>(TypeSize.short).toShort()
    }

    private inline fun <reified E : Any> withSegmentInt(): Int = when(seg.limit - pos < TypeSize.int) {
        false -> seg.getInt(pos).also { pos += TypeSize.int }
        else -> buildFromSegment<Unit>(TypeSize.int).toInt()
    }

    private inline fun <reified E : Any> withSegmentLong(): Long = when(seg.limit - pos < TypeSize.long) {
        false -> seg.getLong(pos).also { pos += TypeSize.long }
        else -> buildFromSegment<Unit>(TypeSize.long)
    }

    private inline fun <reified E : Any> withSegmentRevShort(): Short = when(seg.limit - pos < TypeSize.short) {
        false -> seg.getRevShort(pos).also { pos += TypeSize.short }
        else -> buildFromSegment<Unit>(TypeSize.short).toShort()
    }

    private inline fun <reified E : Any> withSegmentRevInt(): Int = when(seg.limit - pos < TypeSize.int) {
        false -> seg.getRevInt(pos).also { pos += TypeSize.int }
        else -> buildFromSegment<Unit>(TypeSize.int).toInt()
    }

    private inline fun <reified E : Any> withSegmentRevLong(): Long = when(seg.limit - pos < TypeSize.long) {
        false -> seg.getRevLong(pos).also { pos += TypeSize.long }
        else -> buildFromSegment<Unit>(TypeSize.long)
    }

    override fun readByte(): Byte = withSegmentByte<Unit>()

    override fun readUByte(): UByte = withSegmentByte<Unit>().conv2uB<Unit>()

    override fun readShort(): Short = withSegmentShort<Unit>()

    override fun readUShort(): UShort = withSegmentShort<Unit>().conv2uS<Unit>()

    override fun readInt(): Int = withSegmentInt<Unit>()

    override fun readUInt(): UInt = withSegmentInt<Unit>().conv2uI<Unit>()

    override fun readLong(): Long = withSegmentLong<Unit>()

    override fun readULong(): ULong = withSegmentLong<Unit>().conv2uL<Unit>()

    override fun readFloat(): Float = withSegmentInt<Unit>().conv2F<Unit>()

    override fun readDouble(): Double = withSegmentLong<Unit>().conv2D<Unit>()

    override fun readRevShort(): Short = withSegmentRevShort<Unit>()

    override fun readRevUShort(): UShort = withSegmentRevShort<Unit>().conv2uS<Unit>()

    override fun readRevInt(): Int = withSegmentRevInt<Unit>()

    override fun readRevUInt(): UInt = withSegmentRevInt<Unit>().conv2uI<Unit>()

    override fun readRevLong(): Long = withSegmentRevLong<Unit>()

    override fun readRevULong(): ULong = withSegmentRevLong<Unit>().conv2uL<Unit>()

    override fun readRevFloat(): Float = withSegmentRevInt<Unit>().conv2F<Unit>()

    override fun readRevDouble(): Double = withSegmentRevLong<Unit>().conv2D<Unit>()
}