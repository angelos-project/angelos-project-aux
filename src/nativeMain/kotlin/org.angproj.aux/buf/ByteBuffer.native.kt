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
import org.angproj.aux.res.Cleanable
import org.angproj.aux.res.allocateMemory
import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.ref.Cleaner
import kotlin.native.ref.createCleaner

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
@OptIn(ExperimentalForeignApi::class)
public actual class ByteBuffer(size: Int): AbstractBufferType<Byte>(size) {
    private val data = allocateMemory(realSizeCalc(size))
    private val ptr = data.ptr

    @OptIn(ExperimentalNativeApi::class)
    private val cleaner: Cleaner = createCleaner(data) { data.dispose() }
    override fun close() { data.dispose() }

    public actual override operator fun get(index: Int): Byte {
        if(index !in 0..<(size)) throw IllegalArgumentException("Out of bounds.")
        return (ptr + index)!!.reinterpret<ByteVar>().pointed.value
    }

    public actual override operator fun set(index: Int, value: Byte) {
        if(index !in 0..<(size)) throw IllegalArgumentException("Out of bounds.")
        (ptr + index)!!.reinterpret<ByteVar>().pointed.value = value
    }
}