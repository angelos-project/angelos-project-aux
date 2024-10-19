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


public abstract class SpeedCopy internal constructor(segment: Segment) {
    public abstract val capacity: Int
    public abstract val limit: Int
    internal val _segment: Segment = segment
}

public fun<C: SpeedCopy> SpeedCopy.copyInto(destination: C, destinationOffset: Int, fromIndex: Int, toIndex: Int) {
    check(_segment.isOpen && destination._segment.isOpen) { "Closed memory" }
    _segment.copyInto(destination._segment, destinationOffset, fromIndex, toIndex)
}