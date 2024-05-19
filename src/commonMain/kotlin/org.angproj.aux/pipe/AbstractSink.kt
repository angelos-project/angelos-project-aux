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
import org.angproj.aux.io.PumpWriter
import org.angproj.aux.io.Segment
import org.angproj.aux.io.segment
import org.angproj.aux.util.NullObject

public abstract class AbstractSink<T: PipeType>(
    protected val pump: PumpWriter = Pump
): AbstractPipePoint<T>(), Sink, PipeType {
    protected var _out: Long = 0
    protected var curLim: Int = 0
    protected var pos: Int = 0
    protected var seg: Segment = NullObject.segment

    internal fun absorb(seg: Segment, size: Int = seg.length): Int {
        TODO()
    }

    protected inline fun <reified E> withinReadLimit(length: Int, action: () -> E): E {
        require(curLim - pos >= length) { "Buffer overflow, limit reached." }
        val out = action()
        pos += length
        return out
    }
}