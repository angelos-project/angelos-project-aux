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
public actual class ULongBuffer actual constructor(
    size: Int, idxOff: Int, idxEnd: Int
) : AbstractBufferType<ULong>(size, typeSize, idxOff, idxEnd) {

    public actual constructor(size: Int) : this(size, 0, size)

    override val marginSized: Int = SpeedCopy.addMarginByIndexType(size, idxSize)
    override fun create(size: Int, idxOff: Int, idxEnd: Int): AbstractBufferType<ULong> {
        TODO("Not yet implemented")
    }

    override val length: Int = marginSized * idxSize.size
    private val data: LongArray = LongArray(marginSized)

    actual override operator fun get(index: Int): ULong {
        //if(index !in 0..<size) throw IllegalArgumentException("Out of bounds.")
        return data[index].toULong()
    }

    actual override operator fun set(index: Int, value: ULong) {
        //if(index !in 0..<size) throw IllegalArgumentException("Out of bounds.")
        data[index] = value.toLong()
    }

    public actual companion object {
        public actual val typeSize: TypeSize = TypeSize.U_LONG
    }
}

public actual fun ULongBuffer.copyInto(
    destination: ULongBuffer,
    destinationOffset: Int,
    fromIndex: Int,
    toIndex: Int
) {
}