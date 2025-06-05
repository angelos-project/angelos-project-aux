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

import org.angproj.aux.utf.Ascii


public interface Hex {

    private inline fun<reified R: Any> upperToHex(value: Byte): Int = bin2hex[value.toInt() ushr 4 and 0xf]

    private inline fun<reified R: Any> lowerToHex(value: Byte): Int = bin2hex[value.toInt() and 0xf]

    public fun <E: Number> E.upperToHex(): Int = upperToHex<Unit>(this.toByte())

    public fun <E: Number> E.lowerToHex(): Int = lowerToHex<Unit>(this.toByte())

    public fun Byte.upperToHex(): CodePoint = upperToHex<Unit>(this).toCodePoint()

    public fun Byte.lowerToHex(): CodePoint = lowerToHex<Unit>(this).toCodePoint()

    private inline fun<reified R: Any> upperHexOf(value: Int): Int = (hex2bin[value] ?: error("Invalid hex")) shl 4

    private inline fun<reified R: Any> lowerHexOf(value: Int): Int = (hex2bin[value] ?: error("Invalid hex"))

    public fun <E: Number> E.upperHexOf(): Int = upperHexOf<Unit>(this.toInt())

    public fun <E: Number> E.lowerHexOf(): Int = lowerHexOf<Unit>(this.toInt())

    public fun CodePoint.upperHexOf(): Int = upperHexOf<Unit>(this.value)

    public fun CodePoint.lowerHexOf(): Int = lowerHexOf<Unit>(this.value)

    public fun byteFromHex(upper: CodePoint, lower: CodePoint): Byte = (
            upperHexOf<Unit>(upper.value) or lowerHexOf<Unit>(lower.value)).toByte()

    public companion object {
        public val valid: Set<Int> = setOf(
            Ascii.PRNT_ZERO.cp,
            Ascii.PRNT_ONE.cp,
            Ascii.PRNT_TWO.cp,
            Ascii.PRNT_THREE.cp,
            Ascii.PRNT_FOUR.cp,
            Ascii.PRNT_FIVE.cp,
            Ascii.PRNT_SIX.cp,
            Ascii.PRNT_SEVEN.cp,
            Ascii.PRNT_EIGHT.cp,
            Ascii.PRNT_NINE.cp,
            Ascii.PRNT_A_LOW.cp,
            Ascii.PRNT_B_LOW.cp,
            Ascii.PRNT_C_LOW.cp,
            Ascii.PRNT_D_LOW.cp,
            Ascii.PRNT_E_LOW.cp,
            Ascii.PRNT_F_LOW.cp,
            Ascii.PRNT_A_UP.cp,
            Ascii.PRNT_B_UP.cp,
            Ascii.PRNT_C_UP.cp,
            Ascii.PRNT_D_UP.cp,
            Ascii.PRNT_E_UP.cp,
            Ascii.PRNT_F_UP.cp
        )

        public val hex2bin: Map<Int, Int> = mapOf(
            Ascii.PRNT_ZERO.cp to 0x0,
            Ascii.PRNT_ONE.cp to 0x1,
            Ascii.PRNT_TWO.cp to 0x2,
            Ascii.PRNT_THREE.cp to 0x3,
            Ascii.PRNT_FOUR.cp to 0x4,
            Ascii.PRNT_FIVE.cp to 0x5,
            Ascii.PRNT_SIX.cp to 0x6,
            Ascii.PRNT_SEVEN.cp to 0x7,
            Ascii.PRNT_EIGHT.cp to 0x8,
            Ascii.PRNT_NINE.cp to 0x9,
            Ascii.PRNT_A_LOW.cp to 0xA,
            Ascii.PRNT_B_LOW.cp to 0xB,
            Ascii.PRNT_C_LOW.cp to 0xC,
            Ascii.PRNT_D_LOW.cp to 0xD,
            Ascii.PRNT_E_LOW.cp to 0xE,
            Ascii.PRNT_F_LOW.cp to 0xF,
            Ascii.PRNT_A_UP.cp to 0xA,
            Ascii.PRNT_B_UP.cp to 0xB,
            Ascii.PRNT_C_UP.cp to 0xC,
            Ascii.PRNT_D_UP.cp to 0xD,
            Ascii.PRNT_E_UP.cp to 0xE,
            Ascii.PRNT_F_UP.cp to 0xF
        )

        public val bin2hex: List<Int> = listOf(
            Ascii.PRNT_ZERO.cp,
            Ascii.PRNT_ONE.cp,
            Ascii.PRNT_TWO.cp,
            Ascii.PRNT_THREE.cp,
            Ascii.PRNT_FOUR.cp,
            Ascii.PRNT_FIVE.cp,
            Ascii.PRNT_SIX.cp,
            Ascii.PRNT_SEVEN.cp,
            Ascii.PRNT_EIGHT.cp,
            Ascii.PRNT_NINE.cp,
            Ascii.PRNT_A_LOW.cp,
            Ascii.PRNT_B_LOW.cp,
            Ascii.PRNT_C_LOW.cp,
            Ascii.PRNT_D_LOW.cp,
            Ascii.PRNT_E_LOW.cp,
            Ascii.PRNT_F_LOW.cp
        )
    }
}