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
import org.angproj.aux.util.Reify
import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.ref.Cleaner
import kotlin.native.ref.createCleaner

@Suppress(
    "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING",
    "MODALITY_CHANGED_IN_NON_FINAL_EXPECT_CLASSIFIER_ACTUALIZATION_WARNING",
    "ACTUAL_CLASSIFIER_MUST_HAVE_THE_SAME_MEMBERS_AS_NON_FINAL_EXPECT_CLASSIFIER_WARNING",
)
@OptIn(ExperimentalNativeApi::class)
public actual abstract class AbstractBufferType<E> actual constructor(
    size: Int, idxSize: TypeSize, idxLimit: Int
) : AbstractSpeedCopy(size, idxSize, idxLimit), BufferType<E> {

    final override val length: Int = SpeedCopy.addMarginInTotalBytes(idxLimit, idxSize)
    final override val marginSized: Int = SpeedCopy.addMarginByIndexType(idxLimit, idxSize)

    @PublishedApi
    internal val data: Memory = allocateMemory(length)
    protected val ptr: Long = data.ptr

    init {
        require(data.size == length)
        require(marginSized * idxSize.size == length)
    }

    private val clean: Cleaner = createCleaner(data) { data.dispose() }
    override fun close() {
        data.dispose()
    }

    override fun getPointer(): Long = data.ptr

    actual abstract override fun create(size: Int, idxLimit: Int): AbstractBufferType<E>
}

@PublishedApi
internal inline fun<reified E, T: AbstractBufferType<E>> T.innerCopy(dest: T, destOff: Int, idxFrom: Int, idxTo: Int) {
    data.copyInto<Reify>(dest.data, destOff, idxFrom, idxTo)
}