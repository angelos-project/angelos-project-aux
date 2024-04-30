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
import org.angproj.aux.buf.Reify

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
@OptIn(ExperimentalForeignApi::class)
public actual class MutableMemory actual constructor(
    size: Int, idxOff: Int, idxEnd: Int
) : Memory(size, idxOff, idxEnd), MutableSegment {

    public actual constructor(size: Int) : this(size, 0, size)

    actual override fun setByte(index: Int, value: Byte) {
        index.checkRangeByte<Reify>()
        (ptr + index).toCPointer<ByteVar>()!!.pointed.value = value
    }

    actual override fun setShort(index: Int, value: Short) {
        index.checkRangeShort<Reify>()
        (ptr + index).toCPointer<ShortVar>()!!.pointed.value = value
    }

    actual override fun setInt(index: Int, value: Int) {
        index.checkRangeInt<Reify>()
        (ptr + index).toCPointer<IntVar>()!!.pointed.value = value
    }

    actual override fun setLong(index: Int, value: Long) {
        index.checkRangeLong<Reify>()
        (ptr + index).toCPointer<LongVar>()!!.pointed.value = value
    }

    actual override fun create(size: Int, idxOff: Int, idxEnd: Int): MutableMemory = MutableMemory(size, idxOff, idxEnd)

    public actual override fun copyOfRange(idxFrom: Int, idxTo: Int): MutableMemory = innerCopyOfRange(idxFrom, idxTo
    ) { basePtr, copyPtr, offset ->
        (copyPtr + offset).toCPointer<LongVar>()!!.pointed.value = (
                basePtr + offset).toCPointer<LongVar>()!!.pointed.value
    } as MutableMemory

    actual override fun copyOf(): MutableMemory {
        TODO("Not yet implemented")
    }
}