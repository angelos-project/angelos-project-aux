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
import org.angproj.aux.io.PumpWriter
import org.angproj.aux.io.TextReadable
import org.angproj.aux.io.isNull
import org.angproj.aux.utf.CodePoint
import org.angproj.aux.utf.Glyph

public class TextSink(
    pump: PumpWriter = Pump
): AbstractSink<TextType>(pump), TextType, TextReadable {

    override fun readGlyph(): CodePoint = isNotClosed {
        Glyph.readStart {
            if(pos == seg.limit) pullSegment()
            seg.getByte(pos++)
        }
    }

    override fun dispose() {
        when(pipe) {
            is PushPipe -> Unit
            is PullPipe -> seg.also { if(!it.isNull()) it.close() }
            else -> error("Cannot dispose undefined pipe class.")
        }
    }
}