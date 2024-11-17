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
import org.angproj.aux.io.TextReadable
import org.angproj.aux.utf.Ascii
import org.angproj.aux.util.CodePoint
import org.angproj.aux.util.toCodePoint

public class TextSink(
    private val sink: GlyphSink
): Sink<TextType>, TextReadable {

    public constructor(pipe: PullPipe) : this(GlyphSink(pipe))

    override val count: Long
        get() = sink.count

    override fun isOpen(): Boolean = sink.isOpen()

    override fun close(): Unit = sink.close()

    public fun readLine(text: TextBuffer): Int = readLine(text, Ascii.CTRL_LF.cp.toCodePoint())

    public fun readLine(text: TextBuffer, newLine: CodePoint): Int {
        var cnt = 0
        do {
            val cp = sink.readGlyph()
            cnt++
            text.writeGlyph(cp)
        } while (cp != newLine && text.position < text.limit)
        return cnt
    }

    override fun read(): CodePoint {
        TODO("Not yet implemented")
    }
}