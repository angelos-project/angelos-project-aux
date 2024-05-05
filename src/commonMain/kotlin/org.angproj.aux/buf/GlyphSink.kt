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
package org.angproj.aux.buf

import org.angproj.aux.io.TextReadable
import org.angproj.aux.utf.Glyph

public class GlyphSink(pump: Pump = Pump): AbstractSink<PipeType.TXT>(pump), TextReadable {
    override fun readGlyph(): Glyph {
        TODO("Not yet implemented")
    }
}