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

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
@OptIn(ExperimentalForeignApi::class)
public actual class Memory(public actual val size: Int, public val ptr: CPointer<ByteVarOf<Byte>>): Cleanable {

    /*
    * How to get:
    * (ptr + index)!!.reinterpret<ShortVar>().pointed.value
    *
    * How to set:
    * (ptr + index)!!.reinterpret<ShortVar>().pointed.value = value
    * */

    public override fun dispose() {
        nativeHeap.free(ptr)
    }
}

@OptIn(ExperimentalForeignApi::class)
public actual fun allocateMemory(size: Int): Memory = Memory(size, nativeHeap.allocArray<ByteVar>(size))