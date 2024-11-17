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
import org.angproj.aux.mem.BufMgr
import org.angproj.aux.mem.Default
import org.angproj.aux.util.*
import kotlin.math.max
import kotlin.math.min


public class TextBuffer internal constructor(
    segment: Segment<*>, view: Boolean = false
): FlowBuffer(segment, view), GlyphReadable, GlyphWritable {

    public constructor(size: Int) : this(Default.allocate(size))

    public constructor(size: DataSize = DataSize._4K) : this(size.size)

    override fun create(segment: Segment<*>): TextBuffer = TextBuffer(segment)

    override fun readGlyph(): CodePoint = withUnicodeAware {
        readGlyphBlk(remaining) { _segment.getByte(_position++) } }

    override fun writeGlyph(codePoint: CodePoint): Int = withUnicodeAware {
        writeGlyphBlk(codePoint, remaining) { _segment.setByte(_position++, it) } }

    /**
     * Currently doesn't work
     * */
    public fun select(selection: IntRange = mark..limit): IntRange = withUnicodeAware {
        require(selection.first in mark..limit && selection.last in mark..limit) {
            "Selection outside mark and limit." }

        val first = (selection.first .. max(selection.first - 5, mark)).find {
            isGlyphStart(_segment.getByte(it)) }
        val last = (selection.last .. min(selection.last + 5, limit)).findLast {
            isGlyphStart(_segment.getByte(it)) }

        check(first != null) { "Invalid beginning of start glyph" }
        check(last != null) { "Invalid end of last glyph" }

        first until  (last+hasGlyphSize(_segment.getByte(last)))
    }

    public fun seekNext(index: Int): Int = withUnicodeAware {
        val size = hasGlyphSize(_segment.getByte(index))
        if(size > 0) index + size
        else {
            var pos = index
            while(!isGlyphStart(_segment.getByte(pos)) && pos < limit) pos++
            pos
        }
    }

    public fun seek(offset: Int): Int = withUnicodeAware {
        /*var pos = 0
        while(offset) {
            if(pos >= limit) error("Position not reachable, limit at ")
            val size = hasGlyphSize(_segment.getByte(pos))
            if (size < 1) error("Not a glyph at position $pos")
            pos += size
        }
        pos*/
        0
    }
}

public fun textOf(capacity: Int): TextBuffer = BufMgr.text(capacity)

/**
 * For proper copying of a certain field of characters, markAt() and limitAt() has to be set first.
 * Make sure that the TextBuffer is flipped using flip() first.
 * */
public fun TextBuffer.toText(selection: IntRange = mark..limit): Text = with(select(selection)) {
    BufMgr.txt(last - first).apply { this@toText.copyInto(this, 0, first, last) }
}

/*public fun TextBuffer.toText(selection: IntRange = mark..limit): Text = with(selection) {
    BufMgr.txt(last - first).apply { this@toText.copyInto(this, 0, first, last) }
}*/
