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
package org.angproj.aux.util.rand

import org.angproj.aux.util.RegistryProxy

public class RandomProxy(private val item: AbstractBufferedRandom): RandomGenerator, RegistryProxy {
    override fun getByte(): Byte = item.getByte()

    override fun getUByte(): UByte = item.getUByte()

    override fun getShort(): Short = item.getShort()

    override fun getUShort(): UShort = item.getUShort()

    override fun getInt(): Int = item.getInt()

    override fun getUInt(): UInt = item.getUInt()

    override fun getLong(): Long = item.getLong()

    override fun getULong(): ULong = item.getULong()

    override fun getFloat(): Float = item.getFloat()

    override fun getDouble(): Double = item.getDouble()

    override fun getByteArray(size: Int): ByteArray = item.getByteArray(size)

    override fun getShortArray(size: Int): ShortArray = item.getShortArray(size)

    override fun getIntArray(size: Int): IntArray = item.getIntArray(size)

    override fun getLongArray(size: Int): LongArray = item.getLongArray(size)

    override fun getFloatArray(size: Int): FloatArray = item.getFloatArray(size)

    override fun getDoubleArray(size: Int): DoubleArray = item.getDoubleArray(size)

    override val identifier: String
        get() = item.identifier
}