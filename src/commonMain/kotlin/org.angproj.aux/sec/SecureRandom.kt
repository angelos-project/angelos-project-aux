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
package org.angproj.aux.sec

import org.angproj.aux.io.Readable
import org.angproj.aux.io.Reader
import org.angproj.aux.util.BufferSize
import org.angproj.aux.util.DataBuffer
import org.angproj.aux.util.floorMod
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
public object SecureRandom : Reader, Readable {

    private val buffer = DataBuffer(BufferSize._1K.size)

    init {
        refill()
    }

    private fun refill() {
        buffer.reset(false)
        SecureFeed.read(buffer.getArray())
    }

    private fun <E> withinLimit(size: Int, block: () -> E): E {
        if (buffer.remaining < size) refill()
        return block()
    }

    override fun readByte(): Byte = withinLimit(Byte.SIZE_BYTES) { buffer.readByte() }
    override fun readUByte(): UByte = withinLimit(UByte.SIZE_BYTES) { buffer.readUByte() }
    override fun readChar(): Char = withinLimit(Char.SIZE_BYTES) { buffer.readChar() }
    override fun readShort(): Short = withinLimit(Short.SIZE_BYTES) { buffer.readShort() }
    override fun readUShort(): UShort = withinLimit(UShort.SIZE_BYTES) { buffer.readUShort() }
    override fun readInt(): Int = withinLimit(Int.SIZE_BYTES) { buffer.readInt() }
    override fun readUInt(): UInt = withinLimit(UInt.SIZE_BYTES) { buffer.readUInt() }
    override fun readLong(): Long = withinLimit(Long.SIZE_BYTES) { buffer.readLong() }
    override fun readULong(): ULong = withinLimit(ULong.SIZE_BYTES) { buffer.readULong() }
    override fun readFloat(): Float = withinLimit(Float.SIZE_BYTES) { buffer.readFloat() }
    override fun readDouble(): Double = withinLimit(Double.SIZE_BYTES) { buffer.readDouble() }

    private fun fill(data: ByteArray) {
        val buffer = ByteArray(BufferSize._1K.size)
        (data.indices step buffer.size).forEach { offset ->
            SecureFeed.read(buffer)
            buffer.copyInto(data, offset, 0, buffer.size)
        }

        val remaining = data.size.floorMod(buffer.size)
        if (remaining > 0) {
            SecureFeed.read(buffer)
            buffer.copyInto(data, data.size - remaining, 0, remaining)
        }
    }

    override fun read(length: Int): ByteArray = ByteArray(length).also { fill(it) }

    override fun read(data: ByteArray): Int = (data.size).also { fill(data) }
}