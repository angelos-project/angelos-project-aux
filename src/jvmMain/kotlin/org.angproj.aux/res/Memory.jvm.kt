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

import org.angproj.aux.util.Reifiable
import org.angproj.aux.util.Speed
import sun.misc.Unsafe
import java.lang.reflect.Field

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public actual class Memory(public actual val size: Int, public actual val ptr: Long): Speed, Cleanable {

    public override fun dispose() {
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

    override val limit: Int = size

    private inline fun <reified T: Number> speedLongGet(index: Int): Long = unsafe.getLong(ptr + index)
    private inline fun <reified T: Number> speedLongSet(index: Int, value: Long): Unit = unsafe.putLong(ptr + index, value)
    private inline fun <reified T: Number> speedByteGet(index: Int): Byte = unsafe.getByte(ptr + index)
    private inline fun <reified T: Number> speedByteSet(index: Int, value: Byte): Unit = unsafe.putByte(ptr + index, value)

    override fun getLong(pos: Int): Long = speedLongGet<Int>(pos)

    override fun getByte(pos: Int): Byte = speedByteGet<Int>(pos)

    override fun setLong(pos: Int, value: Long): Unit = speedLongSet<Int>(pos, value)

    override fun setByte(pos: Int, value: Byte): Unit = speedByteSet<Int>(pos, value)
}

public actual fun allocateMemory(size: Int): Memory {
    validateAskedMemorySize(size)
    return Memory(size, Memory.unsafe.allocateMemory(size.toLong()))
}

@PublishedApi
internal actual inline fun <reified T: Reifiable> Memory.speedLongGet(index: Long): Long = Memory.unsafe.getLong(ptr + index)
@PublishedApi
internal actual inline fun <reified T: Reifiable> Memory.speedLongSet(index: Long, value: Long): Unit = Memory.unsafe.putLong(ptr + index, value)
@PublishedApi
internal actual inline fun <reified T: Reifiable> Memory.speedByteGet(index: Long): Byte = Memory.unsafe.getByte(ptr + index)
@PublishedApi
internal actual inline fun <reified T: Reifiable> Memory.speedByteSet(index: Long, value: Byte): Unit = Memory.unsafe.putByte(ptr + index, value)