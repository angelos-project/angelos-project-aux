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
package org.angproj.aux.io

import kotlinx.cinterop.*
import org.angproj.aux.buf.SpeedCopy
import org.angproj.aux.res.allocateMemory
import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.ref.Cleaner
import kotlin.native.ref.createCleaner
import org.angproj.aux.res.Memory as Chunk

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING",
    "MODALITY_CHANGED_IN_NON_FINAL_EXPECT_CLASSIFIER_ACTUALIZATION_WARNING",
    "ACTUAL_CLASSIFIER_MUST_HAVE_THE_SAME_MEMBERS_AS_NON_FINAL_EXPECT_CLASSIFIER_WARNING"
)
@OptIn(ExperimentalForeignApi::class, ExperimentalNativeApi::class)
public actual open class Memory actual constructor(size: Int) : Segment(size, typeSize) {

    init {
        // Must be BYTE
        require(typeSize == TypeSize.BYTE)
    }

    final override val length: Int = SpeedCopy.addMarginInTotalBytes(size, idxSize)

    protected actual val data: Chunk = allocateMemory(length)
    protected val ptr: CPointer<ByteVarOf<Byte>> = (data.ptr + idxOff)!!

    private val cleaner: Cleaner = createCleaner(data) { data.dispose() }
    override fun close() { data.dispose() }

    actual override fun getByte(index: Int): Byte {
        if(index !in 0..<size) throw IllegalArgumentException("Out of bounds.")
        return (ptr + index)!!.pointed.value
    }

    actual override fun getShort(index: Int): Short {
        if(index !in 0..<(size-1)) throw IllegalArgumentException("Out of bounds.")
        return (ptr + index)!!.reinterpret<ShortVar>().pointed.value
    }

    actual override fun getInt(index: Int): Int {
        if(index !in 0..<(size-3)) throw IllegalArgumentException("Out of bounds.")
        return (ptr + index)!!.reinterpret<IntVar>().pointed.value
    }

    actual override fun getLong(index: Int): Long {
        if(index !in 0..<(size-7)) throw IllegalArgumentException("Out of bounds.")
        return (ptr + index)!!.reinterpret<LongVar>().pointed.value
    }

    override fun speedLongGet(idx: Int): Long {
        TODO("Not yet implemented")
    }

    override fun speedLongSet(idx: Int, value: Long) {
        TODO("Not yet implemented")
    }

    public actual companion object {
        public actual val typeSize: TypeSize = TypeSize.BYTE
    }
}