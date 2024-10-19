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
import org.angproj.aux.buf.AbstractSpeedCopy
import org.angproj.aux.util.Reify
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
    size: Int, idxLimit: Int
) : AbstractMemory(size, idxLimit) {

    public actual constructor(size: Int) : this(size, size)

    private var _closed = false

    override val isOpen: Boolean
        get() = !_closed

    actual final override val data: Chunk = allocateMemory(length)
    protected val ptr: Long = data.ptr

    private val cleaner: Cleaner = createCleaner(data) { data.dispose() }
    override fun close() {
        if(isOpen) {
            data.dispose()
            _closed = true
        }
    }

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

    actual override fun create(size: Int, idxLimit: Int): Memory = Memory(size, idxLimit)

    override fun <T: AbstractSpeedCopy> calculateInto(dest: T, destOff: Int, idxFrom: Int, idxTo: Int) {
        innerCopy(dest as Memory, destOff, idxFrom, idxTo)
    }
}