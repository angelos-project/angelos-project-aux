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
import org.angproj.aux.util.AbstractBufferAware
import org.angproj.aux.util.Measurable
import org.angproj.aux.util.NullObject
import kotlin.time.TimeSource


public abstract class AbstractSource<T: PipeType>(
    protected val pipe: PushPipe<T>
): AbstractBufferAware(), Source<T>, Measurable<PipeStats>/*, PipeType*/ {

    /**
     * The default [DataSize] of each segment used inside the pipe.
     * */
    public val segSize: DataSize = pipe.segSize

    /**
     * The max [DataSize] that can be used in total in the buffer at all times.
     * */
    public val bufSize: DataSize = pipe.bufSize

    /**
     * The only purpose is to disable segment pushing and thereby writing
     * after manually closing the source.
     * */
    protected var _closed: Boolean = false

    private var _count: Long = 0
    public override val count: Long
        get() = _count + pos

    protected var pos: Int = 0
    protected var seg: Segment<*> = pipe.allocate<Unit>(pipe.segSize)

    public override fun telemetry(): PipeStats {
        val sink = pipe.sink
        return PipeStats(
            dateTimeMark = TimeSource.Monotonic.markNow(),
            pumpOpen = sink.isOpen(),
            pipeOpen = !_closed,
            staleCnt = sink.staleCnt,
            pumpCnt = sink.count,
            pipeCnt = count,
            usedMem = pipe.bufUse + pos,
            allocMem = pipe.queueLen * pipe.segSize.size + seg.size,
            curSegCnt = pipe.queueLen,
            totSegCnt = pipe.totalSegmentCount + pipe.queueLen + if(seg.isNull()) 0 else 1
        )
    }

    /**
     * A segment is pushed either when it is full or when an incomplete segment and buffer data needs to be flushed.
     * First a check to see whether the pipe is manually closed or not takes place, then the buffer is checked
     * whether it is crammed or not, if so is the case it's drained. Then the current segment is pushed onto the
     * buffer and a new fresh segment is allocated from the memory manager, for security the new segment is cleared.
     * */
    protected fun <reified : Any>pushSegment() {
        if(_closed) throw PipeException("Closed")
        if(pipe.isCrammed<Unit>()) pipe.drain<Unit>()
        pipe.push<Unit>(seg)
        seg = pipe.allocate<Unit>(pipe.segSize)
        seg.clear()
        _count += pos
        pos = 0
    }

    /**
     * Flush allows the programmer to enforce a complete drainage of the pipe to the [PumpSink].
     * First [pushSegment] is called, and then a complete drainage takes place.
     *
     * In a fresh pipe Null segment has not [limitAt] set at all but is allowed to be pushed, thereby relying
     * on [pushSegment] to give a new empty segment to write to.
     *
     * Flushing a fresh empty segment will only trigger [drain] but leave [limitAt] and [pushSement] alone.
     *
     * Only a segment with at least one byte will have [limitAt] set and be given to [pushSegment] so that
     * everything works normally.
     * */
    public fun flush() {
        if(pos > 0) seg.limitAt(pos)
        if(seg.isNull() || pos > 0) pushSegment<Unit>()
        if(pipe.queueLen > 0) pipe.drain<Unit>()
    }

    override fun isOpen(): Boolean = pipe.isOpen() or !seg.isNull()

    /**
     * Close the pipe from future [pushSegment] calls, also recycle the ready segment,
     * at the same time call underlying [PushPipe] to clean up all segments, also set
     * everything to null and calculate final data.
     * */
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