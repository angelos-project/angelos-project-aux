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
import org.angproj.aux.res.allocateMemory
import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.ref.Cleaner
import kotlin.native.ref.createCleaner

@Suppress(
    "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING",
    "ACTUAL_CLASSIFIER_MUST_HAVE_THE_SAME_MEMBERS_AS_NON_FINAL_EXPECT_CLASSIFIER_WARNING",
    "MODALITY_CHANGED_IN_NON_FINAL_EXPECT_CLASSIFIER_ACTUALIZATION_WARNING"
)
@OptIn(ExperimentalForeignApi::class)
public actual abstract class AbstractBufferType<E> actual constructor(
    size: Int, idxSize: TypeSize
) : AbstractSpeedCopy(size, idxSize), BufferType<E> {

    private val data = allocateMemory(SpeedCopy.addMarginInTotalBytes(size, TypeSize.BYTE))
    public val ptr: CPointer<ByteVarOf<Byte>> = data.ptr.plus(idxOff * idxSize.size)!!

    @OptIn(ExperimentalNativeApi::class)
    private val cleaner: Cleaner = createCleaner(data) { it.dispose() }
    override fun close() {
        data.dispose()
    }

    override fun speedLongGet(idx: Int): Long = (ptr + idx * TypeSize.LONG.size)!!.reinterpret<LongVar>().pointed.value
    override fun speedLongSet(idx: Int, value: Long) {
        (ptr + idx * TypeSize.LONG.size)!!.reinterpret<LongVar>().pointed.value = value
    }
}