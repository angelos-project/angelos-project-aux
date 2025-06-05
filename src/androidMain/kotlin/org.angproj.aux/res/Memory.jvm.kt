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

import org.angproj.aux.io.TypeSize
import org.angproj.aux.util.Copyable
import org.angproj.aux.util.floorMod
import sun.misc.Unsafe
import java.lang.reflect.Field

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public actual class Memory(public actual val size: Int, public actual val ptr: Long): Copyable, Cleanable {

    public actual override fun dispose() {
        unsafe.freeMemory(ptr)
    }

    @PublishedApi
    internal companion object {
        @PublishedApi
        internal val unsafe: Unsafe

        init {
            val f: Field = Unsafe::class.java.getDeclaredField("theUnsafe")
            f.isAccessible = true
            unsafe = f.get(null) as Unsafe
        }
    }

    actual override val limit: Int = size

    private inline fun <reified T: Number> speedLongGet(index: Int): Long = unsafe.getLong(ptr + index)
    private inline fun <reified T: Number> speedLongSet(index: Int, value: Long): Unit = unsafe.putLong(ptr + index, value)
    private inline fun <reified T: Number> speedByteGet(index: Int): Byte = unsafe.getByte(ptr + index)
    private inline fun <reified T: Number> speedByteSet(index: Int, value: Byte): Unit = unsafe.putByte(ptr + index, value)

    actual override fun getLong(index: Int): Long = speedLongGet<Int>(index)

    actual override fun getByte(index: Int): Byte = speedByteGet<Int>(index)

    actual override fun setLong(index: Int, value: Long): Unit = speedLongSet<Int>(index, value)

    actual override fun setByte(index: Int, value: Byte): Unit = speedByteSet<Int>(index, value)
}

public actual fun allocateMemory(size: Int): Memory {
    validateAskedMemorySize(size)
    return Memory(size, Memory.unsafe.allocateMemory(size.toLong()))
}

@PublishedApi
internal actual fun speedMemCpy(
    idxFrom: Int, idxTo: Int, dstOff: Int, src: Long, dst: Long
): Int = speedMemCpyAddress<Unit>(idxFrom, idxTo, dstOff, src, dst, Memory.unsafe)

internal inline fun <reified E: Any>speedMemCpyAddress(idxFrom: Int, idxTo: Int, dstOff: Int, src: Long, dst: Long, unsafe: Unsafe): Int {
    val tot = idxFrom - idxTo
    val long = tot - tot.floorMod(TypeSize.long)

    val dstPtr = dst + dstOff
    val srcPtr = src + idxFrom

    var idx = 0
    while(idx < long) {
        unsafe.putLong(idx + dstPtr, unsafe.getLong(idx + srcPtr))
        idx += TypeSize.long
    }

    while (idx < tot) {
        unsafe.putByte(idx + dstPtr, unsafe.getByte(idx + srcPtr))
        idx++
    }
    return tot
}

internal inline fun <reified E: Any>speedMemCpyAddress2(idxFrom: Int, idxTo: Int, dstOff: Int, src: Long, dst: Long, unsafe: Unsafe): Int {
    val tot = idxFrom - idxTo
    val long = tot - tot.floorMod(TypeSize.long)

    val dstPtr = dst + dstOff
    val srcPtr = src + idxFrom

    unsafe.copyMemory(null, srcPtr, null, dstPtr, long.toLong())
    var idx = long
    while (idx < tot) {
        unsafe.putByte(idx + dstPtr, unsafe.getByte(idx + srcPtr))
        idx++
    }
    return tot
}