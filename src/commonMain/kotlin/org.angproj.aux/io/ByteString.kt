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

import org.angproj.aux.buf.Reifiable

@OptIn(ExperimentalStdlibApi::class)
public interface ByteString: AutoCloseable {

    public val size: Int

    public fun getByte(index: Int): Byte
    public fun getShort(index: Int): Short
    public fun getInt(index: Int): Int
    public fun getLong(index: Int): Long

    public override fun close() {}

    public operator fun get(index: Int): Byte = getByte(index)

    public companion object {
        public const val byteSize: Int = Byte.SIZE_BYTES
        public const val shortSize: Int = Short.SIZE_BYTES
        public const val intSize: Int = Int.SIZE_BYTES
        public const val longSize: Int = Long.SIZE_BYTES

        public val typeSizes: Set<Int> = setOf(byteSize, shortSize, intSize, longSize)
    }
}