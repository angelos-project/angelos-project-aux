/**
 * Copyright (c) 2024-2025 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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
package org.angproj.aux.util


public abstract class AbstractUnicodeAware {

    protected inline fun <reified R : Any> isSurrogate(char: Char): Boolean = char.code in SURROGATE_RANGE
    protected inline fun <reified R : Any> isHighSurrogate(char: Char): Boolean = char.code in SURROGATE_HIGH_RANGE
    protected inline fun <reified R : Any> isLowSurrogate(char: Char): Boolean = char.code in SURROGATE_LOW_RANGE
    protected inline fun <reified R : Any> isSurrogatePair(
        high: Char, low: Char
    ): Boolean = isHighSurrogate<Int>(high) && isLowSurrogate<Int>(low)

    protected inline fun <reified R : Any> surrogatesToCodePoint(
        high: Char, low: Char
    ): Int = (((high.code - MIN_HIGH_SURROGATE) shl 10) or (low.code - MIN_LOW_SURROGATE)) + 0x10000

    protected inline fun <reified R : Any> hasSurrogatePairAt(
        seq: CharSequence, index: Int
    ): Boolean = index in 0..seq.length - 2 && isSurrogatePair<Int>(seq[index], seq[index + 1])

    public val MIN_HIGH_SURROGATE: Int = 0xD800
    public val MAX_HIGH_SURROGATE: Int = 0xDBFF
    public val MIN_LOW_SURROGATE: Int = 0xDC00
    public val MAX_LOW_SURROGATE: Int = 0xDFFF
    public val MIN_SURROGATE: Int = MIN_HIGH_SURROGATE
    public val MAX_SURROGATE: Int = MAX_LOW_SURROGATE
    public val SURROGATE_RANGE: IntRange= MIN_SURROGATE..MAX_SURROGATE
    public val SURROGATE_LOW_RANGE: IntRange = MIN_LOW_SURROGATE..MAX_LOW_SURROGATE
    public val SURROGATE_HIGH_RANGE: IntRange= MIN_SURROGATE..MAX_SURROGATE


    protected inline fun <reified R : Any> req(remaining: Int, count: Int, block: () -> R): R {
        check(remaining >= count) { "Buffer overflow, limit reached." } // Should be check (IllegalStateException)
        return block()
    }

    protected inline fun <reified R : Any> followDataLoop(loops: Int, codePoint: Int, readOctet: () -> Byte): Int {
        var value = codePoint
        var loop = loops
        while (loop-- > 0 && value != REPLACEMENT_CHARACTER) value = followDataRead<Unit>(value, readOctet)
        return value
    }

    protected inline fun <reified R : Any> followDataRead(codePoint: Int, readOctet: () -> Byte): Int {
        val octet = readOctet().toInt()
        return when {
            isFollowingOctet<Unit>(octet) -> (codePoint shl 6) or (octet and 0B0011_1111)
            else -> return REPLACEMENT_CHARACTER
        }
    }

    protected inline fun <reified R : Any> isSingleOctet(octet: Int): Boolean = octet and 0B1000_0000 == 0B0000_0000
    protected inline fun <reified R : Any> isFirstOctetOfTwo(octet: Int): Boolean = octet and 0B1110_0000 == 0B1100_0000
    protected inline fun <reified R : Any> isFirstOctetOfThree(octet: Int): Boolean = octet and 0B1111_0000 == 0B1110_0000
    protected inline fun <reified R : Any> isFirstOctetOfFour(octet: Int): Boolean = octet and 0B1111_1000 == 0B1111_0000
    protected inline fun <reified R : Any> isFirstOctetOfFive(octet: Int): Boolean = octet and 0B1111_1100 == 0B1111_1000
    protected inline fun <reified R : Any> isFirstOctetOfSix(octet: Int): Boolean = octet and 0B1111_1110 == 0B1111_1100
    protected inline fun <reified R : Any> isFollowingOctet(octet: Int): Boolean = octet and 0B1100_0000 == 0B1000_0000

    protected inline fun <reified R : Any> valueOfSingle(octet: Int): Int = octet and 0B0111_1111
    protected inline fun <reified R : Any> valueOfFirstOfTwo(octet: Int): Int = octet and 0B0001_1111
    protected inline fun <reified R : Any> valueOfFirstOfThree(octet: Int): Int = octet and 0B0000_1111
    protected inline fun <reified R : Any> valueOfFirstOfFour(octet: Int): Int = octet and 0B0000_0111
    protected inline fun <reified R : Any> valueOfFirstOfFive(octet: Int): Int = octet and 0B0000_0011
    protected inline fun <reified R : Any> valueOfFirstOfSix(octet: Int): Int = octet and 0B0000_0001
    protected inline fun <reified R : Any> valueOfFollowingOctet(octet: Int): Boolean = octet and 0B1100_0000 == 0B1000_0000

    protected inline fun <reified R : Any> octetReadStrm(readOctet: () -> Byte): Int {
        val octet = readOctet().toInt()
        return when {
            isSingleOctet<Unit>(octet) -> valueOfSingle<Unit>(octet)
            isFirstOctetOfTwo<Unit>(octet) -> followDataRead<Unit>(valueOfFirstOfTwo<Unit>(octet), readOctet)
            isFirstOctetOfThree<Unit>(octet) -> followDataLoop<Unit>(2, valueOfFirstOfThree<Unit>(octet), readOctet)
            isFirstOctetOfFour<Unit>(octet) -> followDataLoop<Unit>(3, valueOfFirstOfFour<Unit>(octet), readOctet)
            isFirstOctetOfFive<Unit>(octet) -> followDataLoop<Unit>(4, valueOfFirstOfFive<Unit>(octet), readOctet)
            isFirstOctetOfSix<Unit>(octet) -> followDataLoop<Unit>(5, valueOfFirstOfSix<Unit>(octet), readOctet)
            else -> REPLACEMENT_CHARACTER
        }
    }

    protected inline fun <reified R : Any> octetReadBlk(remaining: Int, readOctet: () -> Byte): Int {
        val octet = readOctet().toInt()
        return when {
            isSingleOctet<Unit>(octet) -> valueOfSingle<Unit>(octet)
            isFirstOctetOfTwo<Unit>(octet) -> req(remaining, 2) {
                followDataRead<Unit>(valueOfFirstOfTwo<Unit>(octet), readOctet) }
            isFirstOctetOfThree<Unit>(octet) -> req(remaining, 3) {
                followDataLoop<Unit>(2, valueOfFirstOfThree<Unit>(octet), readOctet) }
            isFirstOctetOfFour<Unit>(octet)-> req(remaining, 4) {
                followDataLoop<Unit>(3, valueOfFirstOfFour<Unit>(octet), readOctet) }
            isFirstOctetOfFive<Unit>(octet) -> req(remaining, 5) {
                followDataLoop<Unit>(4, valueOfFirstOfFive<Unit>(octet), readOctet) }
            isFirstOctetOfSix<Unit>(octet) -> req(remaining, 6) {
                followDataLoop<Unit>(5, valueOfFirstOfSix<Unit>(octet), readOctet) }
            else -> REPLACEMENT_CHARACTER
        }
    }

    protected inline fun <reified R : Any> escapeInvalid(codePoint: Int): Int =
        when (codePoint in GLYPH_RANGE && codePoint !in GLYPH_HOLE) {
            true -> codePoint
            else -> REPLACEMENT_CHARACTER
        }

    protected inline fun <reified R : Any> extractOctet0(codePoint: Int): Byte = (
            (0B0000000_00000000_00000000_00111111 and codePoint) or -0B10000000).toByte()
    protected inline fun <reified R : Any> extractOctet1(codePoint: Int): Byte = (
            (0B0000000_00000000_00001111_11000000 and codePoint shr 6) or -0B10000000).toByte()
    protected inline fun <reified R : Any> extractOctet2(codePoint: Int): Byte = (
            (0B0000000_00000011_11110000_00000000 and codePoint shr 12) or -0B10000000).toByte()

    protected inline fun <reified R : Any> octetWrite1(codePoint: Int, writeOctet: (octet: Byte) -> Unit): Int {
        writeOctet((0B0000000_00000000_00000000_01111111 and codePoint).toByte())
        return 1
    }

    protected inline fun <reified R : Any> octetWrite2(codePoint: Int, writeOctet: (octet: Byte) -> Unit): Int {
        writeOctet(((0B0000000_00000000_00000111_11000000 and codePoint shr 6) or -0B01000000).toByte())
        writeOctet(extractOctet0<Unit>(codePoint))
        return 2
    }

    protected inline fun <reified R : Any> octetWrite3(codePoint: Int, writeOctet: (octet: Byte) -> Unit): Int {
        writeOctet(((0B0000000_00000000_11110000_00000000 and codePoint shr 12) or -0B00100000).toByte())
        writeOctet(extractOctet1<Unit>(codePoint))
        writeOctet(extractOctet0<Unit>(codePoint))
        return 3
    }

    protected inline fun <reified R : Any> octetWrite4(codePoint: Int, writeOctet: (octet: Byte) -> Unit): Int {
        writeOctet(((0B0000000_00011100_00000000_00000000 and codePoint shr 18) or -0B00010000).toByte())
        writeOctet(extractOctet2<Unit>(codePoint))
        writeOctet(extractOctet1<Unit>(codePoint))
        writeOctet(extractOctet0<Unit>(codePoint))
        return 4
    }

    protected inline fun <reified R : Any> octetWriteStrm(codePoint: Int, writeOctet: (octet: Byte) -> Unit): Int {
        return when (val value = escapeInvalid<Unit>(codePoint)) {
            in GLYPH_SIZE_1 -> octetWrite1<Unit>(value, writeOctet)
            in GLYPH_SIZE_2 -> octetWrite2<Unit>(value, writeOctet)
            in GLYPH_SIZE_3 -> octetWrite3<Unit>(value, writeOctet)
            in GLYPH_SIZE_4 -> octetWrite4<Unit>(value, writeOctet)
            else -> 0
        }
    }

    protected inline fun <reified R : Any> octetWriteBlk(
        codePoint: Int,
        remaining: Int,
        writeOctet: (octet: Byte) -> Unit
    ): Int {
        return when (val value = escapeInvalid<Int>(codePoint)) {
            in GLYPH_SIZE_1 -> req(remaining, 1) { octetWrite1<Unit>(value, writeOctet) }
            in GLYPH_SIZE_2 -> req(remaining, 2) { octetWrite2<Unit>(value, writeOctet) }
            in GLYPH_SIZE_3 -> req(remaining, 3) { octetWrite3<Unit>(value, writeOctet) }
            in GLYPH_SIZE_4 -> req(remaining, 4) { octetWrite4<Unit>(value, writeOctet) }
            else -> 0
        }
    }

    public val REPLACEMENT_CHARACTER: Int = 0xFFFD

    private val GLYPH_MAX_VALUE: Int = 0x10_FFFF
    private val GLYPH_MIN_VALUE: Int = 0x0
    public val GLYPH_RANGE: IntRange = GLYPH_MIN_VALUE..GLYPH_MAX_VALUE
    public val GLYPH_HOLE: IntRange = 0xD800..0xDFFF
    public val GLYPH_SIZE_1: IntRange = GLYPH_MIN_VALUE..0x7F
    public val GLYPH_SIZE_2: IntRange = 0x80..0x7FF
    public val GLYPH_SIZE_3: IntRange = 0x800..0xFFFF
    public val GLYPH_SIZE_4: IntRange = 0x1_0000..GLYPH_MAX_VALUE
}