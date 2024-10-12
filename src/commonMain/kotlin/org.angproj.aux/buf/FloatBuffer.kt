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


public class FloatBuffer internal constructor(
    segment: Segment, view: Boolean = false
): ArrayBuffer<Float>(segment, view, TypeSize.FLOAT), NumberAware {

    public constructor(size: Int) : this(Bytes(size * TypeSize.float))

    public constructor(size: DataSize = DataSize._4K) : this(size.size / TypeSize.float)

    override fun create(segment: Segment): FloatBuffer = FloatBuffer(segment)

    override fun get(index: Int): Float = _segment.getInt(index * TypeSize.float).conv2F()

    override fun set(index: Int, value: Float) {
        _segment.setInt(index * TypeSize.float, value.conv2I())
    }
}

public fun FloatArray.toFloatBuffer(): FloatBuffer = FloatBuffer(this.size).also {
    this.forEachIndexed { index, v -> it[index] = v }
}

public fun FloatBuffer.isNull(): Boolean = NullObject.floatBuffer === this
private val nullFloatBuffer = FloatBuffer(NullObject.segment)
public val NullObject.floatBuffer: FloatBuffer
    get() = nullFloatBuffer