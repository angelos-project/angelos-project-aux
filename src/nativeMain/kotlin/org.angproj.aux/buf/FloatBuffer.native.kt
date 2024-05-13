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
import org.angproj.aux.util.Reify

@Suppress(
    "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"
)
@OptIn(ExperimentalForeignApi::class)
public actual class FloatBuffer actual constructor(
    size: Int, idxLimit: Int
) : AbstractBufferType<Float>(size, typeSize, idxLimit) {

    public actual constructor(size: Int) : this(size, size)

    override fun create(size: Int, idxLimit: Int): FloatBuffer = FloatBuffer(size, idxLimit)

    actual override operator fun get(index: Int): Float {
        index.checkRange<Reify>()
        return (ptr + index * TypeSize.float).toCPointer<FloatVar>()!!.pointed.value
    }

    actual override operator fun set(index: Int, value: Float) {
        index.checkRange<Reify>()
        (ptr + index * TypeSize.float).toCPointer<FloatVar>()!!.pointed.value = value
    }

    public actual companion object {
        public actual val typeSize: TypeSize = TypeSize.FLOAT
    }
}

public fun FloatBuffer.copyInto(destination: FloatBuffer, destinationOffset: Int, fromIndex: Int, toIndex: Int) {
    innerCopy(destination, destinationOffset, fromIndex, toIndex)
}