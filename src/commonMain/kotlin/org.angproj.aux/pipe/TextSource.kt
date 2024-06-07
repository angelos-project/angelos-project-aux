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

import org.angproj.aux.buf.Pump
import org.angproj.aux.io.PumpReader
import org.angproj.aux.io.TextWritable
import org.angproj.aux.io.segment
import org.angproj.aux.utf.CodePoint
import org.angproj.aux.utf.Glyph
import org.angproj.aux.util.NullObject

public class TextSource(pump: PumpReader = Pump): AbstractSource<TextType>(pump), TextType, TextWritable {

    override fun writeGlyph(codePoint: CodePoint): Int = isNotClosed {
        Glyph.writeStart(codePoint) {
            if(pos == seg.limit) pushSegment()
            seg.setByte(pos++, it)
        }
    }

    override fun dispose() {
        when(pipe) {
            is PushPipe -> {
                if(pipe.isCrammed) pipe.drain()
                seg.limit = pos
                pipe.push(seg)
                seg = NullObject.segment
                pos = 0
                pipe.drain()
            }
            is PullPipe -> Unit
            else -> error("Cannot dispose undefined pipe class.")
        }
    }
}