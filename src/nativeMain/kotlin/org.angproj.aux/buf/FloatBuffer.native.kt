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
import org.angproj.aux.io.innerMemCopyOfRange

@Suppress(
    "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"
)
@OptIn(ExperimentalForeignApi::class)
public actual class FloatBuffer actual constructor(
    size: Int, idxOff: Int, idxEnd: Int
) : AbstractBufferType<Float>(size, typeSize, idxOff, idxEnd) {

    public actual constructor(size: Int) : this(size, 0, size)

    override fun create(size: Int, idxOff: Int, idxEnd: Int): FloatBuffer = FloatBuffer(size, idxOff, idxEnd)

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

public actual fun FloatBuffer.copyOfRange(
    idxFrom: Int,
    idxTo: Int
): FloatBuffer = innerCopyOfRange(idxFrom, idxTo)

public actual fun FloatBuffer.copyOf(): FloatBuffer = innerCopyOfRange(0, size)