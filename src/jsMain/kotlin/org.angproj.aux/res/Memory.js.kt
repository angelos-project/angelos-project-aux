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
import org.angproj.aux.util.Reifiable

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public actual class Memory(public actual val size: Int, public actual val ptr: Long): Copyable, Cleanable {

    public override fun dispose() {}

    override val limit: Int = size

    override fun getLong(index: Int): Long = throw UnsupportedOperationException("No access to native memory")

    override fun getByte(index: Int): Byte = throw UnsupportedOperationException("No access to native memory")

    override fun setLong(index: Int, value: Long): Unit = throw UnsupportedOperationException("No access to native memory")

    override fun setByte(index: Int, value: Byte): Unit = throw UnsupportedOperationException("No access to native memory")
}

public actual fun allocateMemory(size: Int): Memory {
    validateAskedMemorySize(size)
    throw UnsupportedOperationException("No access to native memory")
}

@PublishedApi
internal actual fun speedMemCpy(idxFrom: Int, idxTo: Int, dstOff: Int, src: Long, dst: Long): Int {
    throw UnsupportedOperationException("No access to native memory")
}