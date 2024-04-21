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

public interface MutableSegment: Segment, MutableByteString {

    public fun Long.setLeftSide(offset: Int, size: Int, value: Long): Long {
        val pos = (size - (ByteString.longSize - offset)) * 8
        val mask = 0xffffffffffffffffuL.toLong() ushr ((ByteString.longSize - size) * 8)
        return ((mask ushr pos).inv() and this) or (value ushr pos)
    }

    public fun Long.setRightSide(offset: Int, size: Int, value: Long): Long {
        val pos = (ByteString.longSize - size - offset) * 8
        val mask = 0xffffffffffffffffuL.toLong() ushr ((ByteString.longSize - size) * 8)
        return ((mask shl pos).inv() and this) or (value shl pos)
    }
}