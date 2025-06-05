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


public class TextBuffer internal constructor(
    segment: Segment<*>, view: Boolean = false
): FlowBuffer(segment, view), TextReadable, TextWritable, UnicodeAware {

    public constructor(size: Int) : this(Default.allocate(size))

    public constructor(size: DataSize = DataSize._4K) : this(size.size)

    override fun create(segment: Segment<*>): TextBuffer = TextBuffer(segment)

    override fun read(): CodePoint = readGlyphBlk(remaining) { _segment.getByte(_position++) }

    override fun read(text: TextBuffer, offset: Int, length: Int): Int {
        text.positionAt(offset)
        var cnt = 0
        try {
            while(cnt < length) { cnt += text.write(read()) }
        } catch (_: IllegalStateException) {}
        return cnt
    }

    override fun write(codePoint: CodePoint): Int = writeGlyphBlk(codePoint, remaining) {
        _segment.setByte(_position++, it) }

    override fun write(txt: Text): Int {
        var cnt = 0
        try {
            txt.forEach { cnt += write(it) }
        } catch (_: IllegalStateException) {}
        return cnt
    }

    override fun write(str: String): Int {
        var cnt = 0
        try {
            importUnicode(str) { cnt += write(it) }
        } catch (_: IllegalStateException) {}
        return cnt
    }

    override fun write(text: TextBuffer, offset: Int, length: Int): Int {
        text.positionAt(offset)
        var cnt = 0
        try {
            while(cnt < length) { cnt += write(text.read()) }
        } catch (_: IllegalStateException) {}
        return cnt
    }

    /*override fun writeLine(text: Text, newLine: CodePoint): Int {
        var cnt = 0
        text.firstOrNull {
            write(it)
            cnt++
            it == newLine
        } ?: write(newLine).also { cnt++ }
        return cnt
    }*/

    /**
     * Gets the byte [index] of the next glyph if outside [toIndex] it returns its original value,
     * or throws an error if malformed octet byte.
     * */
    protected fun jumpNext(index: Int): Int {
        val offset: Int = hasGlyphSize(_segment.getByte(index)) + index
        return when {
            offset > index -> if(offset < limit) offset else index
            else -> error("Index not beginning of UTF-8 glyph.")
        }
    }

    /**
     * Seeks from byte [index] after the beginning byte of next UTF-8 glyph up to [limit].
     * */
    protected fun seekNext(index: Int): Int {
        var pos = index
        do { pos++ } while(!isGlyphStart(_segment.getByte(pos)) && pos < limit)
        return pos
    }

    protected fun currentOrSeekNext(index: Int): Int = if(isGlyphStart(_segment.getByte(index))) index else seekNext(index)

    /**
     * Seeks from byte [index] after the beginning byte of previous UTF-8 glyph down to [mark].
     * */
    protected fun seekPrev(index: Int): Int {
        var pos = index
        do { pos-- } while(!isGlyphStart(_segment.getByte(pos)) && pos >= _mark)
        return pos
    }

    protected fun currentOrSeekPrev(index: Int): Int = if(isGlyphStart(_segment.getByte(index))) index else seekPrev(index)

    /**
     * Seeks for the byte index of [offset] which is X number of multibyte characters,
     * between [fromIndex] and [toIndex] which defaults to [mark] and [limit], if the
     * seek goes out of range it returns -1.
     * */
    public fun seek(offset: Int, fromIndex: Int = mark, toIndex: Int = limit): Int {
        var current = currentOrSeekPrev(fromIndex)
        var pos = 0
        while(current < toIndex && pos < offset) {
            val next = jumpNext(current)
            if(current == next) break
            else current = next
            pos++
        }
        return if(pos == offset) current else -1
    }
}

public fun textOf(capacity: Int): TextBuffer = BufMgr.text(capacity)

