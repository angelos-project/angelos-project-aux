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

import org.angproj.aux.io.TextWritable
import org.angproj.aux.utf.CodePoint
import org.angproj.aux.util.Reify
import org.angproj.aux.util.withUnicodeAware


public class TextSource(
    pipe: PushPipe<TextType>
): AbstractSource<TextType>(pipe), TextType, TextWritable {
    override fun writeGlyph(codePoint: CodePoint): Int = withUnicodeAware {
        writeGlyph(codePoint) {
            if(pos == seg.limit && _open) pushSegment<Reify>()
            seg.setByte(pos++, it)
        }
    }
}