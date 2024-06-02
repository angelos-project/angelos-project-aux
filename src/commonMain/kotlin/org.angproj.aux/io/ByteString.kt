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
package org.angproj.aux.io

@OptIn(ExperimentalStdlibApi::class)
public interface ByteString: AutoCloseable {

    public val size: Int
    public var limit: Int

    public fun getByte(index: Int): Byte
    public fun getShort(index: Int): Short
    public fun getInt(index: Int): Int
    public fun getLong(index: Int): Long

    public operator fun get(index: Int): Byte = getByte(index)

    public fun setByte(index: Int, value: Byte): Unit
    public fun setShort(index: Int, value: Short): Unit
    public fun setInt(index: Int, value: Int): Unit
    public fun setLong(index: Int, value: Long): Unit

    public operator fun set(index: Int, value: Byte): Unit = setByte(index, value)

    public override fun close() {}

    public companion object {

        public val typeSizes: Set<Int> = setOf(TypeSize.byte, TypeSize.short, TypeSize.int, TypeSize.long)
    }
}