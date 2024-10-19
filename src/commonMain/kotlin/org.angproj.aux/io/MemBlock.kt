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
package org.angproj.aux.io

import org.angproj.aux.buf.SpeedCopy
import org.angproj.aux.util.Auto


public abstract class MemBlock internal constructor(
    segment: Segment, private val _view: Boolean
) : SpeedCopy(segment), Auto {

    public override val limit: Int
        get() = _segment.limit

    public override val capacity: Int
        get() = _segment.size

    /**
     * The same as on Buffer with upper limit.
     * */
    public fun limitAt(newLimit: Int) {
        require(newLimit in 0.._segment.size)
        _segment.limit = newLimit
    }

    /**
     * Reduced function compared to Buffer interface due to no rewind capability.
     * */
    public fun clear() {
        _segment.limit = _segment.size
    }

    internal inline fun <reified E: Any> remaining(position: Int): Int = _segment.limit - position

    override fun isView(): Boolean = _view

    override fun isMem(): Boolean = _segment is Memory

    override fun close() {
        if(!isView()) _segment.close()
    }

    public override fun equals(other: Any?): Boolean {
        if(this === other) return true
        if(other == null || this::class != other::class) return false
        other as MemBlock
        return _segment == other._segment
    }

    public override fun hashCode(): Int = _segment.hashCode()
}

public fun <E: MemBlock>E.asBinary(): Binary = Binary(this._segment, true)