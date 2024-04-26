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
public actual class ShortBuffer actual constructor(size: Int) : AbstractBufferType<Short>(size, typeSize) {

    override val marginSize: Int = SpeedCopy.addMarginByIndexType(size, idxSize)
    override val length: Int = marginSize * idxSize.size
    private val data: ShortArray = ShortArray(marginSize)

    actual override operator fun get(index: Int): Short {
        if (index !in 0..<size) throw IllegalArgumentException("Out of bounds.")
        return data[index + idxOff]
    }

    actual override operator fun set(index: Int, value: Short) {
        if (index !in 0..<size) throw IllegalArgumentException("Out of bounds.")
        data[index + idxOff] = value
    }

    public actual companion object {
        public actual val typeSize: TypeSize = TypeSize.SHORT
    }
}