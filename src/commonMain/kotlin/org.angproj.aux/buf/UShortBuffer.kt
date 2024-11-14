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
import org.angproj.aux.util.NumberAware


public class UShortBuffer internal constructor(
    segment: Segment<*>, view: Boolean = false
): ArrayBuffer<UShort>(segment, view, TypeSize.U_SHORT) {

    public constructor(size: Int) : this(Default.allocate(size * TypeSize.uShort))

    public constructor(size: DataSize = DataSize._4K) : this(size.size / TypeSize.uShort)

    override fun create(segment: Segment<*>): UShortBuffer = UShortBuffer(segment)

    override fun get(index: Int): UShort = _segment.getShort(index * TypeSize.uShort).conv2uS<Unit>()

    override fun set(index: Int, value: UShort) {
        _segment.setShort(index * TypeSize.uShort, value.conv2S<Unit>())
    }
}

public fun UShortBuffer.isNull(): Boolean = NullObject.uShortBuffer === this
private val nullUShortBuffer = UShortBuffer(NullObject.segment)
public val NullObject.uShortBuffer: UShortBuffer
    get() = nullUShortBuffer