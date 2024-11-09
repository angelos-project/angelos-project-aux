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
import org.angproj.aux.io.isNull
import org.angproj.aux.io.segment
import org.angproj.aux.util.Measurable
import org.angproj.aux.util.NullObject
import kotlin.time.TimeSource


public abstract class AbstractSink<T: PipeType>(
    protected val pipe: PullPipe<T>
) : Sink, Measurable<PipeStats>, PipeType {

    /**
     * The default [DataSize] of each segment used inside the pipe.
     * */
    public val segSize: DataSize = pipe.segSize

    /**
     * The max [DataSize] that can be used in total in the buffer at all times.
     * */
    public val bufSize: DataSize = pipe.bufSize

    /**
     * The only purpose is to disable segment pulling and thereby reading
     * after manually closing the sink.
     * */
    protected var _closed: Boolean = false

    private var _count: Long = 0
    public override val count: Long
        get() = _count + pos

    protected var pos: Int = 0
    protected var seg: Segment<*> = NullObject.segment

    /**
     * Rearrange the calculations on some!!!
     * */
    public override fun telemetry(): PipeStats {
        val src = pipe.src
        return PipeStats(
            dateTimeMark = TimeSource.Monotonic.markNow(),
            pumpOpen = src.isOpen(),
            pipeOpen = !_closed,
            staleCnt = src.staleCnt,
            pumpCnt = src.count,
            pipeCnt = count,
            usedMem = pipe.bufUse + pos,
            allocMem = pipe.queueLen * pipe.segSize.size + seg.size,
            curSegCnt = pipe.queueLen,
            totSegCnt = pipe.totalSegmentCount
        )
    }

    protected fun<reified : Any> pullSegment() {
        if(_closed) throw PipeException("Closed")
        pipe.recycle<Unit>(seg)
        _count += pos
        pos = 0
        if(pipe.isExhausted<Unit>()) pipe.tap<Unit>()
        seg = pipe.pop<Unit>()
        if(seg.isNull()) throw StaleException("Source is stale")
    }

    override fun isOpen(): Boolean = pipe.isOpen() or !seg.isNull()

    override fun close() {
        if(isOpen()) {
            _closed = true
            pipe.recycle<Unit>(seg)
            pipe.close()
            seg = NullObject.segment
            _count += pos
            pos = 0
        }
    }
}