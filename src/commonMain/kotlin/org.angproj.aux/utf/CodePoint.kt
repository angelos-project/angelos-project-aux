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

import kotlin.jvm.JvmInline

@JvmInline
public value class CodePoint(public val value: Int) {
    public fun isValid(): Boolean = value in Glyph.GLYPH_RANGE && value !in Glyph.GLYPH_HOLE

    public fun escapeInvalid(): CodePoint = when(isValid()) {
        false -> Glyph.REPLACEMENT_CHARACTER
        else -> this
    }

    public fun sectionTypeOf(): SequenceType = when(value) {
        in Glyph.GLYPH_HOLE -> SequenceType.ILLEGAL
        in Glyph.GLYPH_SIZE_1 -> SequenceType.START_ONE_LONG
        in Glyph.GLYPH_SIZE_2 -> SequenceType.START_TWO_LONG
        in Glyph.GLYPH_SIZE_3 -> SequenceType.START_THREE_LONG
        in Glyph.GLYPH_SIZE_4 -> SequenceType.START_FOUR_LONG
        else -> SequenceType.ILLEGAL
    }
}

public fun Int.toCodePoint(): CodePoint = CodePoint(this)