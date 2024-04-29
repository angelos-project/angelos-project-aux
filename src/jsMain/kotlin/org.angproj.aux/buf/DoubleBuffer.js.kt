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
public actual class DoubleBuffer actual constructor(
    size: Int, idxOff: Int, idxEnd: Int
) : AbstractBufferType<Double>(size, typeSize, idxOff, idxEnd) {

    public actual constructor(size: Int) : this(size, 0, size)

    override fun create(size: Int): DoubleBuffer = DoubleBuffer(size)
    override fun copyOf(): DoubleBuffer = create(idxEnd).also { data.copyInto(it.data) }

    public fun copyOfRange(fromIdx: Int, toIdx: Int): DoubleBuffer {
        check(fromIdx in 0..<size && toIdx in 0..<size && fromIdx < toIdx)
        val from = fromIdx + idxOff
        val to = toIdx + idxOff
        return create(to - from).also { data.copyInto(it.data, 0, from, to) }
    }

    override val marginSized: Int = SpeedCopy.addMarginByIndexType(size, idxSize)
    override val length: Int = marginSized * idxSize.size
    private val data: DoubleArray = DoubleArray(marginSized)

    actual override operator fun get(index: Int): Double {
        //if(index !in 0..<size) throw IllegalArgumentException("Out of bounds.")
        return data[index]
    }

    actual override operator fun set(index: Int, value: Double) {
        //if(index !in 0..<size) throw IllegalArgumentException("Out of bounds.")
        data[index] = value
    }

    public actual companion object {
        public actual val typeSize: TypeSize = TypeSize.DOUBLE
    }
}