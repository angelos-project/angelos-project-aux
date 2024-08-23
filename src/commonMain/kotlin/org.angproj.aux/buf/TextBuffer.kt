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
import org.angproj.aux.utf.CodePoint
import org.angproj.aux.util.withUnicodeAware

public class TextBuffer private constructor(
    segment: Segment, view: Boolean = false
): Buffer(segment, view), TextReadable, TextWritable {

    public constructor(size: Int) : this(Bytes(size))

    public constructor(size: DataSize = DataSize._4K) : this(size.size)

    override fun create(segment: Segment): TextBuffer = TextBuffer(segment)

    override fun readGlyph(): CodePoint = withUnicodeAware {
        readGlyphSafe(remaining) { segment.getByte(_position++) } }

    override fun writeGlyph(codePoint: CodePoint): Int = withUnicodeAware {
        writeGlyphSafe(codePoint, remaining) { segment.setByte(_position++, it) } }
}