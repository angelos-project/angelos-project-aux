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
public actual class ShortBuffer actual constructor(
    size: Int, idxLimit: Int
) : AbstractBufferType<Short>(size, typeSize, idxLimit) {

    public actual constructor(size: Int) : this(size, size)

    override fun create(size: Int, idxLimit: Int): ShortBuffer = ShortBuffer(size, idxLimit)

    actual override operator fun get(index: Int): Short {
        index.checkRange<Reify>()
        return unsafe.getShort(ptr + index * TypeSize.short)
    }

    actual override operator fun set(index: Int, value: Short) {
        index.checkRange<Reify>()
        unsafe.putShort(ptr + index * TypeSize.short, value)
    }

    public actual companion object {
        internal val unsafe: Unsafe = Chunk.unsafe
        public actual val typeSize: TypeSize = TypeSize.SHORT
    }
}

public actual fun ShortBuffer.copyInto(destination: ShortBuffer, destinationOffset: Int, fromIndex: Int, toIndex: Int) {
    val ts = ShortBuffer.typeSize.size
    innerCopy(destination, destinationOffset * ts, fromIndex * ts, toIndex * ts)
}