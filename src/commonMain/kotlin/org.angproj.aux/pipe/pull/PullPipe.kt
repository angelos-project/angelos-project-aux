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
package org.angproj.aux.pipe.pull

import org.angproj.aux.io.Bytes
import org.angproj.aux.io.DataSize
import org.angproj.aux.io.Segment
import org.angproj.aux.io.segment

import org.angproj.aux.util.NullObject
import org.angproj.aux.util.Reifiable
import org.angproj.aux.util.Reify
import kotlin.math.min

public class PullPipe<T: PipeType>(
    internal val src: AbstractSource<T>,
    public val segSize: DataSize = DataSize._1K,
    public val bufSize: DataSize = DataSize._4K
) {

    internal val buf: MutableList<Segment> = mutableListOf()

    public fun<reified : Reifiable> totSize(): Int = buf.sumOf { it.size }

    public fun<reified : Reifiable> isExhausted(): Boolean = buf.isEmpty()

    /**
     * Will tap up data as long there is leftover and will quit when the segment is not fully filled.
     * */
    public fun<reified : Reifiable> tap() {
        check(src.isOpen()) { "Buffer underflow!." }
        var leftover = bufSize.size - totSize<Reify>()

        if(leftover > 0) do {
            val seg = allocate<Reify>(min(segSize.size, leftover))
            src.squeeze<Reify>(seg)
            leftover -= seg.limit
            buf.push<Reify>(seg)
        } while(seg.limit == seg.size && leftover > 0)
    }

    public fun<reified : Reifiable> allocate(size: Int): Segment = Bytes(size)

    public fun<reified : Reifiable> recycle(seg: Segment) { seg.close() }

    public fun<reified : Reifiable> pop(): Segment = buf.pop<Reify>()

    /**
     * Adds FIFO push abilities to the List of Segment.
     * */
    private fun<reified : Reifiable> MutableList<Segment>.push(seg: Segment) { this.add(0, seg) }

    /**
     * Adds FIFO pop abilities to the List of Segment
     * */
    private fun<reified : Reifiable> MutableList<Segment>.pop(): Segment = when {
        isEmpty() -> NullObject.segment
        last().limit == 0 -> {
            removeLast().close()
            NullObject.segment
        }
        else -> removeLast()
    }
}

public fun PullPipe<TextType>.getSink(): TextSink = TextSink(this)

public fun PullPipe<BinaryType>.getSink(): BinarySink = BinarySink(this)