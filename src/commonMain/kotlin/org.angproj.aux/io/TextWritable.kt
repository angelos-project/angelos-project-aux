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

import org.angproj.aux.buf.TextBuffer
import org.angproj.aux.utf.Ascii
import org.angproj.aux.util.CodePoint
import org.angproj.aux.util.Unicode
import org.angproj.aux.util.toCodePoint

public interface TextWritable {
    public fun write(codePoint: CodePoint): Int

    public fun write(str: String): Int {
        var cnt = 0
        Unicode.importUnicode(str) {
            write(it)
            cnt++
        }
        return cnt
    }

    public fun write(text: TextBuffer, offset: Int, count: Int): Int {
        var cnt = 0
        text.markAt() // Fix tomorrow
        while(text.position < text.limit && cnt < count) {
            this@TextWritable.write(text.readGlyph())
            cnt++
        }
        return cnt
    }

    public fun write(text: TextBuffer): Int { return write(text.readGlyph()) } // FIX

    public fun writeLine(text: Text, newLine: CodePoint = Ascii.CTRL_LF.cp.toCodePoint()): Int {
        var cnt = 0
        text.firstOrNull {
            this@TextWritable.write(it)
            cnt++
            it == newLine
        } ?: this@TextWritable.write(newLine).also { cnt++ }
        return cnt
    }
}