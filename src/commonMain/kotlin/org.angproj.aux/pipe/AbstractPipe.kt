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

public abstract class AbstractPipe<T: PipeType>(
    protected val src: AbstractSource<out PipeType>,
    protected val sink: AbstractSink<out PipeType>,
    public val bufferSize: DataSize,
): Pipe, PipeMode {

    protected abstract val buf: MutableList<Segment>
    internal var limit: Int = Int.MAX_VALUE

    public val isExhausted: Boolean
        get() = buf.isEmpty()

    init {
        src.connect(this)
        sink.connect(this)
    }

    /**
     * Draining should be done by a Source in a PushPipe, it forces the sink to use up the buffer,
     * or 2, forces the sink to finish and close up tidily.
     * */
    internal fun drain() { throw UnsupportedOperationException("Can not drain.") }

    /**
     * Tapping should be done by a Sink in a PullPipe, it forces the source to fill up the buffer,
     * or 2, forces the source to complete and finish.
     * */
    internal open fun tap() { throw UnsupportedOperationException("Can not tap.") }

    override fun close() {
        if(!src.isClosed)
            src.close()
        if(!sink.isClosed)
            sink.close()
    }
}