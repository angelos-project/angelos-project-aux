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

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public actual class Memory: Cleanable {
    public actual val size: Int
        get() = throw UnsupportedOperationException()
    public actual val ptr: Long
        get() = TODO("Not yet implemented")

    override fun dispose() { throw UnsupportedOperationException() }
}

public actual fun allocateMemory(size: Int): Memory = throw UnsupportedOperationException()
internal actual inline fun <reified T: Reifiable> Memory.speedLongGet(index: Long): Long = throw UnsupportedOperationException()
internal actual inline fun <reified T: Reifiable> Memory.speedLongSet(index: Long, value: Long): Unit = throw UnsupportedOperationException()
internal actual inline fun <reified T: Reifiable> Memory.speedByteGet(index: Long): Byte = throw UnsupportedOperationException()
internal actual inline fun <reified T: Reifiable> Memory.speedByteSet(index: Long, value: Byte): Unit = throw UnsupportedOperationException()