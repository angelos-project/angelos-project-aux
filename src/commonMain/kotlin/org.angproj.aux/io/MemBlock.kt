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

import org.angproj.aux.util.Auto


public abstract class MemBlock internal constructor(
    protected val _segment: Segment,
    private val _view: Boolean
) : Auto{

    public val segment: Segment
        get() = _segment

    public val limit: Int
        get() = _segment.limit

    public val capacity: Int
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

    protected inline fun <reified R: Number> remaining(position: Int): Int = _segment.limit - position

    public fun asBinary(): Binary = Binary(_segment, true)

    override fun isView(): Boolean = _view

    override fun isMem(): Boolean = _segment is Memory

    override fun close() {
        _segment.close()
    }

    public override fun equals(other: Any?): Boolean {
        if(this === other) return true
        if(other == null || this::class != other::class) return false
        other as MemBlock
        return _segment == other._segment
    }

    public override fun hashCode(): Int = _segment.hashCode()
}