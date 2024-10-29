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
package org.angproj.aux.mem

import org.angproj.aux.buf.BinaryBuffer
import org.angproj.aux.buf.TextBuffer
import org.angproj.aux.io.*

public object BufMgr {
    public fun txt(size: Int): Text = Text(Default.allocate(size))

    public fun bin(size: Int): Binary = Binary(Default.allocate(size))

    public fun text(size: Int): TextBuffer = TextBuffer(Default.allocate(DataSize.findLowestAbove(size)))

    public fun binary(size: Int): BinaryBuffer = BinaryBuffer(Default.allocate(DataSize.findLowestAbove(size)))

    public inline fun<reified E> asWrap(segment: Segment<*>, block: Binary.() -> E): E = Binary(segment, true).block()

}