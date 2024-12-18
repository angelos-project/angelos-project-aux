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
package org.angproj.aux.util

import kotlin.jvm.JvmInline


@JvmInline
public value class CodePoint(public val value: Int) {
    public fun isValid(): Boolean = value in UnicodeAware.GLYPH_RANGE && value !in UnicodeAware.GLYPH_HOLE

    public fun octetSize(): Int = when (value) {
        in UnicodeAware.GLYPH_HOLE -> -1
        in UnicodeAware.GLYPH_SIZE_1 -> 1
        in UnicodeAware.GLYPH_SIZE_2 -> 2
        in UnicodeAware.GLYPH_SIZE_3 -> 3
        in UnicodeAware.GLYPH_SIZE_4 -> 4
        else -> -1
    }
}

public fun Int.toCodePoint(): CodePoint = CodePoint(this)

public fun Char.toCodePoint(): CodePoint = CodePoint(this.code)


/**
 * https://www.ietf.org/rfc/rfc2279.txt
 * https://www.ietf.org/rfc/rfc3629.txt
 * https://en.wikipedia.org/wiki/UTF-8
 * */
public interface UnicodeAware {

    private inline fun <reified R : Any> req(remaining: Int, count: Int, block: () -> R): R {
        require(remaining >= count) { "Buffer overflow, limit reached." }
        return block()
    }

    private inline fun <reified R : Number> followDataLoop(loops: Int, codePoint: Int, readOctet: () -> Byte): Int {
        var value = codePoint
        var loop = loops
        while (loop-- > 0 && value != REPLACEMENT_CHARACTER) value = followDataRead<Int>(value, readOctet)
        return value
    }

    private inline fun <reified R : Number> followDataRead(codePoint: Int, readOctet: () -> Byte): Int {
        val octet = readOctet().toInt()
        return when {
            octet and 0B1100_0000 == 0B1000_0000 -> (codePoint shl 6) or (octet and 0B0011_1111)
            else -> return REPLACEMENT_CHARACTER
        }
    }

    private inline fun <reified R : Number> octetReadStrm(readOctet: () -> Byte): Int {
        val octet = readOctet().toInt()
        return when {
            octet and 0B1000_0000 == 0B0000_0000 -> octet and 0B0111_1111
            octet and 0B1110_0000 == 0B1100_0000 -> followDataRead<Int>(
                octet and 0B0001_1111, readOctet
            )
            octet and 0B1111_0000 == 0B1110_0000 -> followDataLoop<Int>(
                2, octet and 0B0000_1111, readOctet
            ) //SequenceType.START_THREE_LONG
            octet and 0B1111_1000 == 0B1111_0000 -> followDataLoop<Int>(
                3, octet and 0B0000_0111, readOctet
            ) //SequenceType.START_FOUR_LONG
            octet and 0B1111_1100 == 0B1111_1000 -> followDataLoop<Int>(
                4, octet and 0B0000_0011, readOctet
            ) //SequenceType.START_FIVE_LONG // Just to deal with the illegal sequence
            octet and 0B1111_1110 == 0B1111_1100 -> followDataLoop<Int>(
                5, octet and 0B0000_0001, readOctet
            ) //SequenceType.START_SIX_LONG // Just to deal with the illegal sequence
            else -> REPLACEMENT_CHARACTER
        }
    }

    private inline fun <reified R : Number> octetReadBlk(remaining: Int, readOctet: () -> Byte): Int {
        val octet = readOctet().toInt()
        return when {
            octet and 0B1000_0000 == 0B0000_0000 -> octet and 0B0111_1111
            octet and 0B1110_0000 == 0B1100_0000 -> req(remaining, 2) {
                followDataRead<Int>(octet and 0B0001_1111, readOctet)
            }
            octet and 0B1111_0000 == 0B1110_0000 -> req(remaining, 3) {
                followDataLoop<Int>(2, octet and 0B0000_1111, readOctet)
            } //SequenceType.START_THREE_LONG
            octet and 0B1111_1000 == 0B1111_0000 -> req(remaining, 4) {
                followDataLoop<Int>(3, octet and 0B0000_0111, readOctet)
            } //SequenceType.START_FOUR_LONG
            octet and 0B1111_1100 == 0B1111_1000 -> req(remaining, 5) {
                followDataLoop<Int>(4, octet and 0B0000_0011, readOctet)
            } //SequenceType.START_FIVE_LONG // Just to deal with the illegal sequence
            octet and 0B1111_1110 == 0B1111_1100 -> req(remaining, 6) {
                followDataLoop<Int>(5, octet and 0B0000_0001, readOctet)
            } //SequenceType.START_SIX_LONG // Just to deal with the illegal sequence
            else -> REPLACEMENT_CHARACTER
        }
    }

    public fun readGlyphStrm(readOctet: () -> Byte): CodePoint {
        val value = octetReadStrm<Int>(readOctet)
        return CodePoint(value)
    }

    public fun readGlyphBlk(remaining: Int, readOctet: () -> Byte): CodePoint {
        val value = req(remaining, 1) { octetReadBlk<Int>(remaining, readOctet) }
        return CodePoint(value)
    }

    private inline fun <reified R : Number> escapeInvalid(codePoint: Int): Int =
        when (codePoint in GLYPH_RANGE && codePoint !in GLYPH_HOLE) {
            false -> REPLACEMENT_CHARACTER
            else -> codePoint
        }

    private inline fun <reified R : Number> octetWrite1(codePoint: Int, writeOctet: (octet: Byte) -> Unit): Int {
        writeOctet((0B0000000_00000000_00000000_01111111 and codePoint).toByte())
        return 1
    }

