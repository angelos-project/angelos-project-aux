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
import org.angproj.aux.util.BufferAware
import org.angproj.aux.util.Copy
import org.angproj.aux.util.Copyable


public abstract class MemBlock internal constructor(
    segment: Segment<*>, private val _view: Boolean
) : SpeedCopy(segment), Auto, Comparable<MemBlock> {

    public override val limit: Int
        get() = _segment.limit

    public override val capacity: Int
        get() = _segment.size

    /**
     * The same as on Buffer with upper limit.
     * */
    public fun limitAt(newLimit: Int) {
        require(newLimit in 0.._segment.size)
        _segment.limitAt(newLimit)
    }

    /**
     * Reduced function compared to Buffer interface due to no rewind capability.
     * */
    public fun clear() {
        _segment.limitAt(_segment.size)
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
        return compareTo(other) == 0
    }

    public override fun hashCode(): Int = _segment.hashCode()

    public override operator fun compareTo(other: MemBlock): Int { return hashCode() - other.hashCode() }
}

/**
 * View extension method for all MemBlocks.
 * This method should be the only used method to manipulate data within any short memory block used.
 * */
public fun <E: MemBlock>E.asBinary(): Binary = Binary(this._segment, true)

public fun <E: MemBlock>E.toByteArray(): ByteArray = object : Copy {
    operator fun invoke(): ByteArray {
        check(_segment.isOpen) { "Closed memory" }
        val dst = object : Copyable, BufferAware {
            override val limit: Int = _segment.limit
            val outArr = ByteArray(limit)

            override fun getLong(index: Int): Long = outArr.readLongAt(index)
            override fun getByte(index: Int): Byte = outArr.get(index)
            override fun setLong(index: Int, value: Long) { outArr.writeLongAt(index, value) }
            override fun setByte(index: Int, value: Byte) { outArr.set(index, value)}
        }
        if(limit == 0) return byteArrayOf()
        require(0, limit, 0, _segment, dst)
        innerCopy(0, limit, 0, _segment, dst)
        return dst.outArr
    }
}()