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
package org.angproj.aux.mem

import org.angproj.aux.io.Bytes
import org.angproj.aux.io.DataSize
import org.angproj.aux.io.isNull
import org.angproj.aux.util.Finalizer

public object DefaultPool: PoolManager<Bytes>(
    DataSize._2K,
    DataSize._256K,
    128
) {
    private val allocatedSegments = mutableSetOf<Bytes>()
    private val freeSegments = mutableSetOf<Bytes>()

    init {
        Finalizer.registerFinalizeHook {
            allocatedSegments.clear()
            freeSegments.clear()
        }
    }

    public override val allocCount: Int
        get() = allocatedSegments.size

    public override val usedCount: Int
        get() = allocatedSegments.size - freeSegments.size

    override fun allocate(dataSize: DataSize): Bytes {
        require(dataSize == segSize) { "Only default size $segSize available." }
        return when {
            freeSegments.size > 0 -> freeSegments.last().also { it._closed = false }
            allocatedSegments.size < maxCount -> Bytes(segSize.size, this).also { allocatedSegments.add(it) }
            else -> throw MemoryException("DefaultPool have reached maxCount of $maxCount, can not allocate another Bytes segment.")
        }
    }

    override fun recycle(segment: Bytes) {
        when {
            segment.isNull() -> Unit
            allocatedSegments.contains(segment) -> freeSegments.add(segment)
            else -> throw MemoryException("DefaultPool doesn't recognize recycled element, wrong memory manager!.")
        }
    }
}