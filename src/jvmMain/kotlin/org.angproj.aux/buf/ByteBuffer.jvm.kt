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
public actual class ByteBuffer actual constructor(size: Int) : AbstractBufferType<Byte>(size, typeSize) {

    actual override operator fun get(index: Int): Byte {
        if (index !in 0..<size) throw IllegalArgumentException("Out of bounds.")
        //return unsafe.getByte(ptr + (index + idxOff) * idxSize.size)
        //return unsafe.getByte(ptr + (index) * idxSize.size)
        return unsafe.getByte(ptr + index)

    }

    actual override operator fun set(index: Int, value: Byte) {
        if (index !in 0..<size) throw IllegalArgumentException("Out of bounds.")
        //unsafe.putByte(ptr + (index + idxOff) * idxSize.size, value)
        //unsafe.putByte(ptr + (index) * idxSize.size, value)
        unsafe.putByte(ptr + index, value)

    }

    public actual companion object {
        internal val unsafe: Unsafe = Chunk.unsafe
        public actual val typeSize: TypeSize = TypeSize.BYTE
    }
}