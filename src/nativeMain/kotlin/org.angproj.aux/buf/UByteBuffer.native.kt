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

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING", "OVERRIDE_BY_INLINE")
@OptIn(ExperimentalForeignApi::class)
public actual class UByteBuffer actual constructor(size: Int) : AbstractBufferType<UByte>(size, typeSize) {

    override val length: Int = data.size
    override val marginSize: Int = length / idxSize.size

    actual override inline operator fun get(index: Int): UByte {
        if (index !in 0..<size) throw IllegalArgumentException("Out of bounds.")
        return (ptr + index)!!.reinterpret<UByteVar>().pointed.value
    }

    actual override inline operator fun set(index: Int, value: UByte) {
        if (index !in 0..<size) throw IllegalArgumentException("Out of bounds.")
        (ptr + index)!!.reinterpret<UByteVar>().pointed.value = value
    }

    public actual companion object {
        public actual val typeSize: TypeSize = TypeSize.U_BYTE
    }
}