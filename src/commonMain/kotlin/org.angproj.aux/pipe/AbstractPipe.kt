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
import org.angproj.aux.util.NullObject

public abstract class AbstractPipe<T: PipeType>(
    protected val src: AbstractSource<out PipeType>,
    protected val sink: AbstractSink<out PipeType>,
    public val bufferSize: DataSize,
): Pipe, PipeMode {

    public val segmentSize: DataSize = DataSize._1K

    protected abstract val buf: MutableList<Segment>
    protected val usedSize: Int
        get() = buf.sumOf { it.size }

    public val isExhausted: Boolean
        get() = buf.isEmpty()

    public val isCrammed: Boolean
        get() = usedSize >= bufferSize.size

    init {
        src.connect(this)
        sink.connect(this)
    }

    /**
     * Draining should be done by a Source in a PushPipe, it forces the sink to use up the buffer,
     * or 2, forces the sink to finish and close up tidily.
     * */
    internal open fun drain() { throw UnsupportedOperationException("Can not drain.") }

    /**
     * Tapping should be done by a Sink in a PullPipe, it forces the source to fill up the buffer,
     * or 2, forces the source to complete and finish.
     * */
    internal open fun tap() { throw UnsupportedOperationException("Can not tap.") }

    internal fun pop(): Segment = buf.pop()
    internal fun push(seg: Segment) = buf.push(seg)

    override fun close() {
        println("Close: ${this::class.qualifiedName}")
        if(!src.isClosed)
            src.close()
        if(!sink.isClosed)
            sink.close()
    }

    /**
     * Adds FIFO push abilities to the List of Segment.
     * */
    public fun MutableList<Segment>.push(seg: Segment) {
        this.add(0, seg)
    }

    /**
     * Adds FIFO pop abilities to the List of Segment
     * */
    public fun MutableList<Segment>.pop(): Segment = when {
        isEmpty() -> NullObject.segment
        last().limit == 0 -> {
            removeLast().close()
            NullObject.segment
        }
        else -> removeLast()
    }
}