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

public interface RandomGenerator: RegistryProxy {
    public fun getByte(): Byte

    public fun getUByte(): UByte

    public fun getShort(): Short

    public fun getUShort(): UShort

    public fun getInt(): Int

    public fun getUInt(): UInt

    public fun getLong(): Long

    public fun getULong(): ULong

    public fun getFloat(): Float

    public fun getDouble(): Double

    public fun getByteArray(size: Int): ByteArray

    public fun getShortArray(size: Int): ShortArray

    public fun getIntArray(size: Int): IntArray

    public fun getLongArray(size: Int): LongArray

    public fun getFloatArray(size: Int): FloatArray

    public fun getDoubleArray(size: Int): DoubleArray
}