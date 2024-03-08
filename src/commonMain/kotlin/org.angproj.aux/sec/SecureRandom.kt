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

import org.angproj.aux.rand.RandomGenerator
import org.angproj.aux.util.*
import kotlin.math.ceil
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
public object SecureRandom: RandomGenerator {

    private val buffer = ByteArray(1024)
    private var bufPos: Int = 0

    init {
        refill()
    }

    private fun refill() {
        repeat(1024 / 64) { index ->
            SecureFeed.getFeed(buffer, index * 64) }
        bufPos = 0
    }

    private fun <E> getValue(size: Int, block: () -> E): E {
        if (bufPos + size > buffer.lastIndex) refill()
        val value = block()
        bufPos += size
        return value
    }

    override fun getByte(): Byte = getValue(Byte.SIZE_BYTES) { buffer[bufPos] }

    override fun getUByte(): UByte = getValue(UByte.SIZE_BYTES) { buffer[bufPos].toUByte() }

    override fun getShort(): Short = getValue(Short.SIZE_BYTES) { buffer.readShortAt(bufPos) }

    override fun getUShort(): UShort = getValue(UShort.SIZE_BYTES) { buffer.readUShortAt(bufPos) }

    override fun getInt(): Int = getValue(Int.SIZE_BYTES) { buffer.readIntAt(bufPos) }

    override fun getUInt(): UInt = getValue(UInt.SIZE_BYTES) { buffer.readUIntAt(bufPos) }

    override fun getLong(): Long = getValue(Long.SIZE_BYTES) { buffer.readLongAt(bufPos) }

    override fun getULong(): ULong = getValue(ULong.SIZE_BYTES) { buffer.readULongAt(bufPos) }

    override fun getFloat(): Float = getValue(Float.SIZE_BYTES) { buffer.readFloatAt(bufPos) }

    override fun getDouble(): Double = getValue(Double.SIZE_BYTES) { buffer.readDoubleAt(bufPos) }

    override fun getByteArray(size: Int): ByteArray {
        val data = ByteArray(ceil(size / 64f).toInt())
        repeat(data.size / 64) { index ->
            SecureFeed.getFeed(data, index)
        }
        return data
    }

    override fun getShortArray(size: Int): ShortArray = ShortArray(size) { getShort() }

    override fun getIntArray(size: Int): IntArray = IntArray(size) { getInt() }

    override fun getLongArray(size: Int): LongArray = LongArray(size) { getLong() }

    override fun getFloatArray(size: Int): FloatArray = FloatArray(size) { getFloat() }

    override fun getDoubleArray(size: Int): DoubleArray = DoubleArray(size) { getDouble() }
}