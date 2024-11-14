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

import org.angproj.aux.buf.copyInto
import org.angproj.aux.io.DataSize
import org.angproj.aux.io.Segment
import org.angproj.aux.mem.BufMgr
import org.angproj.aux.mem.MemoryManager

public class PushPipe<T: PipeType>(
    memMgr: MemoryManager<*>,
    public val sink: PumpSink<T>,
    segSize: DataSize = DataSize._4K,
    bufSize: DataSize = DataSize._32K
): AbstractPipe<T>(segSize, bufSize, memMgr), Close {

    init {
        require(sink.isOpen()) { "A pipe must have an open sink" }
    }

    /**
     * Adds FIFO push abilities to the List of Segment.
     * */
    protected fun<reified : Any> ArrayDeque<Segment<*>>.push(seg: Segment<*>) { addFirst(seg) }

    /**
     * Adds FIFO pop abilities to the List of Segment
     * */
    protected fun<reified : Any> ArrayDeque<Segment<*>>.pop(): Segment<*> = last() //

    public fun<reified : Any> isCrammed(): Boolean = queueCap <= queueLen

    /**
     * Will drain all data.
     * Even if the queue is empty it is allowed to run one Null segment
     * to deal with empty [flush]es triggered by the programmer unnecessarily
     * */
    public fun<reified : Any> drain() {
        do {
            val seg = buf.pop<Unit>()
            val length = sink.absorb<Unit>(seg)
            when {
                length == seg.limit -> { // Remove fully absorbed segment
                    buf.remove(seg)
                    recycle<Unit>(seg)
                    _segCnt++
                }
                length == 0 -> throw StaleException("Sink is stale") // Nothing was absorbed call stale
                length < seg.limit -> { // Partially absorbed, move unabsorbed data to beginning of segment and set new limit
                    BufMgr.asWrap(seg) {
                        this.copyInto(this, 0, length, seg.limit)
                    }
                    seg.limitAt(seg.limit - length)
                }
            }
        } while(length == seg.limit && buf.isNotEmpty())
        _bufUse = -1
    }

    public fun<reified : Any> push(seg: Segment<*>): Unit = buf.push<Unit>(seg)

    override fun isOpen(): Boolean = sink.isOpen() or buf.isNotEmpty()
    override fun close() {
        if(sink.isOpen()) {
            sink.close()
            dispose<Unit>()
        }
    }
}

public fun PushPipe<TextType>.getSource(): TextSource = TextSource(this)

public fun PushPipe<BinaryType>.getSource(): BinarySource = BinarySource(this)