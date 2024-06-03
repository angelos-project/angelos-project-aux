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

import org.angproj.aux.util.Reifiable
import org.angproj.aux.util.Reify

/**
 * https://www.ietf.org/rfc/rfc2279.txt
 * https://www.ietf.org/rfc/rfc3629.txt
 * https://en.wikipedia.org/wiki/UTF-8
 * */
public object Glyph {
    public const val GLYPH_MAX_VALUE: Int = 0x10_FFFF
    public const val GLYPH_MIN_VALUE: Int = 0x0

    public val GLYPH_RANGE: IntRange = GLYPH_MIN_VALUE..GLYPH_MAX_VALUE
    public val GLYPH_HOLE: IntRange = 0xD800..0xDFFF
    public val GLYPH_SIZE_1: IntRange = GLYPH_MIN_VALUE..0x7F
    public val GLYPH_SIZE_2: IntRange = 0x80..0x7FF
    public val GLYPH_SIZE_3: IntRange = 0x800..0xFFFF
    public val GLYPH_SIZE_4: IntRange = 0x1_0000..GLYPH_MAX_VALUE

    public val NEWLINE_CHARACTER: CodePoint = CodePoint('\n'.code)
    public val REPLACEMENT_CHARACTER: CodePoint = CodePoint(0xFFFD)

    internal inline fun <reified : Reifiable>readStart(readOctet: () -> Byte): CodePoint {
        val octet = readOctet()
        return when(val seqType = SequenceType.qualify(octet)) {
            SequenceType.FOLLOW_DATA, SequenceType.ILLEGAL -> REPLACEMENT_CHARACTER
            else -> readFollowData<Reify>(seqType, SequenceType.extract(seqType, octet), readOctet)
        }
    }

    internal inline fun <reified : Reifiable> readFollowData(
        seqType: SequenceType, codePoint: Int, readOctet: () -> Byte): CodePoint {
        var value = codePoint
        repeat(seqType.size - 1) {
            val octet = readOctet()
            if(SequenceType.qualify(octet) != SequenceType.FOLLOW_DATA) return REPLACEMENT_CHARACTER
            value = (value shl seqType.bits) or (SequenceType.extract(SequenceType.FOLLOW_DATA, octet))
        }
        return CodePoint(value)
    }

    internal inline fun <reified : Reifiable>startOneOctetOf(codePoint: CodePoint): Byte = (
            0B0000000_00000000_00000000_01111111 and codePoint.value).toByte()

    internal inline fun <reified : Reifiable>startTwoOctetOf(codePoint: CodePoint): Byte = (
            (0B0000000_00000000_00000111_11000000 and codePoint.value shr 6) or -0B01000000).toByte()

    internal inline fun <reified : Reifiable>startThreeOctetOf(codePoint: CodePoint): Byte = (
            (0B0000000_00000000_11110000_00000000 and codePoint.value shr 12) or -0B00100000).toByte()

    internal inline fun <reified : Reifiable>startFourOctetOf(codePoint: CodePoint): Byte = (
            (0B0000000_00011100_00000000_00000000 and codePoint.value shr 18) or -0B00010000).toByte()

    public fun startOctetOf(sequenceType: SequenceType, codePoint: CodePoint): Byte = when(sequenceType){
        SequenceType.START_ONE_LONG -> startOneOctetOf<Reify>(codePoint)
        SequenceType.START_TWO_LONG -> startTwoOctetOf<Reify>(codePoint)
        SequenceType.START_THREE_LONG -> startThreeOctetOf<Reify>(codePoint)
        SequenceType.START_FOUR_LONG -> startFourOctetOf<Reify>(codePoint)
        else -> error ("Unsupported start data.")
    }

    internal inline fun <reified : Reifiable>followThreeUpOctetOf(codePoint: CodePoint): Byte = (
            (0B0000000_00000011_11110000_00000000 and codePoint.value shr 12) or -0B10000000).toByte()

    internal inline fun <reified : Reifiable>followTwoUpOctetOf(codePoint: CodePoint): Byte = (
            (0B0000000_00000000_00001111_11000000 and codePoint.value shr 6) or -0B10000000).toByte()

    internal inline fun <reified : Reifiable>followLastOctetOf(codePoint: CodePoint): Byte = (
            (0B0000000_00000000_00000000_00111111 and codePoint.value) or -0B10000000).toByte()

    public fun followOctetOf(sequenceType: SequenceType, codePoint: CodePoint, index: Int): Byte {
        return when(sequenceType.size - index) {
            1 -> followLastOctetOf<Reify>(codePoint)
            2 -> followTwoUpOctetOf<Reify>(codePoint)
            3 -> followThreeUpOctetOf<Reify>(codePoint)
            else -> error("Unsupported follow data.")
        }
    }
}

/**
 * Takes a UTF-8 leading octet as a Byte and check what size the multibyte character has.
 * Also works as validation of the first octet in a UTF-8 binary octet sequence.
 */
public fun Byte.sequenceTypeOf(): SequenceType = SequenceType.qualify(this)

public fun ByteArray.readGlyphAt(offset: Int): CodePoint {
    var pos = offset
    return Glyph.readStart<Reify> { this[pos].also { pos++ } }
}

public fun ByteArray.writeGlyphAt(offset: Int, value: CodePoint): Int = value.sectionTypeOf().also{ secType ->
    this[offset] = Glyph.startOctetOf(secType, value)
    (1 until secType.size).forEach{ this[offset + it] = Glyph.followOctetOf(secType, value, it) }
}.size