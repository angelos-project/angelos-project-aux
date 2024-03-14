/**
 * Copyright (c) 2022-2024 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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

public typealias Glyph = Int

public const val GLYPH_MAX_VALUE: Int = 0x10FFFF
public const val GLYPH_MIN_VALUE: Int = 0x0

internal val GLYPH_RANGE: IntRange = GLYPH_MIN_VALUE..GLYPH_MAX_VALUE
internal val GLYPH_HOLE: IntRange = 0xD800..0xDFFF
internal val GLYPH_SIZE_1: IntRange = 0x00..0x7F
internal val GLYPH_SIZE_2: IntRange = 0x80..0x7FF
internal val GLYPH_SIZE_3: IntRange = 0x800..0xFFFF
internal val GLYPH_SIZE_4: IntRange = 0x1_0000..0x10_FFFF
public const val REPLACEMENT_CHARACTER: Glyph = 0xFFFD

public fun Glyph.isValid(): Boolean = this in GLYPH_RANGE && this !in GLYPH_HOLE

public fun Glyph.escapeInvalid(): Glyph = when(isValid()) {
    false -> REPLACEMENT_CHARACTER
    else -> this
}

public fun Glyph.getSize(): Int = when (this) {
    in GLYPH_SIZE_1 -> 1
    in GLYPH_SIZE_2 -> 2
    in GLYPH_SIZE_3 -> 3
    in GLYPH_SIZE_4 -> 4
    else -> -1
}


// https://www.ietf.org/rfc/rfc2279.txt
// https://www.ietf.org/rfc/rfc3629.txt
// https://en.wikipedia.org/wiki/UTF-8


/**
 * Takes a UTF-8 leading octet as a Byte and check what size the multibyte character has.
 * Also works as validation of the first octet in a UTF-8 binary octet sequence.
 */
public fun Byte.glyphSize(): Int = when {
    (this.toInt() and -0B1000_0000) == 0 -> 1
    (this.toInt() and 0B1110_0000) == 0B1100_0000 -> 2
    (this.toInt() and 0B1111_0000) == 0B1110_0000 -> 3
    (this.toInt() and 0B1111_1000) == 0B1111_0000 -> 4
    (this.toInt() and 0B1111_1100) == 0B1111_1000 -> 5 // Just to deal with the illegal sequence
    (this.toInt() and 0B1111_1110) == 0B1111_1100 -> 6 // Just to deal with the illegal sequence
    else -> -1
}


/**
 * Validates a UTF-8 binary sequence, presuming that the first octet is already known.
 */
public fun ByteArray.isGlyphValid(pos: Int, size: Int): Boolean = when (size) {
    1 -> true
    2 -> this[pos + 1].toInt() and -0B1000000 == -0B10000000

    3 -> this[pos + 1].toInt() and -0B1000000 == -0B10000000 &&
            this[pos + 2].toInt() and -0B1000000 == -0B10000000

    4 -> this[pos + 1].toInt() and -0B1000000 == -0B10000000 &&
            this[pos + 2].toInt() and -0B1000000 == -0B10000000 &&
            this[pos + 3].toInt() and -0B1000000 == -0B10000000
    else -> false
}

public fun glyphRead(data: ByteArray, pos: Int, size: Int): Glyph = when (size) {
    1 -> 0B01111111 and data[pos].toInt()
    2 -> (0B00011111 and data[pos].toInt() shl 6) or
            (0B00111111 and data[pos + 1].toInt())

    3 -> (0B00001111 and data[pos].toInt() shl 12) or
            (0B00111111 and data[pos + 1].toInt() shl 6) or
            (0B00111111 and data[pos + 2].toInt())

    4 -> (0B00000111 and data[pos].toInt() shl 18) or
            (0B00111111 and data[pos + 1].toInt() shl 12) or
            (0B00111111 and data[pos + 2].toInt() shl 6) or
            (0B00111111 and data[pos + 3].toInt())
    else -> REPLACEMENT_CHARACTER
}

public fun glyphWrite(data: ByteArray, pos: Int, glyph: Glyph, size: Int): Unit = when (size) {
    1 -> data[pos] = (0B0000000_00000000_00000000_01111111 and glyph).toByte()
    2 -> {
        data[pos] = ((0B0000000_00000000_00000111_11000000 and glyph shr 6) or -0B01000000).toByte()
        data[pos + 1] = ((0B0000000_00000000_00000000_00111111 and glyph) or -0B10000000).toByte()
    }
    3 -> {
        data[pos] = ((0B0000000_00000000_11110000_00000000 and glyph shr 12) or -0B00100000).toByte()
        data[pos + 1] = ((0B0000000_00000000_00001111_11000000 and glyph shr 6) or -0B10000000).toByte()
        data[pos + 2] = ((0B0000000_00000000_00000000_00111111 and glyph) or -0B10000000).toByte()
    }
    4 -> {
        data[pos] = ((0B0000000_00011100_00000000_00000000 and glyph shr 18) or -0B00010000).toByte()
        data[pos + 1] = ((0B0000000_00000011_11110000_00000000 and glyph shr 12) or -0B10000000).toByte()
        data[pos + 2] = ((0B0000000_00000000_00001111_11000000 and glyph shr 6) or -0B10000000).toByte()
        data[pos + 3] = ((0B0000000_00000000_00000000_00111111 and glyph) or -0B10000000).toByte()
    }
    else -> error("Glyph illegal boundary detected. ${glyph.toString(16)}")
}

internal fun ByteArray.readGlyphAt(offset: Int, size: Int): Glyph = glyphRead(this, offset, size)

public fun ByteArray.readGlyphAt(offset: Int): Glyph = readGlyphAt(offset, this[offset].glyphSize())

internal fun ByteArray.writeGlyphAt(offset: Int, value: Glyph, size: Int): Unit = glyphWrite(this, offset, value, size)

public fun ByteArray.writeGlyphAt(offset: Int, value: Glyph): Unit = writeGlyphAt(offset, value, value.getSize())