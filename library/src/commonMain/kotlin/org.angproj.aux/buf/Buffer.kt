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

import org.angproj.aux.io.Binary
import org.angproj.aux.io.Memory
import org.angproj.aux.io.Segment
import org.angproj.aux.util.Auto
import org.angproj.aux.util.Uuid4


public abstract class Buffer protected constructor(
    segment: Segment<*>, protected val view: Boolean = false
): SpeedCopy(segment), Auto, Comparable<Buffer> {

    internal abstract fun create(segment: Segment<*>): Buffer

    /**
     * Gives the max capacity of the buffer
     * */
    public abstract override val capacity: Int

    /**
     * The current limit of the buffer as defined.
     * */
    public abstract override val limit: Int

    override fun isView(): Boolean = view

    override fun isMem(): Boolean = _segment is Memory

    override fun close() {
        if(!isView()) _segment.close()
    }

    public override fun equals(other: Any?): Boolean {
        if(this === other) return true
        if(other == null || this::class != other::class) return false
        other as Buffer
        return compareTo(other) == 0
    }

    public override fun hashCode(): Int = _segment.hashCode()

    public override operator fun compareTo(other: Buffer): Int { return hashCode() - other.hashCode() }
}


//public fun <S: Buffer, D: Buffer>S.copyOfRange(fromIndex: Int, toIndex: Int): D

//public fun <S: Buffer, D: Buffer>S.copyOf(): D

/*@Deprecated("Has moved to Binary", ReplaceWith("x.asBinary.securelyRandomize()"))
public fun Buffer.random() { SecureRandom.read(_segment) }*/

public fun Buffer.random() { Uuid4.read(_segment) }

/**
 * View extension method for all Buffers.
 * This method should be the only used method to manipulate data within any buffer used.
 * */
public fun <E: Buffer>E.asBinary(): Binary = Binary(this._segment, true)