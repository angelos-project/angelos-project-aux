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
public actual class UByteBuffer actual constructor(
    size: Int, idxLimit: Int
) : AbstractBufferType<UByte>(size, typeSize, idxLimit) {

    public actual constructor(size: Int) : this(size, size)

    override fun create(size: Int, idxLimit: Int): UByteBuffer = UByteBuffer(size, idxLimit)

    actual override operator fun get(index: Int): UByte {
        index.checkRange<Reify>()
        return unsafe.getByte(ptr + index).toUByte()
    }

    actual override operator fun set(index: Int, value: UByte) {
        index.checkRange<Reify>()
        unsafe.putByte(ptr + index, value.toByte())
    }

    override fun <T: AbstractSpeedCopy> calculateInto(dest: T, destOff: Int, idxFrom: Int, idxTo: Int) {
        val ts = idxSize.size
        innerCopy(dest as UByteBuffer, destOff * ts, idxFrom * ts, idxTo * ts)
    }

    public actual companion object {
        internal val unsafe: Unsafe = Chunk.unsafe
        public actual val typeSize: TypeSize = TypeSize.U_BYTE
    }
}