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

import org.angproj.aux.buf.Pump
import org.angproj.aux.io.*
import org.angproj.aux.util.NullObject

public abstract class AbstractSink<T: PipeType>(
    protected val pump: PumpWriter = Pump
): AbstractPipePoint<T>(), Sink, PipeType {
    protected var pos: Int = 0
    protected var seg: Segment = NullObject.segment

    internal fun absorb(seg: Segment, size: Int = seg.size): Int = pump.write(seg, size)

    /**
     * Loads a segment from the outside, should only be used by PullPipe class.
     * */
    internal fun loadSegment(): Unit = pullSegment()

    protected fun pullSegment() {
        if(pipe.isExhausted) pipe.tap()
        seg.close()
        seg = pipe.pop()
        pos = 0
    }
}