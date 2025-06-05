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
import org.angproj.aux.io.Memory

public object MemoryFree: FreeAlloc<Memory> {
    override fun allocate(size: Int): Memory = Memory(size, this)

    override fun allocate(dataSize: DataSize): Memory = allocate(dataSize.size)

    override fun recycle(segment: Memory) { segment.data.dispose() }
}