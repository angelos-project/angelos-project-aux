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
import org.angproj.aux.util.NumberAware


public class UByteBuffer internal constructor(
    segment: Segment, view: Boolean = false
): ArrayBuffer<UByte>(segment, view, TypeSize.U_BYTE), NumberAware{

    public constructor(size: Int) : this(Bytes(size * TypeSize.uByte))

    public constructor(size: DataSize = DataSize._4K) : this(size.size / TypeSize.uByte)

    override fun create(segment: Segment): UByteBuffer = UByteBuffer(segment)

    override fun get(index: Int): UByte = _segment.getByte(index).conv2uB()

    override fun set(index: Int, value: UByte) {
        _segment.setByte(index, value.conv2B())
    }
}