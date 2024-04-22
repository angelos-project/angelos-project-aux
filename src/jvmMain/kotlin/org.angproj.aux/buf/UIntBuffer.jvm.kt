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

import org.angproj.aux.res.Memory as Chunk
import org.angproj.aux.io.ByteString
import org.angproj.aux.res.Manager
import org.angproj.aux.res.allocateMemory
import sun.misc.Unsafe
import java.lang.ref.Cleaner.Cleanable

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public actual class UIntBuffer actual constructor(size: Int) : AbstractBufferType<UInt>(size) {
    private val data: Chunk = allocateMemory(realSizeCalc(size) / ByteString.intSize)
    private val ptr: Long = data.ptr

    private val cleanable: Cleanable = Manager.cleaner.register(this) { data.dispose() }
    override fun close() { cleanable.clean() }

    actual override fun get(index: Int): UInt {
        if(index !in 0..<size-3) throw IllegalArgumentException("Out of bounds.")
        return unsafe.getInt(ptr + index).toUInt()
    }

    actual override fun set(index: Int, value: UInt) {
        if(index !in 0..<size-3) throw IllegalArgumentException("Out of bounds.")
        unsafe.putInt(ptr + index, value.toInt())
    }

    internal companion object {
        internal val unsafe: Unsafe = Chunk.unsafe
    }
}