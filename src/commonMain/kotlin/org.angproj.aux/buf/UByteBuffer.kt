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


public class UByteBuffer internal constructor(
    segment: Segment<*>, view: Boolean = false
): ArrayBuffer<UByte>(segment, view, TypeSize.U_BYTE) {

    public constructor(size: Int) : this(Default.allocate(size * TypeSize.uByte))

    public constructor(size: DataSize = DataSize._4K) : this(size.size / TypeSize.uByte)

    override fun create(segment: Segment<*>): UByteBuffer = UByteBuffer(segment)

    override fun get(index: Int): UByte = _segment.getByte(index).conv2uB<Unit>()

    override fun set(index: Int, value: UByte) {
        _segment.setByte(index, value.conv2B<Unit>())
    }
}

public fun UByteBuffer.isNull(): Boolean = NullObject.uByteBuffer === this
private val nullUByteBuffer = UByteBuffer(NullObject.segment)
public val NullObject.uByteBuffer: UByteBuffer
    get() = nullUByteBuffer