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
import org.angproj.aux.mem.MemoryManager
import org.angproj.aux.util.Reifiable
import org.angproj.aux.util.Reify
import kotlin.math.min

public class PullPipe<T: PipeType>(
    memMgr: MemoryManager,
    private val src: PumpSource<T>,
    segSize: DataSize = DataSize._1K,
    bufSize: DataSize = DataSize._4K
): AbstractPipe<T>(segSize, bufSize, memMgr) {

    public fun<reified : Reifiable> isExhausted(): Boolean = buf.isEmpty()

    /**
     * Will tap up data as long there is leftover and will quit when the segment is not fully filled.
     * */
    public fun<reified : Reifiable> tap() {
        var leftover = bufSize.size - totSize<Reify>()
        if(leftover > 0) do {
            //val seg = allocate<Reify>(min(segSize.size, leftover))
            val seg = allocate<Reify>(segSize) // Exchanged for above
            seg.limit = min(segSize.size, leftover) // Exchanged for above
            src.squeeze<Reify>(seg)
            leftover -= seg.limit
            buf.push<Reify>(seg)
        } while(seg.limit == seg.size && leftover > 0)
    }

    public fun<reified : Reifiable> pop(): Segment = buf.pop<Reify>()
}

public fun PullPipe<TextType>.getSink(): TextSink = TextSink(this)

public fun PullPipe<BinaryType>.getSink(): BinarySink = BinarySink(this)