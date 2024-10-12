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


public class LongBuffer internal constructor(
    segment: Segment, view: Boolean = false
): ArrayBuffer<Long>(segment, view, TypeSize.LONG) {

    public constructor(size: Int) : this(Bytes(size * TypeSize.long))

    public constructor(size: DataSize = DataSize._4K) : this(size.size / TypeSize.long)

    override fun create(segment: Segment): LongBuffer = LongBuffer(segment)

    override fun get(index: Int): Long = _segment.getLong(index * TypeSize.long)

    override fun set(index: Int, value: Long) {
        _segment.setLong(index * TypeSize.long, value)
    }
}

public fun LongArray.toLongBuffer(): LongBuffer = LongBuffer(this.size).also {
    this.forEachIndexed { index, v -> it[index] = v }
}

public fun LongBuffer.isNull(): Boolean = NullObject.longBuffer === this
private val nullLongBuffer = LongBuffer(NullObject.segment)
public val NullObject.longBuffer: LongBuffer
    get() = nullLongBuffer