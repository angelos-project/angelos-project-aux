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

public interface MutableByteString : ByteString {

    public fun setByte(index: Int, value: Byte): Unit
    public fun setShort(index: Int, value: Short): Unit
    public fun setInt(index: Int, value: Int): Unit
    public fun setLong(index: Int, value: Long): Unit

    public operator fun set(index: Int, value: Byte): Unit = setByte(index, value)
}