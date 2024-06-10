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

import org.angproj.aux.io.*
import org.angproj.aux.util.NullObject
import org.angproj.aux.util.Reifiable
import org.angproj.aux.util.Reify

public abstract class AbstractSink<T: PipeType>(
    protected val pipe: PullPipe<T>
) : Sink, PipeType {
    protected var pos: Int = 0
    protected var seg: Segment = NullObject.segment

    protected var _open: Boolean = true

    init {
        pullSegment<Reify>()
    }

    private fun<reified : Reifiable> nextSegment(open: Boolean = true) {
        pipe.recycle<Reify>(seg)
        seg = when(open){
            true -> pipe.pop<Reify>()
            else -> NullObject.segment
        }
        pos = 0
    }

    protected fun<reified : Reifiable> pullSegment() {
        if(pipe.isExhausted<Reify>()) pipe.tap<Reify>()
        nextSegment<Reify>()
        if(seg.isNull()) _open = false
    }

    override fun isOpen(): Boolean = _open

    override fun close() {
        if(_open) {
            pipe.dispose<Reify>()
            nextSegment<Reify>(false)
            _open = false
        }
    }
}