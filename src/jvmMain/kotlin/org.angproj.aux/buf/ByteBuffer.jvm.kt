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
public actual class ByteBuffer actual constructor(
    size: Int, idxOff: Int, idxEnd: Int
) : AbstractBufferType<Byte>(size, typeSize, idxOff, idxEnd) {

    public actual constructor(size: Int) : this(size, 0, size)

    override fun create(size: Int, idxOff: Int, idxEnd: Int): ByteBuffer = ByteBuffer(size, idxOff, idxEnd)

    actual override operator fun get(index: Int): Byte {
        index.checkRange<Reify>()
        return unsafe.getByte(ptr + index)
    }

    actual override operator fun set(index: Int, value: Byte) {
        index.checkRange<Reify>()
        unsafe.putByte(ptr + index, value)
    }

    @PublishedApi
    internal fun speedCopyPrecision(ctx: CopyIntoContext): ByteBuffer {
        TODO("TO BE IMPLEMENTED")
    }

    @PublishedApi
    internal fun calculateIntoContext(dest: ByteBuffer, destOff: Int, idxFrom: Int, idxTo: Int): CopyIntoContext {
        if(dest.idxSize != this.idxSize) throw IllegalArgumentException("Not of same TypeSize.")
        if(destOff !in 0..size || idxTo !in 0..size) throw IllegalArgumentException("Illegal range.")
        if((idxTo - idxFrom) + destOff !in 0..size) throw IllegalArgumentException("Illegal range.")
        if(idxFrom > idxTo) throw IllegalStateException("Wrong sizes")

        val factor = TypeSize.long / idxSize.size

        val srcIdxOff = (idxOff + idxFrom) % factor
        val dstIdxOff = (dest.idxOff + destOff) % factor
        dest.idxOff

        val newIdxOff = (idxOff + idxFrom) % factor
        val newSize = idxTo - idxFrom
        val newIdxEnd = newIdxOff + newSize
        val baseIdx = (idxOff + idxFrom) - newIdxOff
        val leftIdxMargin = 0
        val rightIdxMargin = 0
        return CopyIntoContext(factor, newIdxOff, newSize, newIdxEnd, baseIdx)
    }

    internal data class CopyIntoContext(
        val factor: Int,
        val newIdxOff: Int,
        val newSize: Int,
        val newIdxEnd: Int,
        val baseIdx: Int
    ) {
        override fun toString(): String = "$factor, $newIdxOff, $newSize, $newIdxEnd, $baseIdx"
    }

    public actual companion object {
        internal val unsafe: Unsafe = Chunk.unsafe
        public actual val typeSize: TypeSize = TypeSize.BYTE
    }
}

@PublishedApi
internal fun ByteBuffer.innerCopyInto(dest: ByteBuffer, destOff: Int = 0, idxFrom: Int, idxTo: Int) {
    val ctx = calculateIntoContext(dest, destOff, idxFrom, idxTo)
    speedCopyPrecision(ctx)
}

public fun ByteBuffer.copyInto(dest: ByteBuffer, destOff: Int = 0, idxFrom: Int = 0, idxTo: Int = size) {
    innerCopyInto(dest, destOff, idxFrom, idxTo)
}