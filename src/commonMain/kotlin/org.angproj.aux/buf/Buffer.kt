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
package org.angproj.aux.buf

import org.angproj.aux.io.Memory
import org.angproj.aux.io.Segment
import org.angproj.aux.util.Auto

public abstract class Buffer protected constructor(
    internal val _segment: Segment, protected val view: Boolean = false
): Auto {

    internal abstract fun create(segment: Segment): Buffer

    public val segment: Segment
        get() = _segment

    /**
     * Gives the max capacity of the buffer
     * */
    public val capacity: Int
        get() = _segment.size

    protected var _position: Int = 0
    /**
     * The current position of the cursor in the buffer.
     * */
    public val position: Int
        get() = _position

    /**
     * Jump to a defined position.
     * The newPos has to be between the current mark and the current limit.
     * */
    public fun positionAt(newPos: Int) {
        require(newPos in _mark.._segment.limit)
        _position = newPos
    }

    /**
     * The current limit of the buffer as defined.
     * */
    public val limit: Int
        get() = _segment.limit

    /**
     * Set a new limit of the buffer.
     * The newLimit must be between the current mark and given capacity.
     * If the limit is before the current position, the position is moved to the new limit.
     * */
    public fun limitAt(newLimit: Int) {
        require(newLimit in _mark.._segment.size)
        _segment.limit = newLimit
        if(_position > newLimit) _position = newLimit
    }

    private var _mark: Int = 0
    /**
     * Given mark at position zero or as latest defined.
     * */
    public val mark: Int
        get() = _mark

    /**
     * Set the mark defined by the current position.
     * */
    public fun markAt() {
        _mark = _position
    }

    /**
     * Reset the current position to the latest defined mark.
     * */
    public fun reset() {
        _position = _mark
    }

    /**
     * Rewinds and set limit to the given capacity.
     * */
    public fun clear() {
        rewind()
        _segment.limit = _segment.size
    }

    /**
     *  Flips the limit to the current position,
     *  and secondly rewinds.
     * */
    public fun flip() {
        _segment.limit = _position
        rewind()
    }

    /**
     * Sets both the position and mark to zero.
     * */
    public fun rewind() {
        _mark = 0
        _position = 0
    }

    /**
     * Gives the remaining bytes between position and limit.
     * */
    public val remaining: Int
        get() = _segment.limit - _position

    override fun isView(): Boolean = view

    override fun isMem(): Boolean = _segment is Memory

    override fun close() {
        _segment.close()
    }
}

/**
 * Copy the current buffer from between mark and limit into the position of the destination buffer.
 * */
public fun<T: Buffer> T.copyInto(destination: T) { 
    _segment.calculateInto(destination._segment, destination.position, mark, limit) }

/**
 * Make a new copy buffer of the current buffer between mark and limit.
 * */
@Suppress("UNCHECKED_CAST")
public fun<T: Buffer> T.copyOfRange(): T = create(_segment.copyOfRange(mark, limit)) as T

/**
 * Make a full buffer copy.
 * */
@Suppress("UNCHECKED_CAST")
public fun<T: Buffer> T.copyOf(): T = create(_segment.copyOf()) as T