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
import org.angproj.aux.io.innerMemCopyOfRange
import sun.misc.Unsafe
import org.angproj.aux.res.Memory as Chunk

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public actual class ULongBuffer actual constructor(
    size: Int, idxOff: Int, idxEnd: Int
) : AbstractBufferType<ULong>(size, typeSize, idxOff, idxEnd) {

    public actual constructor(size: Int) : this(size, 0, size)

    override fun create(size: Int, idxOff: Int, idxEnd: Int): ULongBuffer = ULongBuffer(size, idxOff, idxEnd)

    actual override operator fun get(index: Int): ULong {
        index.checkRange<Reify>()
        return unsafe.getLong(ptr + index * TypeSize.uLong).toULong()
    }

    actual override operator fun set(index: Int, value: ULong) {
        index.checkRange<Reify>()
        unsafe.putLong(ptr + index * TypeSize.uLong, value.toLong())
    }

    public actual companion object {
        internal val unsafe: Unsafe = Chunk.unsafe
        public actual val typeSize: TypeSize = TypeSize.U_LONG
    }
}

public actual fun ULongBuffer.copyOfRange(
    idxFrom: Int,
    idxTo: Int
): ULongBuffer = innerCopyOfRange(idxFrom, idxTo)

public actual fun ULongBuffer.copyOf(): ULongBuffer = innerCopyOfRange(0, size)