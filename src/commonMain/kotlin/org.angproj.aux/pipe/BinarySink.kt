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
import org.angproj.aux.io.isNull
import org.angproj.aux.util.Reify

public class BinarySink(
    pump: Pump = Pump
): AbstractSink<BinaryType>(pump), BinaryType, BinaryReadable {

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

    override fun dispose() {
        if(!seg.isNull()) seg.close()
    }
}