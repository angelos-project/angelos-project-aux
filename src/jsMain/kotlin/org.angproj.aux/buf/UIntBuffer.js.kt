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
public actual class UIntBuffer actual constructor(
    size: Int, idxOff: Int, idxEnd: Int
) : AbstractBufferType<UInt>(size, typeSize, idxOff, idxEnd) {

    public actual constructor(size: Int) : this(size, 0, size)

    override val marginSized: Int = SpeedCopy.addMarginByIndexType(size, idxSize)
    override fun create(size: Int, idxOff: Int, idxEnd: Int): AbstractBufferType<UInt> {
        TODO("Not yet implemented")
    }

    override val length: Int = marginSized * idxSize.size
    private val data: IntArray = IntArray(marginSized)

    actual override operator fun get(index: Int): UInt {
        if (index !in 0..<size) throw IllegalArgumentException("Out of bounds.")
        return data[index + idxOff].toUInt()
    }

    actual override operator fun set(index: Int, value: UInt) {
        if (index !in 0..<size) throw IllegalArgumentException("Out of bounds.")
        data[index + idxOff] = value.toInt()
    }

    public actual companion object {
        public actual val typeSize: TypeSize = TypeSize.U_INT
    }
}

actual fun UIntBuffer.copyInto(
    destination: UIntBuffer,
    destinationOffset: Int,
    fromIndex: Int,
    toIndex: Int
) {
}