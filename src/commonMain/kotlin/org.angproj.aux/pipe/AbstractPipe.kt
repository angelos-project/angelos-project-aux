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
package org.angproj.aux.pipe

import org.angproj.aux.io.DataSize
import org.angproj.aux.io.Segment
import org.angproj.aux.io.segment
import org.angproj.aux.mem.Default
import org.angproj.aux.mem.MemoryManager
import org.angproj.aux.util.NullObject
import org.angproj.aux.util.Reifiable
import org.angproj.aux.util.Reify

public abstract class AbstractPipe<T: PipeType>(
    public val segSize: DataSize = DataSize._1K,
    public val bufSize: DataSize = DataSize._4K,
    private val memMgr: MemoryManager = Default
) {
    internal val buf: MutableList<Segment> = mutableListOf()

    public fun<reified : Reifiable> totSize(): Int = buf.sumOf { it.size }

    public fun<reified : Reifiable> dispose() { while(buf.isNotEmpty()) recycle<Reify>(buf.pop<Reify>()) }

    public fun<reified : Reifiable> allocate(size: DataSize): Segment = memMgr.allocate(size)

    public fun<reified : Reifiable> recycle(seg: Segment) { memMgr.recycle(seg) }

    /**
     * Adds FIFO push abilities to the List of Segment.
     * */
    protected fun<reified : Reifiable> MutableList<Segment>.push(seg: Segment) { this.add(0, seg) }

    /**
     * Adds FIFO pop abilities to the List of Segment
     * */
    protected fun<reified : Reifiable> MutableList<Segment>.pop(): Segment = when {
        isEmpty() -> NullObject.segment
        last().limit == 0 -> {
            recycle<Reify>(removeLast())
            NullObject.segment
        }
        else -> removeLast()
    }
}