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
import org.angproj.aux.mem.MemoryManager
import org.angproj.aux.util.NullObject


public class PullPipe<T: PipeType>(
    memMgr: MemoryManager<*>,
    public val src: PumpSource<T>,
    segSize: DataSize = DataSize._4K,
    bufSize: DataSize = DataSize._32K
): AbstractPipe<T>(segSize, bufSize, memMgr), Close {

    init {
        require(src.isOpen()) { "A pipe must have a non-closed source" }
    }

    /**
     * Adds FIFO push abilities to the List of Segment.
     * */
    private inline fun<reified E: Any> ArrayDeque<Segment<*>>.push(seg: Segment<*>): Unit = when(seg.limit > 0) {
        true -> addFirst(seg)
        else -> recycle<Unit>(seg).also { addFirst(NullObject.segment) }
    }

    /**
     * Adds FIFO pop abilities to the List of Segment
     * */
    private inline fun<reified E: Any> ArrayDeque<Segment<*>>.pop(): Segment<*> = removeLastOrNull() ?: NullObject.segment

    public fun<reified : Any> isExhausted(): Boolean = buf.isEmpty()

    /**
     * Will tap up data as long there is leftover and will quit when the segment is not fully filled.
     * */
    public fun<reified : Any> tap() {
        var capacity = queueCap - buf.size
        if(capacity > 0) do {
            val seg = allocate<Unit>(segSize)
            seg.clear()
            val length = src.squeeze<Unit>(seg)
            seg.limitAt(length)
            buf.push<Unit>(seg)
            _segCnt++
        } while(length > 0 && --capacity > 0)
        _bufUse = -1
    }

    public fun<reified : Any> pop(): Segment<*> = buf.pop<Unit>()

    override fun isOpen(): Boolean = src.isOpen() or buf.isNotEmpty()
    override fun close() {
        if(isOpen()) {
            src.close()
            dispose<Unit>()
        }
    }
}

public fun PullPipe<TextType>.getSink(): TextSink = TextSink(this)

public fun PullPipe<BinaryType>.getSink(): BinarySink = BinarySink(this)