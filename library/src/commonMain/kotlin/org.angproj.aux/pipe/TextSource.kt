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
package org.angproj.aux.pipe

import org.angproj.aux.buf.TextBuffer
import org.angproj.aux.io.Text
import org.angproj.aux.io.TextWritable
import org.angproj.aux.util.CodePoint
import org.angproj.aux.util.Measurable
import org.angproj.aux.util.UnicodeAware

public class TextSource(
    private val src: GlyphSource
): Source<TextType>, TextWritable, Measurable<PipeStats>, UnicodeAware {

    public constructor(pipe: PushPipe) : this(GlyphSource(pipe))

    override val count: Long
        get() = src.count

    public fun flush(): Unit = src.flush()

    override fun isOpen(): Boolean = src.isOpen()

    override fun close(): Unit = src.close()

    public override fun telemetry(): PipeStats = src.telemetry()

    /*public fun writeLine(text: TextBuffer): Int = writeLine(text, Ascii.CTRL_LF.cp.toCodePoint())

    public fun writeLine(text: TextBuffer, newLine: CodePoint): Int {
        var cnt = 0
        do {
            val cp = text.read()
            cnt++
            src.writeGlyph(cp)
        } while (cp != newLine && text.position < text.limit)
        return cnt
    }*/

    override fun write(codePoint: CodePoint): Int = src.writeGlyph(codePoint)

    override fun write(str: String): Int {
        var cnt = 0
        try {
            importUnicode(str) { cnt += src.writeGlyph(it) }
        } catch (_: IllegalStateException) {}
        return cnt
    }

    override fun write(txt: Text): Int {
        var cnt = 0
        try {
            txt.forEach { cnt += src.writeGlyph(it) }
        } catch (_: IllegalStateException) {}
        return cnt
    }

    override fun write(text: TextBuffer, offset: Int, length: Int): Int {
        text.positionAt(text.seek(offset))
        var cnt = 0
        try {
            while(cnt < length) { cnt += src.writeGlyph(text.read()) }
        } catch (_: IllegalStateException) {}
        return cnt
    }
}