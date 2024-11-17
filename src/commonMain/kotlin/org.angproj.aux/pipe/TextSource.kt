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
import org.angproj.aux.io.TextWritable
import org.angproj.aux.utf.Ascii
import org.angproj.aux.util.CodePoint
import org.angproj.aux.util.toCodePoint

public class TextSource(
    private val src: GlyphSource
): Source<TextType>, TextWritable {

    public constructor(pipe: PushPipe) : this(GlyphSource(pipe))

    override val count: Long
        get() = src.count

    public fun flush(): Unit = src.flush()

    override fun isOpen(): Boolean = src.isOpen()

    override fun close(): Unit = src.close()

    public fun writeLine(text: TextBuffer): Int = writeLine(text, Ascii.CTRL_LF.cp.toCodePoint())

    public fun writeLine(text: TextBuffer, newLine: CodePoint): Int {
        var cnt = 0
        do {
            val cp = text.readGlyph()
            cnt++
            src.writeGlyph(cp)
        } while (cp != newLine && text.position < text.limit)
        return cnt
    }

    override fun write(codePoint: CodePoint): Int {
        TODO("Not yet implemented")
    }
}