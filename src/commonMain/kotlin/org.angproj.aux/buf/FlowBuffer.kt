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

import org.angproj.aux.io.*
import org.angproj.aux.util.BufferAware
import org.angproj.aux.util.Copy
import org.angproj.aux.util.Copyable


public abstract class FlowBuffer protected constructor(
    segment: Segment<*>, view: Boolean = false
): Buffer(segment, view) {

    private var _innerOffset: Int = 0
    private var _count: Long = 0
     public val count: Long
         get() = _count + (_position - _innerOffset)

    /**
     * Gives the max capacity of the buffer
     * */
    public override val capacity: Int
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
        _count += _position - _innerOffset
        _position = newPos
        _innerOffset = _position
    }

    /**
     * The current limit of the buffer as defined.
     * */
    public override val limit: Int
        get() = _segment.limit

    /**
     * Set a new limit of the buffer.
     * The newLimit must be between the current mark and given capacity.
     * If the limit is before the current position, the position is moved to the new limit.
     * */
    public fun limitAt(newLimit: Int) {
        require(newLimit in _mark.._segment.size)
        _segment.limitAt(newLimit)
        if(_position > newLimit) positionAt(newLimit) //_position = newLimit
    }

    protected var _mark: Int = 0
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
        positionAt(_mark) //_position = _mark
    }

    /**
     * Rewinds and set limit to the given capacity.
     * */
    public fun clear() {
        rewind()
        _segment.limitAt(_segment.size)
    }

    /**
     *  Flips the limit to the current position,
     *  and secondly rewinds.
     * */
    public fun flip() {
        _segment.limitAt(_position)
        rewind()
    }

    /**
     * Sets both the position and mark to zero.
     * */
    public fun rewind() {
        _mark = 0
        positionAt(0) // _position = 0
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

public fun FlowBuffer.toByteArray(): ByteArray = object : Copy {
    operator fun invoke(): ByteArray {
        check(_segment.isOpen) { "Closed memory" }
        val dst = object : Copyable, BufferAware {
            override val limit: Int = this@toByteArray.limit - this@toByteArray.mark
            val outArr = ByteArray(limit)

            override fun getLong(index: Int): Long = outArr.readLongAt(index)
            override fun getByte(index: Int): Byte = outArr.get(index)
            override fun setLong(index: Int, value: Long) { outArr.writeLongAt(index, value) }
            override fun setByte(index: Int, value: Byte) { outArr.set(index, value)}
        }
        require(0, limit, 0, _segment, dst)
        innerCopy(0, limit, 0, _segment, dst)
        return dst.outArr
    }
}()


/**
 * Copy the current buffer from between mark and limit into the position of the destination buffer.
 * */
/*public fun<T: FlowBuffer> T.copyInto(destination: T) {
    _segment.copyInto(destination._segment, destination.position, mark, limit)
}*/

/**
 * Make a new copy buffer of the current buffer between mark and limit.
 * */
//@Suppress("UNCHECKED_CAST")
//public fun<T: FlowBuffer> T.copyOfRange(): T = create(_segment.copyOfRange(mark, limit)) as T

/**
 * Make a full buffer copy.
 * */
//@Suppress("UNCHECKED_CAST")
//public fun<T: FlowBuffer> T.copyOf(): T = create(_segment.copyOf()) as T