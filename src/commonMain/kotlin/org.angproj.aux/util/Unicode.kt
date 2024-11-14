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

import org.angproj.aux.codec.Decoder
import org.angproj.aux.codec.Encoder


public object Unicode /*: Encoder<ByteArray, String>, Decoder<String, ByteArray>*/ {

    private inline fun <reified R : Number> isSurrogate(char: Char): Boolean = char.code in SURROGATE_RANGE

    private inline fun <reified R : Number> isHighSurrogate(char: Char): Boolean = char.code in SURROGATE_HIGH_RANGE

    private inline fun <reified R : Number> isLowSurrogate(char: Char): Boolean = char.code in SURROGATE_LOW_RANGE

    private inline fun <reified R : Number> isSurrogatePair(high: Char, low: Char): Boolean = isHighSurrogate<Int>(high) && isLowSurrogate<Int>(low)

    private inline fun <reified R : Number> toCodePoint(high: Char, low: Char): Int = (((high.code - MIN_HIGH_SURROGATE) shl 10) or (low.code - MIN_LOW_SURROGATE)) + 0x10000

    private inline fun <reified R : Number> hasSurrogatePairAt(seq: CharSequence, index: Int): Boolean = index in 0..seq.length - 2
            && isSurrogatePair<Int>(seq[index], seq[index + 1])

    internal fun importUnicode(str: String, writeGlyph: (it: CodePoint) -> Unit) {
        var idx = 0
        while(idx < str.length) when {
            !isSurrogate<Int>(str[idx]) -> {
                writeGlyph(str[idx].code.toCodePoint())
                idx++
            }
            hasSurrogatePairAt<Int>(str, idx) -> {
                writeGlyph(toCodePoint<Int>(str[idx], str[idx+1]).toCodePoint())
                idx += 2
            }
            else -> error("Illegal codepoint.")
        }
    }

    internal fun importByteSize(str: String): Int {
        var idx = 0
        var size = 0
        while(idx < str.length) when {
            !isSurrogate<Int>(str[idx]) -> {
                size += str[idx].code.toCodePoint().octetSize()
                idx++
            }
            hasSurrogatePairAt<Int>(str, idx) -> {
                size += toCodePoint<Int>(str[idx], str[idx+1]).toCodePoint().octetSize()
                idx += 2
            }
            else -> error("Illegal codepoint.")
        }
        return size
    }

    public const val MIN_HIGH_SURROGATE: Int = 0xD800
    public const val MAX_HIGH_SURROGATE: Int = 0xDBFF
    public const val MIN_LOW_SURROGATE: Int = 0xDC00
    public const val MAX_LOW_SURROGATE: Int = 0xDFFF
    public const val MIN_SURROGATE: Int = MIN_HIGH_SURROGATE
    public const val MAX_SURROGATE: Int = MAX_LOW_SURROGATE
    public val SURROGATE_RANGE: IntRange= MIN_SURROGATE..MAX_SURROGATE
    public val SURROGATE_LOW_RANGE: IntRange = MIN_LOW_SURROGATE..MAX_LOW_SURROGATE
    public val SURROGATE_HIGH_RANGE: IntRange= MIN_SURROGATE..MAX_SURROGATE

    /*public fun sanitizeWithEscaping(data: ByteArray): ByteArray {
        val fsm = Sanitizer.create()
        fsm.state = Sanitizer.SIX_COMPLETED
    }*/

    /*override fun decode(data: String): ByteArray {
        TODO("Not yet implemented")
    }

    override fun encode(data: ByteArray): String {
        TODO("Not yet implemented")
    }*/
}