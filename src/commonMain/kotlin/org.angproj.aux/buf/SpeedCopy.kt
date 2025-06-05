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
import org.angproj.aux.res.speedMemCpy
import org.angproj.aux.util.UtilityAware
import org.angproj.aux.util.Copy


public abstract class SpeedCopy internal constructor(segment: Segment<*>): UtilityAware {
    public abstract val capacity: Int
    public abstract val limit: Int
    internal val _segment: Segment<*> = segment
}

public fun <C: SpeedCopy>C.copyInto(
    destination: C, destinationOffset: Int, fromIndex: Int, toIndex: Int
): Int = object : Copy {
    operator fun invoke(): Int {
        check(_segment.isOpen && destination._segment.isOpen) { "Closed memory" }
        return when {
            _segment is Memory && destination._segment is Memory ->  {
                require(fromIndex, toIndex, destinationOffset, _segment.data, destination._segment.data)
                speedMemCpy(fromIndex, toIndex, destinationOffset, _segment.data.ptr, destination._segment.data.ptr)
            }
            _segment is Memory -> {
                require(fromIndex, toIndex, destinationOffset, _segment.data, destination._segment)
                innerCopy(fromIndex, toIndex, destinationOffset, _segment.data, destination._segment)
            }
            else -> {
                require(fromIndex, toIndex, destinationOffset, _segment, destination._segment)
                innerCopy(fromIndex, toIndex, destinationOffset, _segment, destination._segment)
            }
        }
    }
}()