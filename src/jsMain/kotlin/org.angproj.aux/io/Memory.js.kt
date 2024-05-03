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
package org.angproj.aux.io

import org.angproj.aux.buf.AbstractSpeedCopy
import org.angproj.aux.res.allocateMemory
import org.angproj.aux.res.Memory as Chunk

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public actual open class Memory actual constructor(size: Int) : Segment(size, typeSize) {

    protected actual val data: Chunk = allocateMemory(size)

    actual override fun getByte(index: Int): Byte {
        throw UnsupportedOperationException("No access to native memory.")
    }

    actual override fun getShort(index: Int): Short {
        throw UnsupportedOperationException("No access to native memory.")
    }

    actual override fun getInt(index: Int): Int {
        throw UnsupportedOperationException("No access to native memory.")
    }

    actual override fun getLong(index: Int): Long {
        throw UnsupportedOperationException("No access to native memory.")
    }

    actual override fun setByte(index: Int, value: Byte) {
        throw UnsupportedOperationException("No access to native memory.")
    }

    actual override fun setShort(index: Int, value: Short) {
        throw UnsupportedOperationException("No access to native memory.")
    }

    actual override fun setInt(index: Int, value: Int) {
        throw UnsupportedOperationException("No access to native memory.")
    }

    actual override fun setLong(index: Int, value: Long) {
        throw UnsupportedOperationException("No access to native memory.")
    }

    override fun close() {}

    public actual companion object {
        public actual val typeSize: TypeSize = TypeSize.BYTE
    }

    override val marginSized: Int
        get() = TODO("Not yet implemented")

    override fun create(size: Int): AbstractSpeedCopy {
        TODO("Not yet implemented")
    }

    override val length: Int
        get() = TODO("Not yet implemented")
}

internal actual inline fun <reified T : Memory> T.longCopy(
    basePtr: Long,
    copyPtr: Long,
    offset: Int
) {
    throw UnsupportedOperationException()
}