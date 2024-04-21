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

import org.angproj.aux.Chunk
import org.angproj.aux.Native

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public actual open class Memory actual constructor(size: Int) : Segment {

    actual final override val size: Int = size
    protected actual val data: Chunk = Native.allocateChunk(size)

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

}