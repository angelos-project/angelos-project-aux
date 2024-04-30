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
public actual class UShortBuffer actual constructor(
    size: Int, idxOff: Int, idxEnd: Int
) : AbstractBufferType<UShort>(size, typeSize, idxOff, idxEnd) {

    public actual constructor(size: Int) : this(size, 0, size)

    override fun create(size: Int, idxOff: Int, idxEnd: Int): UShortBuffer = UShortBuffer(size, idxOff, idxEnd)

    override fun copyOf(): AbstractBufferType<UShort> {
        TODO("Not yet implemented")
    }

    public override fun copyOfRange(idxFrom: Int, idxTo: Int): UShortBuffer = copyOfRange2(idxFrom, idxTo) as UShortBuffer

    actual override operator fun get(index: Int): UShort {
        index.checkRange<Reify>()
        return unsafe.getShort(ptr + index * TypeSize.uShort).toUShort()
    }

    actual override operator fun set(index: Int, value: UShort) {
        index.checkRange<Reify>()
        unsafe.putShort(ptr + index * TypeSize.uShort, value.toShort())
    }

    public actual companion object {
        internal val unsafe: Unsafe = Chunk.unsafe
        public actual val typeSize: TypeSize = TypeSize.U_SHORT
    }
}