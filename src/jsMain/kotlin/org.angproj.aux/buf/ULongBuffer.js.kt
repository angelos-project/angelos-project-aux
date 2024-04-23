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
public actual class ULongBuffer actual constructor(size: Int) : AbstractBufferType<ULong>(size) {

    override val idxSize: TypeSize = TypeSize.U_LONG
    private val data: LongArray = LongArray(SpeedCopy.addMargin(size, idxSize))

    actual override operator fun get(index: Int): ULong {
        //if(index !in 0..<size) throw IllegalArgumentException("Out of bounds.")
        return data[index].toULong()
    }

    actual override operator fun set(index: Int, value: ULong) {
        //if(index !in 0..<size) throw IllegalArgumentException("Out of bounds.")
        data[index] = value.toLong()
    }
}