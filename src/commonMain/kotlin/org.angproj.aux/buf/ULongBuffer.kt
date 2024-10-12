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

import org.angproj.aux.io.*
import org.angproj.aux.util.NullObject
import org.angproj.aux.util.NumberAware


public class ULongBuffer internal constructor(
    segment: Segment, view: Boolean = false
): ArrayBuffer<ULong>(segment, view, TypeSize.U_LONG), NumberAware {

    public constructor(size: Int) : this(Bytes(size * TypeSize.uLong))

    public constructor(size: DataSize = DataSize._4K) : this(size.size / TypeSize.uLong)

    override fun create(segment: Segment): ULongBuffer = ULongBuffer(segment)

    override fun get(index: Int): ULong = _segment.getLong(index * TypeSize.uLong).conv2uL()

    override fun set(index: Int, value: ULong) {
        _segment.setLong(index * TypeSize.uLong, value.conv2L())
    }
}

public fun ULongBuffer.isNull(): Boolean = NullObject.uLongBuffer === this
private val nullULongBuffer = ULongBuffer(NullObject.segment)
public val NullObject.uLongBuffer: ULongBuffer
    get() = nullULongBuffer