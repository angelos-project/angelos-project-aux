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

import org.angproj.aux.io.GlyphWritable
import org.angproj.aux.util.CodePoint
import org.angproj.aux.util.withUnicodeAware


public class GlyphSource(
    pipe: PushPipe
): AbstractSource<GlyphType>(pipe), GlyphWritable {
    override fun writeGlyph(codePoint: CodePoint): Int = withUnicodeAware {
        writeGlyphStrm(codePoint) {
            if(pos == seg.limit) pushSegment<Unit>()
            seg.setByte(pos++, it)
        }
    }
}