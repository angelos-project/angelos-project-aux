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

import org.angproj.aux.util.*


public class Text internal constructor(
    internal val _segment: Segment, protected val view: Boolean = false
) : Auto, TextRetrievable, TextStorable, Iterable<CodePoint> {

    public constructor(size: Int) : this(Bytes(size))

    public constructor(size: DataSize = DataSize._4K) : this(size.size)

    public val segment: Segment
        get() = _segment

    public val limit: Int
        get() = _segment.limit

    public val capacity: Int
        get() = _segment.size

    /**
     * The same as on Buffer with upper limit.
     * */
    public fun limitAt(newLimit: Int) {
        require(newLimit in 0.._segment.size)
        _segment.limit = newLimit
    }

    /**
     * Reduced function compared to Buffer interface due to no rewind capability.
     * */
    public fun clear() {
        _segment.limit = _segment.size
    }

    override fun iterator(): Iterator<CodePoint> = object: Iterator<CodePoint> {
        private var position = 0
        override fun hasNext(): Boolean = remaining<Int>(position) > 0
        override fun next(): CodePoint = this@Text.retrieveGlyph(position).also { position += it.octetSize() }
    }

    private inline fun <reified E: Number> remaining(position: Int): Int = _segment.limit - position

    override fun retrieveGlyph(position: Int): CodePoint = withUnicodeAware {
        var offset = position
        readGlyphBlk(remaining<Int>(offset)) { _segment.getByte(offset++) } }

    override fun storeGlyph(position: Int, codePoint: CodePoint): Int = withUnicodeAware {
        var offset = position
        writeGlyphBlk(codePoint, remaining<Int>(offset)) { _segment.setByte(offset++, it) } }

    override fun isView(): Boolean = view

    override fun isMem(): Boolean = _segment is Memory

    override fun close() {
        _segment.close()
    }

    public fun asBinary(): Binary = Binary(_segment, true)
}

public fun String.toText(): Text = Text(Unicode.importByteSize(this)).also { tb ->
    var offset = 0
    Unicode.importUnicode(this) { offset += tb.storeGlyph(offset, it) }
}

public fun Text.isNull(): Boolean = NullObject.text === this
private val nullText = Text(NullObject.segment)
public val NullObject.text: Text
    get() = nullText