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
import org.angproj.aux.res.*
import org.angproj.aux.res.Manager
import org.angproj.aux.res.speedLongSet
import org.angproj.aux.util.Reify
import sun.misc.Unsafe
import java.lang.ref.Cleaner.Cleanable

@Suppress(
    "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING",
    "ACTUAL_CLASSIFIER_MUST_HAVE_THE_SAME_MEMBERS_AS_NON_FINAL_EXPECT_CLASSIFIER_WARNING",
    "MODALITY_CHANGED_IN_NON_FINAL_EXPECT_CLASSIFIER_ACTUALIZATION_WARNING",
)
public actual abstract class AbstractBufferType<E> actual constructor(
    size: Int, idxSize: TypeSize, idxOff: Int, idxEnd: Int
) : AbstractSpeedCopy(size, idxSize, idxOff, idxEnd), BufferType<E> {

    final override val length: Int = SpeedCopy.addMarginInTotalBytes(idxEnd, idxSize)
    final override val marginSized: Int = SpeedCopy.addMarginByIndexType(idxEnd, idxSize)

    private val data: Memory = allocateMemory(length)
    protected val ptr: Long = data.ptr + idxOff * idxSize.size

    init {
        require(data.size == length)
        require(marginSized * idxSize.size == length)
    }

    private val clean: Cleanable = Manager.cleaner.register(data) { data.dispose() }
    override fun close() {
        clean.clean()
    }

    override fun getPointer(): Long = data.ptr

    actual abstract override fun create(
        size: Int,
        idxOff: Int,
        idxEnd: Int
    ): AbstractBufferType<E>

    override fun speedCopy(ctx: CopyRangeContext): AbstractSpeedCopy {
        val copy = create(ctx.newSize, ctx.newIdxOff, ctx.newIdxEnd)
        val baseOffset = (ctx.baseIdx * idxSize.size)

        (0 until copy.length step TypeSize.long).forEach {
            data.speedLongSet<Reify>(it.toLong(),
                copy.data.speedLongGet<Reify>(baseOffset + it.toLong()))
        }

        return copy
    }

    public companion object {
        internal val unsafe: Unsafe = Memory.unsafe
    }
}