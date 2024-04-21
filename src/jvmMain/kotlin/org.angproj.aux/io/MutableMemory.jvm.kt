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
package org.angproj.aux.io

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public actual class MutableMemory actual constructor(size: Int) : Memory(size), MutableSegment {

    actual override fun setByte(index: Int, value: Byte) {
        if(index !in 0..<size) throw IllegalArgumentException("Out of bounds.")
        unsafe.putByte(ptr + index, value)
    }

    actual override fun setShort(index: Int, value: Short) {
        if(index !in 0..<(size-1)) throw IllegalArgumentException("Out of bounds.")
        unsafe.putShort(ptr + index, value)
    }

    actual override fun setInt(index: Int, value: Int) {
        if(index !in 0..<(size-3)) throw IllegalArgumentException("Out of bounds.")
        unsafe.putInt(ptr + index, value)
    }

    actual override fun setLong(index: Int, value: Long) {
        if(index !in 0..<(size-7)) throw IllegalArgumentException("Out of bounds.")
        unsafe.putLong(ptr + index, value)
    }
}