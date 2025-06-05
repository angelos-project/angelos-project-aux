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

import kotlin.jvm.JvmInline


@JvmInline
public value class CodePoint(public val value: Int): UnicodeAware {
    public fun isValid(): Boolean = isValid(value)

    public fun octetSize(): Int = octetSize(value)
}

public fun Int.toCodePoint(): CodePoint = CodePoint(this)

public fun Char.toCodePoint(): CodePoint = CodePoint(this.code)


/**
 * https://www.ietf.org/rfc/rfc2279.txt
 * https://www.ietf.org/rfc/rfc3629.txt
 * https://en.wikipedia.org/wiki/UTF-8
 * */
public interface UnicodeAware {

    public fun readGlyphStrm(readOctet: () -> Byte): CodePoint {
        val value = octetReadStrm<Unit>(readOctet)
        return CodePoint(value)
    }

    public fun readGlyphBlk(remaining: Int, readOctet: () -> Byte): CodePoint {
        val value = req(remaining, 1) { octetReadBlk<Unit>(remaining, readOctet) }
        return CodePoint(value)
    }

    public fun writeGlyphStrm(
        codePoint: CodePoint, writeOctet: (octet: Byte) -> Unit
    ): Int = octetWriteStrm<Unit>(codePoint.value, writeOctet)


    public fun writeGlyphBlk(
        codePoint: CodePoint, remaining: Int, writeOctet: (octet: Byte) -> Unit
    ): Int = octetWriteBlk<Unit>(codePoint.value, remaining, writeOctet)

    public fun isGlyphStart(octet: Byte): Boolean {
        val value = octet.toInt()
        return when {
            isSingleOctet<Unit>(value) ||
            isFirstOctetOfTwo<Unit>(value) ||
            isFirstOctetOfThree<Unit>(value) ||
            isFirstOctetOfFour<Unit>(value) ||
            isFirstOctetOfFive<Unit>(value) ||
            isFirstOctetOfSix<Unit>(value) -> true
            else -> false
        }
    }

    public fun hasGlyphSize(octet: Byte): Int {
        val value = octet.toInt()
        return when {
            isSingleOctet<Unit>(value) -> 1
            isFirstOctetOfTwo<Unit>(value) -> 2
            isFirstOctetOfThree<Unit>(value) -> 3
            isFirstOctetOfFour<Unit>(value) -> 4
            isFirstOctetOfFive<Unit>(value) -> 5
            isFirstOctetOfSix<Unit>(value) -> 6
            else -> 0
        }
    }

    public fun isValid(value: Int): Boolean = value in GLYPH_RANGE && value !in GLYPH_HOLE

    public fun octetSize(value: Int): Int = when (value) {
        in GLYPH_HOLE -> -1
        in GLYPH_SIZE_1 -> 1
        in GLYPH_SIZE_2 -> 2
        in GLYPH_SIZE_3 -> 3
        in GLYPH_SIZE_4 -> 4
        else -> -1
    }

    public fun importUnicode(str: String, writeGlyph: (it: CodePoint) -> Unit) {
        var idx = 0
        while(idx < str.length) when {
            !isSurrogate<Unit>(str[idx]) -> {
                writeGlyph(str[idx].code.toCodePoint())
                idx++
            }
            hasSurrogatePairAt<Unit>(str, idx) -> {
                writeGlyph(surrogatesToCodePoint<Unit>(str[idx], str[idx + 1]).toCodePoint())
                idx += 2
            }
            else -> error("Illegal codepoint.")
        }
    }

    public fun importByteSize(str: String): Int {
        var idx = 0
        var size = 0
        while(idx < str.length) when {
            !isSurrogate<Unit>(str[idx]) -> {
                size += octetSize(str[idx].code)
                idx++
            }
            hasSurrogatePairAt<Unit>(str, idx) -> {
                size += octetSize(surrogatesToCodePoint<Unit>(str[idx], str[idx + 1]))
                idx += 2
            }
            else -> error("Illegal codepoint.")
        }
        return size
    }

    public companion object: AbstractUnicodeAware()
}


public object UnicodeAwareContext : UnicodeAware

public inline fun <reified T> withUnicode(block: UnicodeAwareContext.() -> T): T = UnicodeAwareContext.block()

public inline fun <reified T> withUnicode(
    array: ByteArray, block: UnicodeAwareContext.(array: ByteArray) -> T
): T = UnicodeAwareContext.block(array)

