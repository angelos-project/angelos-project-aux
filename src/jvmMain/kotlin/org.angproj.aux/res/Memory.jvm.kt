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

import org.angproj.aux.buf.Reifiable
import sun.misc.Unsafe
import java.lang.reflect.Field

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public actual class Memory(public actual val size: Int, public actual val ptr: Long): Cleanable {

    public override fun dispose() {
        unsafe.freeMemory(ptr)
    }

    internal companion object {
        internal val unsafe: Unsafe

        init {
            val f: Field = Unsafe::class.java.getDeclaredField("theUnsafe")
            f.isAccessible = true
            unsafe = f.get(null) as Unsafe
        }
    }
}

public actual fun allocateMemory(size: Int): Memory = Memory(size, Memory.unsafe.allocateMemory(size.toLong()))
internal actual inline fun <reified T: Reifiable> Memory.speedLongGet(index: Int): Long = Memory.unsafe.getLong(ptr + index)
internal actual inline fun <reified T: Reifiable> Memory.speedLongSet(index: Int, value: Long): Unit = Memory.unsafe.putLong(ptr + index, value)