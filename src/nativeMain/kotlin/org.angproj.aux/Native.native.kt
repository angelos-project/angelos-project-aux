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

import kotlinx.cinterop.*
import platform.posix.free

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
@OptIn(ExperimentalForeignApi::class)
internal actual object Native {

    internal actual fun allocateChunk(size: Int): Chunk {
        val unsafe: CArrayPointer<ByteVar> = memScoped { nativeHeap.allocArray<ByteVar>(size) }
        return Chunk(Blob(unsafe.pointed.ptr.toLong(), size, unsafe))
    }

    internal actual fun freeChunk(chunk: Chunk) {
        memScoped { free(chunk.mem.ptr.toCPointer<ByteVar>()) }
    }
}