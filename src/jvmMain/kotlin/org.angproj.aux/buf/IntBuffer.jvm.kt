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
public actual class IntBuffer actual constructor(
    size: Int, idxOff: Int, idxEnd: Int
) : AbstractBufferType<Int>(size, typeSize, idxOff, idxEnd) {

    public actual constructor(size: Int) : this(size, 0, size)

    override fun create(size: Int, idxOff: Int, idxEnd: Int): IntBuffer = IntBuffer(size, idxOff, idxEnd)

    override fun copyOf(): AbstractBufferType<Int> {
        TODO("Not yet implemented")
    }

    public override fun copyOfRange(idxFrom: Int, idxTo: Int): IntBuffer = copyOfRange2(idxFrom, idxTo) as IntBuffer

    actual override operator fun get(index: Int): Int {
        if (index !in 0..<size) throw IllegalArgumentException("Out of bounds.")
        return unsafe.getInt(ptr + index * TypeSize.int)
    }

    actual override operator fun set(index: Int, value: Int) {
        if (index !in 0..<size) throw IllegalArgumentException("Out of bounds.")
        unsafe.putInt(ptr + index * TypeSize.int, value)
    }

    public actual companion object {
        internal val unsafe: Unsafe = Chunk.unsafe
        public actual val typeSize: TypeSize = TypeSize.INT
    }
}