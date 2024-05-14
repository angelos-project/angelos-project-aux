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
import org.angproj.aux.util.Reify
import sun.misc.Unsafe
import org.angproj.aux.res.Memory as Chunk

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public actual class UIntBuffer actual constructor(
    size: Int, idxLimit: Int
) : AbstractBufferType<UInt>(size, typeSize, idxLimit) {

    public actual constructor(size: Int) : this(size, size)

    override fun create(size: Int, idxLimit: Int): UIntBuffer = UIntBuffer(size, idxLimit)

    actual override operator fun get(index: Int): UInt {
        index.checkRange<Reify>()
        return unsafe.getInt(ptr + index * TypeSize.uInt).toUInt()
    }

    actual override operator fun set(index: Int, value: UInt) {
        index.checkRange<Reify>()
        unsafe.putInt(ptr + index * TypeSize.uInt, value.toInt())
    }

    public actual companion object {
        internal val unsafe: Unsafe = Chunk.unsafe
        public actual val typeSize: TypeSize = TypeSize.U_INT
    }
}

public actual fun UIntBuffer.copyInto(destination: UIntBuffer, destinationOffset: Int, fromIndex: Int, toIndex: Int) {
    val ts = UIntBuffer.typeSize.size
    innerCopy(destination, destinationOffset * ts, fromIndex * ts, toIndex * ts)
}