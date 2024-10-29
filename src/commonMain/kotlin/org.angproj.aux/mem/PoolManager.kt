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

import org.angproj.aux.io.DataSize
import org.angproj.aux.io.Segment

public abstract class PoolManager<S: Segment<S>>(
    public val segSize: DataSize = DataSize._1K,
    public val totSize: DataSize = DataSize._4K,
    public val maxCount: Int = 4
): MemoryManager<S> {

    init {
        require(maxCount >= 3)
        require(totSize.size >= segSize.size * 3)
    }

    protected var count: Int = 0


    abstract override fun allocate(dataSize: DataSize): S

    abstract override fun recycle(segment: S)
}