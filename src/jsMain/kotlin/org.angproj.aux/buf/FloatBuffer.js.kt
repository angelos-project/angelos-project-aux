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

import org.angproj.aux.io.TypeSize

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public actual class FloatBuffer actual constructor(
    size: Int, idxOff: Int, idxEnd: Int
) : AbstractBufferType<Float>(size, typeSize, idxOff, idxEnd) {

    public actual constructor(size: Int) : this(size, 0, size)

    override fun create(size: Int): FloatBuffer = FloatBuffer(size)
    override fun copyOf(): FloatBuffer = create(idxEnd).also { data.copyInto(it.data) }

    override val marginSize: Int = SpeedCopy.addMarginByIndexType(size, idxSize)
    override val length: Int = marginSize * idxSize.size
    private val data: FloatArray = FloatArray(marginSize)

    actual override operator fun get(index: Int): Float {
        if (index !in 0..<size) throw IllegalArgumentException("Out of bounds.")
        return data[index + idxOff]
    }

    actual override operator fun set(index: Int, value: Float) {
        if (index !in 0..<size) throw IllegalArgumentException("Out of bounds.")
        data[index + idxOff] = value
    }

    public actual companion object {
        public actual val typeSize: TypeSize = TypeSize.FLOAT
    }
}