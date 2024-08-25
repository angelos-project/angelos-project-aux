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

import org.angproj.aux.io.TextReadable
import org.angproj.aux.utf.CodePoint
import org.angproj.aux.util.Reify
import org.angproj.aux.util.withUnicodeAware

public class TextSink(
    pipe: PullPipe<TextType>
): AbstractSink<TextType>(pipe), TextType, TextReadable {

    override fun readGlyph(): CodePoint = withUnicodeAware {
        readGlyphStrm {
            if(pos == seg.limit && _open) pullSegment<Reify>()
            seg.getByte(pos++)
        }
    }
}