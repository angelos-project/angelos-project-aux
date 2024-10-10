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
    public abstract val capacity: Int

    /**
     * The current limit of the buffer as defined.
     * */
    public abstract val limit: Int

    override fun isView(): Boolean = view

    override fun isMem(): Boolean = _segment is Memory

    override fun close() {
        _segment.close()
    }
}