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
public value class UtfString(private val octets: ByteArray) {

    public fun validate() {
        var index = 0
        while(index < octets.size) {
            val jump = octets[index].glyphSize()
            if(!octets.isGlyphValid(index, jump)) error("Invalid sequence at index $index.")
            if(!octets.readGlyphAt(index, jump).isValid()) error("Invalid code point at index $index.")
            index += jump
        }
    }
    public fun glyphStringSize(): Int {
        var count = 0
        var index = 0

        while(index < octets.size) {
            val jump = octets[index].glyphSize()
            if(jump == -1) error("Illegal Glyph")
            index += jump
            count++
        }

        return count
    }

    public fun toGlyphString(): GlyphString {
        val buffer = DataBuffer(this.octets)
        val glyphs = IntArray(glyphStringSize()) { buffer.readGlyph() }
        return GlyphString(glyphs)
    }

    public override fun toString(): String = octets.decodeToString()
}