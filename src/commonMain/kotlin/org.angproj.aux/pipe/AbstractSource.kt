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

public abstract class AbstractSource<T: PipeType>(
    protected val pump: PumpReader = Pump
): AbstractPipePoint<T>(), Source, PipeType {
    protected var pos: Int = 0
    protected var seg: Segment = NullObject.segment

    internal fun squeeze(seg: Segment): Int = pump.read(seg)

    /**
     * Stuffs a segment to the outside, should only be used by PushPipe class.
     * */
    internal fun stuffSegment(): Unit = pushSegment()

    protected fun pushSegment() {
        if(pipe.isCrammed) pipe.drain()
        if(!seg.isNull()) {
            seg.limit = pos
            pipe.push(seg)
        }
        seg = Bytes(pipe.segmentSize.size)
        pos = 0
    }
}