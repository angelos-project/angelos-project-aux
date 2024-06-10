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

import org.angproj.aux.io.Segment
import org.angproj.aux.io.isNull
import org.angproj.aux.io.segment
import org.angproj.aux.util.NullObject
import org.angproj.aux.util.Reifiable
import org.angproj.aux.util.Reify

public abstract class AbstractSource<T: PipeType>(
    protected val pipe: PushPipe<T>
): Source, PipeType {
    protected var pos: Int = 0
    protected var seg: Segment = NullObject.segment

    protected var _open: Boolean = true

    init {
        nextSegment<Reify>(true)
    }

    private fun<reified : Reifiable> nextSegment(open: Boolean) {
        seg = when(open){
            true -> pipe.allocate<Reify>(pipe.segSize.size)
            else -> NullObject.segment
        }
        pos = 0
    }

    protected fun<reified : Reifiable>pushSegment() {
        if(!seg.isNull()) {
            if(pipe.isSinkOpen<Reify>() && pipe.isCrammed<Reify>()) pipe.drain<Reify>()
            pipe.push<Reify>(seg)
            nextSegment<Reify>(pipe.isSinkOpen<Reify>())
        }
    }

    public fun flush() {
        pushSegment<Reify>()
        pipe.drain<Reify>()
    }

    override fun isOpen(): Boolean = _open

    override fun close() {
        if(_open) {
            pipe.dispose<Reify>()
            if(!seg.isNull()) {
                pipe.recycle<Reify>(seg)
                nextSegment<Reify>(false)
            }
            _open = false
        }
    }
}