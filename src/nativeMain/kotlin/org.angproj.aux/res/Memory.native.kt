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
package org.angproj.aux.res

import kotlinx.cinterop.*
import org.angproj.aux.util.Reifiable

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
@OptIn(ExperimentalForeignApi::class)
public actual class Memory(public actual val size: Int, public actual val ptr: Long): Cleanable {

    public override fun dispose() {
        nativeHeap.free(ptr.toCPointer<ByteVar>()!!)
    }
}

@OptIn(ExperimentalForeignApi::class)
public actual fun allocateMemory(size: Int): Memory {
    validateAskedMemorySize(size)
    return Memory(size, nativeHeap.allocArray<ByteVar>(size).toLong())
}
@PublishedApi
@OptIn(ExperimentalForeignApi::class)
internal actual inline fun <reified T: Reifiable> Memory.speedLongGet(index: Long): Long = (ptr + index).toCPointer<LongVar>()!!.pointed.value
@PublishedApi
@OptIn(ExperimentalForeignApi::class)
internal actual inline fun <reified T: Reifiable> Memory.speedLongSet(index: Long, value: Long) { (ptr + index).toCPointer<LongVar>()!!.pointed.value = value }
@PublishedApi
@OptIn(ExperimentalForeignApi::class)
internal actual inline fun <reified T: Reifiable> Memory.speedByteGet(index: Long): Byte = (ptr + index).toCPointer<ByteVar>()!!.pointed.value
@PublishedApi
@OptIn(ExperimentalForeignApi::class)
internal actual inline fun <reified T: Reifiable> Memory.speedByteSet(index: Long, value: Byte) { (ptr + index).toCPointer<ByteVar>()!!.pointed.value = value }