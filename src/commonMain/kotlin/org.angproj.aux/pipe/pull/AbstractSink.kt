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
package org.angproj.aux.pipe.pull

import org.angproj.aux.io.*
import org.angproj.aux.util.NullObject
import org.angproj.aux.util.Reifiable
import org.angproj.aux.util.Reify

public abstract class AbstractSink<T: PipeType>(protected val pipe: PullPipe<T>) : Sink, PipeType {
    protected var pos: Int = 0
    protected var seg: Segment = NullObject.segment

    private var _open: Boolean = true

    init {
        pullSegment<Reify>()
    }

    protected fun<reified : Reifiable> pullSegment() {
        if(pipe.isExhausted<Reify>()) pipe.tap<Reify>()
        pipe.recycle<Reify>(seg)
        seg = pipe.pop<Reify>()
        pos = 0
        if(seg.isNull()) close()
    }

    override fun isOpen(): Boolean = _open

    override fun close() {
        _open = false
    }
}