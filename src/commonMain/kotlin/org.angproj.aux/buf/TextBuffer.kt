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
import org.angproj.aux.util.CodePoint
import org.angproj.aux.util.Unicode
import org.angproj.aux.util.withUnicodeAware

public class TextBuffer internal constructor(
    segment: Segment, view: Boolean = false
): FlowBuffer(segment, view), TextReadable, TextWritable {

    public constructor(size: Int) : this(Bytes(size))

    public constructor(size: DataSize = DataSize._4K) : this(size.size)

    override fun create(segment: Segment): TextBuffer = TextBuffer(segment)

    override fun readGlyph(): CodePoint = withUnicodeAware {
        readGlyphBlk(remaining) { segment.getByte(_position++) } }

    override fun writeGlyph(codePoint: CodePoint): Int = withUnicodeAware {
        writeGlyphBlk(codePoint, remaining) { segment.setByte(_position++, it) } }
}


public fun String.toTextBuffer(size: DataSize = DataSize._4K): TextBuffer = TextBuffer(size).also { tb ->
    Unicode.importUnicode(this) { tb.writeGlyph(it) }
}