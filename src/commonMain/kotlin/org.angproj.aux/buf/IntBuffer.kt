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


public class IntBuffer internal constructor(
    segment: Segment, view: Boolean = false
): ArrayBuffer<Int>(segment, view, TypeSize.INT) {

    public constructor(size: Int) : this(Bytes(size * TypeSize.int))

    public constructor(size: DataSize = DataSize._4K) : this(size.size / TypeSize.int)

    override fun create(segment: Segment): IntBuffer = IntBuffer(segment)

    override fun get(index: Int): Int = _segment.getInt(index * TypeSize.int)

    override fun set(index: Int, value: Int) {
        _segment.setInt(index * TypeSize.int, value)
    }
}

public fun IntArray.toIntBuffer(): IntBuffer = IntBuffer(this.size).also {
    this.forEachIndexed { index, v -> it[index] = v }
}