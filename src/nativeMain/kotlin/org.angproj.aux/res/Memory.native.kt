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
import platform.posix.*
import org.angproj.aux.io.TypeSize
import org.angproj.aux.util.Copyable
import org.angproj.aux.util.Reifiable

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
@OptIn(ExperimentalForeignApi::class)
public actual class Memory(public actual val size: Int, public actual val ptr: Long): Copyable, Cleanable {

    public override fun dispose() {
        nativeHeap.free(ptr.toCPointer<ByteVar>()!!)
    }

    override val limit: Int = size

    private inline fun <reified T: Number> speedLongGet(index: Int): Long = (ptr + index).toCPointer<LongVar>()!!.pointed.value
    private inline fun <reified T: Number> speedLongSet(index: Int, value: Long) { (ptr + index).toCPointer<LongVar>()!!.pointed.value = value }
    private inline fun <reified T: Number> speedByteGet(index: Int): Byte = (ptr + index).toCPointer<ByteVar>()!!.pointed.value
    private inline fun <reified T: Number> speedByteSet(index: Int, value: Byte) { (ptr + index).toCPointer<ByteVar>()!!.pointed.value = value }

    override fun getLong(index: Int): Long = speedLongGet<Int>(index)

    override fun getByte(index: Int): Byte = speedByteGet<Int>(index)

    override fun setLong(index: Int, value: Long): Unit = speedLongSet<Int>(index, value)

    override fun setByte(index: Int, value: Byte): Unit = speedByteSet<Int>(index, value)
}

@OptIn(ExperimentalForeignApi::class)
public actual fun allocateMemory(size: Int): Memory {
    validateAskedMemorySize(size)
    return Memory(size, nativeHeap.allocArray<ByteVar>(size).toLong())
}

@PublishedApi
@OptIn(ExperimentalForeignApi::class)
internal actual fun speedMemCpy(idxFrom: Int, idxTo: Int, dstOff: Int, src: Long, dst: Long): Int = memScoped {
    val length = idxTo - idxFrom
    memcpy((src + idxFrom).toCPointer<LongVar>(), (dst + dstOff).toCPointer<LongVar>(), length.toULong())
    length
}