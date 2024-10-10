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

import org.angproj.aux.io.Segment
import org.angproj.aux.io.TypeSize


public abstract class ArrayBuffer<E> protected constructor(
    segment: Segment, view: Boolean = false, protected val typeSize: TypeSize
): Buffer(segment, view), Iterable<E> {

    override fun iterator(): Iterator<E> = object: Iterator<E> {
        private var index = 0
        override fun hasNext(): Boolean = index < limit
        override fun next(): E = this@ArrayBuffer[index++]
    }

    public val indices: IntRange by lazy { 0..<limit }
    public val lastIndex: Int = limit - 1

    abstract override fun create(segment: Segment): ArrayBuffer<E>

    /**
     * Gives the max capacity of the buffer
     * */
    public override val capacity: Int
        get() = _segment.size / typeSize.size

    /**
     * The current limit of the buffer as defined.
     * */
    public final override val limit: Int
        get() = _segment.limit / typeSize.size

    public abstract operator fun get(index: Int): E
    public abstract operator fun set(index: Int, value: E)
}