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

import org.angproj.aux.buf.ByteBuffer.Companion.unsafe
import org.angproj.aux.io.TypeSize
import org.angproj.aux.res.Manager
import org.angproj.aux.res.Memory
import org.angproj.aux.res.allocateMemory
import java.lang.ref.Cleaner.Cleanable

@Suppress(
    "ACTUAL_CLASSIFIER_MUST_HAVE_THE_SAME_MEMBERS_AS_NON_FINAL_EXPECT_CLASSIFIER_WARNING",
    "MODALITY_CHANGED_IN_NON_FINAL_EXPECT_CLASSIFIER_ACTUALIZATION_WARNING",
    "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"
)
public actual abstract class AbstractBufferType<E> actual constructor(
    size: Int, idxSize: TypeSize
) : AbstractSpeedCopy(size, idxSize), BufferType<E> {

    private val data: Memory = allocateMemory(SpeedCopy.addMarginInTotalBytes(size, idxSize))
    protected val ptr: Long = data.ptr + idxOff * idxSize.size

    private val cleanable: Cleanable = Manager.cleaner.register(this) { data.dispose() }
    override fun close() {
        cleanable.clean()
    }

    override fun speedLongGet(idx: Int): Long = unsafe.getLong(ptr + idx * TypeSize.LONG.size)
    override fun speedLongSet(idx: Int, value: Long) {
        unsafe.putLong(ptr + idx * TypeSize.LONG.size, value)
    }
}