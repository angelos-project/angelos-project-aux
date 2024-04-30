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

import kotlinx.cinterop.*
import org.angproj.aux.io.TypeSize

@Suppress(
    "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"
)
@OptIn(ExperimentalForeignApi::class)
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
        index.checkRange<Reify>()
        return (ptr + index * TypeSize.double).toCPointer<DoubleVar>()!!.pointed.value
    }

    actual override operator fun set(index: Int, value: Double) {
        index.checkRange<Reify>()
        (ptr + index * TypeSize.double).toCPointer<DoubleVar>()!!.pointed.value = value
    }

    public actual companion object {
        public actual val typeSize: TypeSize = TypeSize.DOUBLE
    }
}