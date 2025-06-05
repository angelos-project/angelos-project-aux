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

import org.angproj.aux.io.GlyphReadable
import org.angproj.aux.util.CodePoint
import org.angproj.aux.util.UnicodeAware

public class GlyphSink(
    pipe: PullPipe
): AbstractSink<GlyphType>(pipe), GlyphReadable, UnicodeAware {

    override fun readGlyph(): CodePoint = readGlyphStrm {
            if(pos == seg.limit) pullSegment<Unit>()
            seg.getByte(pos++)
        }
}