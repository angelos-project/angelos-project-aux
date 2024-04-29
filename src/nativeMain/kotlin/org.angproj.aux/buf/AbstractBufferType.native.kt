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
import org.angproj.aux.res.Memory
import org.angproj.aux.res.allocateMemory
import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.ref.Cleaner
import kotlin.native.ref.createCleaner

@Suppress(
    "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING",
    "MODALITY_CHANGED_IN_NON_FINAL_EXPECT_CLASSIFIER_ACTUALIZATION_WARNING",
    "ACTUAL_CLASSIFIER_MUST_HAVE_THE_SAME_MEMBERS_AS_NON_FINAL_EXPECT_CLASSIFIER_WARNING",
)
@OptIn(ExperimentalForeignApi::class, ExperimentalNativeApi::class)
public actual abstract class AbstractBufferType<E> actual constructor(
    size: Int, idxSize: TypeSize, idxOff: Int, idxEnd: Int
) : AbstractSpeedCopy(size, idxSize, idxOff, idxEnd), BufferType<E> {

    final override val length: Int = SpeedCopy.addMarginInTotalBytes(idxEnd, idxSize)
    final override val marginSized: Int = SpeedCopy.addMarginByIndexType(idxEnd, idxSize)

    private val data: Memory = allocateMemory(length)
    protected val ptr: CPointer<ByteVarOf<Byte>> = (data.ptr + idxOff * idxSize.size)!!

    init {
        require(data.size == length)
        require(marginSized * idxSize.size == length)
    }

    private val clean: Cleaner = createCleaner(data) { data.dispose() }
    override fun close() {
        data.dispose()
    }

    protected fun copyOfRange2(idxFrom: Int, idxTo: Int): AbstractBufferType<E> {
        val factor = TypeSize.long / idxSize.size
        val newIdxOff = idxFrom % factor
        val newSize = idxTo - idxFrom
        val newIdxEnd = newIdxOff + newSize
        val baseIdx = (idxOff + idxFrom) - newIdxOff

        val copy = create(newSize, newIdxOff, newIdxEnd)

        val basePtr = data.ptr + (baseIdx * idxSize.size)
        val copyPtr = copy.data.ptr

        (0 until copy.length step TypeSize.long).forEach {
            (copyPtr + it)!!.reinterpret<LongVar>().pointed.value = (
                    basePtr + it)!!.reinterpret<LongVar>().pointed.value
        }
        return copy
    }

    actual abstract override fun create(
        size: Int,
        idxOff: Int,
        idxEnd: Int
    ): AbstractBufferType<E>

    actual abstract override fun copyOf(): AbstractBufferType<E>
    actual abstract override fun copyOfRange(idxFrom: Int, idxTo: Int): AbstractBufferType<E>
}