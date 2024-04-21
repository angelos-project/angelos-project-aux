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
package org.angproj.aux

import sun.misc.Unsafe
import java.lang.reflect.Field

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual object Native {
    internal val unsafe: Unsafe

    init {
        val f: Field = Unsafe::class.java.getDeclaredField("theUnsafe")
        f.isAccessible = true
        unsafe = f.get(null) as Unsafe
    }

    internal actual fun allocateChunk(size: Int): Chunk {
        return Chunk(Blob(unsafe.allocateMemory(size.toLong()), size, unsafe))
    }

    internal actual fun freeChunk(chunk: Chunk) {
        unsafe.freeMemory(chunk.mem.ptr)
    }
}