    private inline fun <reified R : Number> octetWrite2(codePoint: Int, writeOctet: (octet: Byte) -> Unit): Int {
        writeOctet(((0B0000000_00000000_00000111_11000000 and codePoint shr 6) or -0B01000000).toByte())
        writeOctet(((0B0000000_00000000_00000000_00111111 and codePoint) or -0B10000000).toByte())
        return 2
    }

    private inline fun <reified R : Number> octetWrite3(codePoint: Int, writeOctet: (octet: Byte) -> Unit): Int {
        writeOctet(((0B0000000_00000000_11110000_00000000 and codePoint shr 12) or -0B00100000).toByte())
        writeOctet(((0B0000000_00000000_00001111_11000000 and codePoint shr 6) or -0B10000000).toByte())
        writeOctet(((0B0000000_00000000_00000000_00111111 and codePoint) or -0B10000000).toByte())
        return 3
    }

    private inline fun <reified R : Number> octetWrite4(codePoint: Int, writeOctet: (octet: Byte) -> Unit): Int {
        writeOctet(((0B0000000_00011100_00000000_00000000 and codePoint shr 18) or -0B00010000).toByte())
        writeOctet(((0B0000000_00000011_11110000_00000000 and codePoint shr 12) or -0B10000000).toByte())
        writeOctet(((0B0000000_00000000_00001111_11000000 and codePoint shr 6) or -0B10000000).toByte())
        writeOctet(((0B0000000_00000000_00000000_00111111 and codePoint) or -0B10000000).toByte())
        return 4
    }

    private inline fun <reified R : Number> octetWriteStrm(codePoint: Int, writeOctet: (octet: Byte) -> Unit): Int {
        return when (val value = escapeInvalid<Int>(codePoint)) {
            in GLYPH_SIZE_1 -> octetWrite1<Int>(value, writeOctet)
            in GLYPH_SIZE_2 -> octetWrite2<Int>(value, writeOctet)
            in GLYPH_SIZE_3 -> octetWrite3<Int>(value, writeOctet)
            in GLYPH_SIZE_4 -> octetWrite4<Int>(value, writeOctet)
            else -> 0
        }
    }

    private inline fun <reified R : Number> octetWriteBlk(
        codePoint: Int,
        remaining: Int,
        writeOctet: (octet: Byte) -> Unit
    ): Int {
        return when (val value = escapeInvalid<Int>(codePoint)) {
            in GLYPH_SIZE_1 -> req(remaining, 1) { octetWrite1<Int>(value, writeOctet) }
            in GLYPH_SIZE_2 -> req(remaining, 2) { octetWrite2<Int>(value, writeOctet) }
            in GLYPH_SIZE_3 -> req(remaining, 3) { octetWrite3<Int>(value, writeOctet) }
            in GLYPH_SIZE_4 -> req(remaining, 4) { octetWrite4<Int>(value, writeOctet) }
            else -> 0
        }
    }

    public fun writeGlyphStrm(codePoint: CodePoint, writeOctet: (octet: Byte) -> Unit): Int {
        return octetWriteStrm<Int>(codePoint.value, writeOctet)
    }

    public fun writeGlyphBlk(codePoint: CodePoint, remaining: Int, writeOctet: (octet: Byte) -> Unit): Int {
        return octetWriteBlk<Int>(codePoint.value, remaining, writeOctet)
    }

    public fun isGlyphStart(octet: Byte): Boolean {
        val value = octet.toInt()
        return when {
            value and 0B1000_0000 == 0B0000_0000 ||
            value and 0B1110_0000 == 0B1100_0000 ||
            value and 0B1111_0000 == 0B1110_0000 ||
            value and 0B1111_1000 == 0B1111_0000 ||
            value and 0B1111_1100 == 0B1111_1000 ||
            value and 0B1111_1110 == 0B1111_1100 -> true
            else -> false
        }
    }

    public fun hasGlyphSize(octet: Byte): Int {
        val value = octet.toInt()
        return when {
            value and 0B1000_0000 == 0B0000_0000 -> 1
            value and 0B1110_0000 == 0B1100_0000 -> 2
            value and 0B1111_0000 == 0B1110_0000 -> 3
            value and 0B1111_1000 == 0B1111_0000 -> 4
            value and 0B1111_1100 == 0B1111_1000 -> 5
            value and 0B1111_1110 == 0B1111_1100 -> 6
            else -> 0
        }
    }

    public companion object {
        public const val REPLACEMENT_CHARACTER: Int = 0xFFFD

        private const val GLYPH_MAX_VALUE: Int = 0x10_FFFF
        private const val GLYPH_MIN_VALUE: Int = 0x0
        public val GLYPH_RANGE: IntRange = GLYPH_MIN_VALUE..GLYPH_MAX_VALUE
        public val GLYPH_HOLE: IntRange = 0xD800..0xDFFF
        public val GLYPH_SIZE_1: IntRange = GLYPH_MIN_VALUE..0x7F
        public val GLYPH_SIZE_2: IntRange = 0x80..0x7FF
        public val GLYPH_SIZE_3: IntRange = 0x800..0xFFFF
        public val GLYPH_SIZE_4: IntRange = 0x1_0000..GLYPH_MAX_VALUE
    }
}


public object UnicodeAwareContext : UnicodeAware

public inline fun <reified T> withUnicodeAware(block: UnicodeAwareContext.() -> T): T = UnicodeAwareContext.block()

public inline fun <reified T> withUnicodeAware(
    array: ByteArray, block: UnicodeAwareContext.(array: ByteArray) -> T
): T = UnicodeAwareContext.block(array)

