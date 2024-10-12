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


public class DoubleBuffer internal constructor(
    segment: Segment, view: Boolean = false
): ArrayBuffer<Double>(segment, view, TypeSize.DOUBLE), NumberAware {

    public constructor(size: Int) : this(Bytes(size * TypeSize.double))

    public constructor(size: DataSize = DataSize._4K) : this(size.size / TypeSize.double)

    override fun create(segment: Segment): DoubleBuffer = DoubleBuffer(segment)

    override fun get(index: Int): Double = _segment.getLong(index * TypeSize.long).conv2D()

    override fun set(index: Int, value: Double) {
        _segment.setLong(index * TypeSize.long, value.conv2L())
    }
}

public fun DoubleArray.toDoubleBuffer(): DoubleBuffer = DoubleBuffer(this.size).also {
    this.forEachIndexed { index, v -> it[index] = v }
}

public fun DoubleBuffer.isNull(): Boolean = NullObject.doubleBuffer === this
private val nullDoubleBuffer = DoubleBuffer(NullObject.segment)
public val NullObject.doubleBuffer: DoubleBuffer
    get() = nullDoubleBuffer