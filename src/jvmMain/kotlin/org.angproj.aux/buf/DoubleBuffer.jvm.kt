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
import sun.misc.Unsafe
import org.angproj.aux.res.Memory as Chunk

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public actual class DoubleBuffer actual constructor(
    size: Int, idxOff: Int, idxEnd: Int
) : AbstractBufferType<Double>(size, typeSize, idxOff, idxEnd) {

    public actual constructor(size: Int) : this(size, 0, size)

    override fun create(size: Int, idxOff: Int, idxEnd: Int): DoubleBuffer = DoubleBuffer(size, idxOff, idxEnd)

    override fun copyOf(): AbstractBufferType<Double> {
        TODO("Not yet implemented")
    }

    public override fun copyOfRange(idxFrom: Int, idxTo: Int): DoubleBuffer = copyOfRange2(idxFrom, idxTo) as DoubleBuffer

    actual override operator fun get(index: Int): Double {
        if (index !in 0..<size) throw IllegalArgumentException("Out of bounds.")
        return Double.fromBits(unsafe.getLong(ptr + index * TypeSize.double))

    }

    actual override operator fun set(index: Int, value: Double) {
        if (index !in 0..<size) throw IllegalArgumentException("Out of bounds.")
        unsafe.putLong(ptr + index * TypeSize.double, value.toBits())

    }

    public actual companion object {
        internal val unsafe: Unsafe = Chunk.unsafe
        public actual val typeSize: TypeSize = TypeSize.DOUBLE
    }
}