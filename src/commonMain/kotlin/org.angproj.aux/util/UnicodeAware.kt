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

import org.angproj.aux.utf.CodePoint

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

    private inline fun <reified R : Number> octetRead(readOctet: () -> Byte): Int {
        val octet = readOctet().toInt()
        return when {
            octet and 0B1000_0000 == 0B0000_0000 -> octet and 0B0111_1111
            octet and 0B1110_0000 == 0B1100_0000 -> followDataRead<Int>(octet and 0B0001_1111, readOctet)
            octet and 0B1111_0000 == 0B1110_0000 -> followDataLoop<Int>(
                2,
                octet and 0B0000_1111,
                readOctet
            ) //SequenceType.START_THREE_LONG
            octet and 0B1111_1000 == 0B1111_0000 -> followDataLoop<Int>(
                3,
                octet and 0B0000_0111,
                readOctet
            ) //SequenceType.START_FOUR_LONG
            octet and 0B1111_1100 == 0B1111_1000 -> followDataLoop<Int>(
                4,
                octet and 0B0000_0011,
                readOctet
            ) //SequenceType.START_FIVE_LONG // Just to deal with the illegal sequence
            octet and 0B1111_1110 == 0B1111_1100 -> followDataLoop<Int>(
                5,
                octet and 0B0000_0001,
                readOctet
            ) //SequenceType.START_SIX_LONG // Just to deal with the illegal sequence
            else -> REPLACEMENT_CHARACTER
        }
    }

    private inline fun <reified R : Number> octetReadSafe(remaining: Int, readOctet: () -> Byte): Int {
        val octet = readOctet().toInt()
        return when {
            octet and 0B1000_0000 == 0B0000_0000 -> octet and 0B0111_1111
            octet and 0B1110_0000 == 0B1100_0000 -> req(remaining, 2) {
                followDataRead<Int>(
                    octet and 0B0001_1111,
                    readOctet
                )
            }

            octet and 0B1111_0000 == 0B1110_0000 -> req(remaining, 3) {
                followDataLoop<Int>(
                    2,
                    octet and 0B0000_1111,
                    readOctet
                )
            } //SequenceType.START_THREE_LONG
            octet and 0B1111_1000 == 0B1111_0000 -> req(remaining, 4) {
                followDataLoop<Int>(
                    3,
                    octet and 0B0000_0111,
                    readOctet
                )
            } //SequenceType.START_FOUR_LONG
            octet and 0B1111_1100 == 0B1111_1000 -> req(remaining, 5) {
                followDataLoop<Int>(
                    4,
                    octet and 0B0000_0011,
                    readOctet
                )
            } //SequenceType.START_FIVE_LONG // Just to deal with the illegal sequence
            octet and 0B1111_1110 == 0B1111_1100 -> req(remaining, 6) {
                followDataLoop<Int>(
                    5,
                    octet and 0B0000_0001,
                    readOctet
                )
            } //SequenceType.START_SIX_LONG // Just to deal with the illegal sequence
            else -> REPLACEMENT_CHARACTER
        }
    }

    public fun readGlyph(readOctet: () -> Byte): CodePoint {
        val value = octetRead<Int>(readOctet)
        return CodePoint(value)
    }

    public fun readGlyphSafe(remaining: Int, readOctet: () -> Byte): CodePoint {
        val value = req(remaining, 1) { octetReadSafe<Int>(remaining, readOctet) }
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

    private inline fun <reified R : Number> octetWrite(codePoint: Int, writeOctet: (octet: Byte) -> Unit): Int {
        return when (codePoint) {
            in GLYPH_HOLE -> 0
            in GLYPH_SIZE_1 -> octetWrite1<Int>(codePoint, writeOctet)
            in GLYPH_SIZE_2 -> octetWrite2<Int>(codePoint, writeOctet)
            in GLYPH_SIZE_3 -> octetWrite3<Int>(codePoint, writeOctet)
            in GLYPH_SIZE_4 -> octetWrite4<Int>(codePoint, writeOctet)
            else -> 0
        }
    }

    private inline fun <reified R : Number> octetWriteSafe(
        codePoint: Int,
        remaining: Int,
        writeOctet: (octet: Byte) -> Unit
    ): Int {
        val value = escapeInvalid<Int>(codePoint)
        return when (value) {
            in GLYPH_HOLE -> 0
            in GLYPH_SIZE_1 -> req(remaining, 1) { octetWrite1<Int>(value, writeOctet) }
            in GLYPH_SIZE_2 -> req(remaining, 2) { octetWrite2<Int>(value, writeOctet) }
            in GLYPH_SIZE_3 -> req(remaining, 3) { octetWrite3<Int>(value, writeOctet) }
            in GLYPH_SIZE_4 -> req(remaining, 4) { octetWrite4<Int>(value, writeOctet) }
            else -> 0
        }
    }


    public fun writeGlyph(codePoint: CodePoint, writeOctet: (octet: Byte) -> Unit): Int {
        return octetWrite<Int>(codePoint.value, writeOctet)
    }

    public fun writeGlyphSafe(codePoint: CodePoint, remaining: Int, writeOctet: (octet: Byte) -> Unit): Int {
        return octetWriteSafe<Int>(codePoint.value, remaining, writeOctet)
    }

    private companion object {
        const val REPLACEMENT_CHARACTER: Int = 0xFFFD

        private const val GLYPH_MAX_VALUE: Int = 0x10_FFFF
        private const val GLYPH_MIN_VALUE: Int = 0x0
        val GLYPH_RANGE: IntRange = GLYPH_MIN_VALUE..GLYPH_MAX_VALUE
        val GLYPH_HOLE: IntRange = 0xD800..0xDFFF
        val GLYPH_SIZE_1: IntRange = GLYPH_MIN_VALUE..0x7F
        val GLYPH_SIZE_2: IntRange = 0x80..0x7FF
        val GLYPH_SIZE_3: IntRange = 0x800..0xFFFF
        val GLYPH_SIZE_4: IntRange = 0x1_0000..GLYPH_MAX_VALUE
    }
}


public object UnicodeAwareContext : UnicodeAware

public inline fun <reified T> withUnicodeAware(block: UnicodeAwareContext.() -> T): T = UnicodeAwareContext.block()

public inline fun <reified T> withUnicodeAware(
    array: ByteArray, block: UnicodeAwareContext.(array: ByteArray) -> T
): T = UnicodeAwareContext.block(array)