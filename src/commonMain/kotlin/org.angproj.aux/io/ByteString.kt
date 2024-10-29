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

import org.angproj.aux.com.Limitable
import org.angproj.aux.util.Copyable

@OptIn(ExperimentalStdlibApi::class)
public interface ByteString: Limitable, Copyable, AutoCloseable {

    public override fun getByte(index: Int): Byte
    public fun getShort(index: Int): Short
    public fun getInt(index: Int): Int
    public override fun getLong(index: Int): Long

    public override fun setByte(index: Int, value: Byte): Unit
    public fun setShort(index: Int, value: Short): Unit
    public fun setInt(index: Int, value: Int): Unit
    public override fun setLong(index: Int, value: Long): Unit

    public override fun close()

    public companion object {

        public val typeSizes: Set<Int> = setOf(TypeSize.byte, TypeSize.short, TypeSize.int, TypeSize.long)
    }
}