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

public class GlyphIterator(private val glyphs: IntArray) : Iterator<Glyph> {
    private var position = 0

    override fun hasNext(): Boolean = position < glyphs.size

    override fun next(): Glyph = glyphs[position++]
}