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
package org.angproj.aux.buf

import org.angproj.aux.io.Bytes
import org.angproj.aux.io.DataSize
import org.angproj.aux.io.Segment
import org.angproj.aux.io.TypeSize


public class ByteBuffer internal constructor(
    segment: Segment, view: Boolean = false
): ArrayBuffer<Byte>(segment, view, TypeSize.BYTE) {

    public constructor(size: Int) : this(Bytes(size * TypeSize.byte))

    public constructor(size: DataSize = DataSize._4K) : this(size.size / TypeSize.byte)

    override fun create(segment: Segment): ByteBuffer = ByteBuffer(segment)

    override fun get(index: Int): Byte = _segment.getByte(index)

    override fun set(index: Int, value: Byte) {
        _segment.setByte(index, value)
    }
}

public fun ByteArray.toByteBuffer(): ByteBuffer = ByteBuffer(this.size).also {
    this.forEachIndexed { index, v -> it[index] = v }
}