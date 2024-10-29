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

import org.angproj.aux.mem.BufMgr
import org.angproj.aux.util.*


public class Text internal constructor(
     segment: Segment<*>, view: Boolean = false
) : MemBlock(segment, view), TextRetrievable, TextStorable, Iterable<CodePoint> {

    //public constructor(size: Int) : this(Bytes(size))

    //public constructor(size: DataSize = DataSize._4K) : this(size.size)

    override fun iterator(): Iterator<CodePoint> = object: Iterator<CodePoint> {
        private var position = 0
        override fun hasNext(): Boolean = remaining<Int>(position) > 0
        override fun next(): CodePoint = this@Text.retrieveGlyph(position).also { position += it.octetSize() }
    }

    override fun retrieveGlyph(position: Int): CodePoint = withUnicodeAware {
        var offset = position
        readGlyphBlk(remaining<Int>(offset)) { _segment.getByte(offset++) } }

    override fun storeGlyph(position: Int, codePoint: CodePoint): Int = withUnicodeAware {
        var offset = position
        writeGlyphBlk(codePoint, remaining<Int>(offset)) { _segment.setByte(offset++, it) } }
}

public fun String.toText(): Text = BufMgr.txt(Unicode.importByteSize(this)).also { tb ->
    var offset = 0
    Unicode.importUnicode(this) { offset += tb.storeGlyph(offset, it) }
}

public fun Text.isNull(): Boolean = NullObject.text === this
private val nullText = Text(NullObject.segment)
public val NullObject.text: Text
    get() = nullText