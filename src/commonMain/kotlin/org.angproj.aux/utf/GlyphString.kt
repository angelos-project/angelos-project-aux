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
package org.angproj.aux.utf

import org.angproj.aux.util.DataBuffer
import kotlin.jvm.JvmInline

@JvmInline
public value class GlyphString(private val glyphs: IntArray): Iterable<Glyph> {

    public fun validate() {
        when(val index = glyphs.first { !it.isValid() }) {
            -1 -> Unit
            else -> error("Invalid code point (${glyphs[index].toString(16)}) at index $index.")
        }
    }

    public fun utfStringSize(): Int {
        var size = 0
        glyphs.forEach {
            size = when(val codePoint = it.getSize()) {
                -1 -> error("Illegal Glyph")
                else -> codePoint
            }
        }
        return size
    }

    public fun toUtfString(): UtfString {
        val buffer = DataBuffer(utfStringSize())
        glyphs.forEach { buffer.writeGlyph(it) }
        return UtfString(buffer.asByteArray())
    }

    override fun iterator(): Iterator<Glyph> = GlyphIterator(glyphs)
}