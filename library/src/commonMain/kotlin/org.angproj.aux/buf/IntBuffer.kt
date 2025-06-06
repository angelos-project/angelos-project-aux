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


public class IntBuffer internal constructor(
    segment: Segment<*>, view: Boolean = false
): ArrayBuffer<Int>(segment, view, TypeSize.INT) {

    public constructor(size: Int) : this(Default.allocate(size * TypeSize.int))

    public constructor(size: DataSize = DataSize._4K) : this(size.size / TypeSize.int)

    override fun create(segment: Segment<*>): IntBuffer = IntBuffer(segment)

    override fun get(index: Int): Int = _segment.getInt(index * TypeSize.int)

    override fun set(index: Int, value: Int) {
        _segment.setInt(index * TypeSize.int, value)
    }
}

public fun IntArray.toIntBuffer(): IntBuffer = IntBuffer(this.size).also {
    this.forEachIndexed { index, v -> it[index] = v }
}

public fun IntBuffer.isNull(): Boolean = NullObject.intBuffer === this
private val nullIntBuffer = IntBuffer(NullObject.segment)
public val NullObject.intBuffer: IntBuffer
    get() = nullIntBuffer