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
import org.angproj.aux.mem.Default
import org.angproj.aux.util.NullObject


public class UIntBuffer internal constructor(
    segment: Segment<*>, view: Boolean = false
): ArrayBuffer<UInt>(segment, view, TypeSize.U_INT) {

    public constructor(size: Int) : this(Default.allocate(size * TypeSize.uInt))

    public constructor(size: DataSize = DataSize._4K) : this(size.size / TypeSize.uInt)

    override fun create(segment: Segment<*>): UIntBuffer = UIntBuffer(segment)

    override fun get(index: Int): UInt = _segment.getInt(index * TypeSize.uInt).conv2uI()

    override fun set(index: Int, value: UInt) {
        _segment.setInt(index * TypeSize.uInt, value.conv2I())
    }
}

public fun UIntBuffer.isNull(): Boolean = NullObject.uIntBuffer === this
private val nullUIntBuffer = UIntBuffer(NullObject.segment)
public val NullObject.uIntBuffer: UIntBuffer
    get() = nullUIntBuffer