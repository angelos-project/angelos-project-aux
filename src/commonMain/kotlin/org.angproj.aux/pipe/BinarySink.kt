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
import org.angproj.aux.io.TypeSize
import org.angproj.aux.util.Reifiable
import org.angproj.aux.util.Reify
import kotlin.math.min

public class BinarySink(
    pipe: PullPipe<BinaryType>
): AbstractSink<BinaryType>(pipe), BinaryType, BinaryReadable {

    private inline fun <reified : Reifiable> buildFromSegment(length: Int, maxIter: Int): Long {
        var value = 0L
        repeat(min(length, maxIter)) {
            if(pos == seg.limit) pullSegment<Reify>()
            ((value shl 8) or (seg.getByte(pos++).toLong() and 0xff)).also { value = it }
        }
        return value
    }

    private inline fun <reified R : Reifiable> withSegmentByte(): Byte = when(seg.limit - pos < TypeSize.byte) {
        true -> seg.getByte(pos).also { pos += TypeSize.byte }
        else -> buildFromSegment<Reify>(TypeSize.byte, TypeSize.byte).toByte()
    }

    private inline fun <reified R : Reifiable> withSegmentShort(): Short = when(seg.limit - pos < TypeSize.short) {
        true -> seg.getShort(pos).also { pos += TypeSize.short }
        else -> buildFromSegment<Reify>(TypeSize.short, TypeSize.short).toShort()
    }

    private inline fun <reified R : Reifiable> withSegmentInt(): Int = when(seg.limit - pos < TypeSize.int) {
        true -> seg.getInt(pos).also { pos += TypeSize.int }
        else -> buildFromSegment<Reify>(TypeSize.int, TypeSize.int).toInt()
    }

    private inline fun <reified R : Reifiable> withSegmentLong(): Long = when(seg.limit - pos < TypeSize.long) {
        true -> seg.getLong(pos).also { pos += TypeSize.long }
        else -> buildFromSegment<Reify>(TypeSize.long, TypeSize.long)
    }

    override fun readByte(): Byte = withSegmentByte<Reify>()

    override fun readUByte(): UByte = withSegmentByte<Reify>().toUByte()

    override fun readShort(): Short = withSegmentShort<Reify>()

    override fun readUShort(): UShort = withSegmentShort<Reify>().toUShort()

    override fun readInt(): Int = withSegmentInt<Reify>()

    override fun readUInt(): UInt = withSegmentInt<Reify>().toUInt()

    override fun readLong(): Long = withSegmentLong<Reify>()

    override fun readULong(): ULong = withSegmentLong<Reify>().toULong()

    override fun readFloat(): Float = Float.fromBits(withSegmentInt<Reify>())

    override fun readDouble(): Double = Double.fromBits(withSegmentLong<Reify>())
}