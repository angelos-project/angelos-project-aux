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


public class ShortBuffer internal constructor(
    segment: Segment, view: Boolean = false
): ArrayBuffer<Short>(segment, view, TypeSize.SHORT) {

    public constructor(size: Int) : this(Bytes(size * TypeSize.short))

    public constructor(size: DataSize = DataSize._4K) : this(size.size / TypeSize.short)

    override fun create(segment: Segment): ShortBuffer = ShortBuffer(segment)

    override fun get(index: Int): Short = _segment.getShort(index * TypeSize.short)

    override fun set(index: Int, value: Short) {
        _segment.setShort(index * TypeSize.short, value)
    }
}

public fun ShortArray.toShortBuffer(): ShortBuffer = ShortBuffer(this.size).also {
    this.forEachIndexed { index, v -> it[index] = v }
}

public fun ShortBuffer.isNull(): Boolean = NullObject.shortBuffer === this
private val nullShortBuffer = ShortBuffer(NullObject.segment)
public val NullObject.shortBuffer: ShortBuffer
    get() = nullShortBuffer