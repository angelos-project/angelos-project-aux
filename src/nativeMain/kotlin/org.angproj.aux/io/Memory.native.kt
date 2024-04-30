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
import org.angproj.aux.res.allocateMemory
import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.ref.Cleaner
import kotlin.native.ref.createCleaner
import org.angproj.aux.res.Memory as Chunk

@Suppress(
    "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"
)
@OptIn(ExperimentalForeignApi::class, ExperimentalNativeApi::class)
public actual open class Memory actual constructor(
    size: Int, idxOff: Int, idxEnd: Int
) : Segment(size, typeSize, idxOff, idxEnd) {

    public actual constructor(size: Int) : this(size, 0, size)

    protected actual val data: Chunk = allocateMemory(length)
    protected val ptr: Long = data.ptr + idxOff

    init {
        // Must be BYTE
        require(typeSize == TypeSize.BYTE)
    }

    private val cleaner: Cleaner = createCleaner(data) { data.dispose() }
    override fun close() { data.dispose() }

    actual override fun getByte(index: Int): Byte {
        index.checkRangeByte<Reify>()
        return (ptr + index).toCPointer<ByteVar>()!!.pointed.value
    }

    actual override fun getShort(index: Int): Short {
        index.checkRangeShort<Reify>()
        return (ptr + index).toCPointer<ShortVar>()!!.pointed.value
    }

    actual override fun getInt(index: Int): Int {
        index.checkRangeInt<Reify>()
        return (ptr + index).toCPointer<IntVar>()!!.pointed.value
    }

    actual override fun getLong(index: Int): Long {
        index.checkRangeLong<Reify>()
        return (ptr + index).toCPointer<LongVar>()!!.pointed.value
    }

    actual override fun create(size: Int, idxOff: Int, idxEnd: Int): Memory = Memory(size, idxOff, idxEnd)

    public actual override fun copyOfRange(idxFrom: Int, idxTo: Int): Memory = innerCopyOfRange(idxFrom, idxTo
    ) { basePtr, copyPtr, offset ->
        (copyPtr + offset).toCPointer<LongVar>()!!.pointed.value = (
                basePtr + offset).toCPointer<LongVar>()!!.pointed.value
    } as Memory

    override fun getPointer(): Long = data.ptr.toLong()

    actual override fun copyOf(): Memory {
        TODO("Not yet implemented")
    }

    public actual companion object {
        public actual val typeSize: TypeSize = TypeSize.BYTE
    }
}