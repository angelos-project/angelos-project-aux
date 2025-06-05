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

import org.angproj.aux.utf.Ascii

public fun Base64.encodeUrlSafe(data: ByteArray): CharArray {
    return encode(data, URL_SAFE)
}

public fun Base64.decodeUrlSafe(data: CharArray): ByteArray {
    return decode(data, MAP_URL_SAFE)
}

public fun Base64.encodeMimeSafe(data: ByteArray): CharArray {
    TODO()
}

public fun Base64.decodeMimeSafe(data: CharArray): ByteArray {
    TODO()
}

public object Base64 {

    public val padding: Int = Ascii.PRNT_EQUALS.cp

    public val base2bin: Map<Int, Int> = mapOf(
        Ascii.PRNT_A_UP.cp to 0x00, Ascii.PRNT_B_UP.cp to 0x01,
        Ascii.PRNT_C_UP.cp to 0x02, Ascii.PRNT_D_UP.cp to 0x03, Ascii.PRNT_E_UP.cp to 0x04, Ascii.PRNT_F_UP.cp to 0x05,
        Ascii.PRNT_G_UP.cp to 0x06, Ascii.PRNT_H_UP.cp to 0x07, Ascii.PRNT_I_UP.cp to 0x08, Ascii.PRNT_J_UP.cp to 0x09,
        Ascii.PRNT_K_UP.cp to 0x0a, Ascii.PRNT_L_UP.cp to 0x0b, Ascii.PRNT_M_UP.cp to 0x0c, Ascii.PRNT_N_UP.cp to 0x0d,
        Ascii.PRNT_O_UP.cp to 0x0e, Ascii.PRNT_P_UP.cp to 0x0f, Ascii.PRNT_Q_UP.cp to 0x10, Ascii.PRNT_R_UP.cp to 0x11,
        Ascii.PRNT_S_UP.cp to 0x12, Ascii.PRNT_T_UP.cp to 0x13, Ascii.PRNT_U_UP.cp to 0x14, Ascii.PRNT_V_UP.cp to 0x15,
        Ascii.PRNT_W_UP.cp to 0x16, Ascii.PRNT_X_UP.cp to 0x17, Ascii.PRNT_Y_UP.cp to 0x18, Ascii.PRNT_Z_UP.cp to 0x19,

        Ascii.PRNT_A_LOW.cp to 0x1a, Ascii.PRNT_B_LOW.cp to 0x1b,
        Ascii.PRNT_C_LOW.cp to 0x1c, Ascii.PRNT_D_LOW.cp to 0x1d, Ascii.PRNT_E_LOW.cp to 0x1e, Ascii.PRNT_F_LOW.cp to 0x1f,
        Ascii.PRNT_G_LOW.cp to 0x20, Ascii.PRNT_H_LOW.cp to 0x21, Ascii.PRNT_I_LOW.cp to 0x22, Ascii.PRNT_J_LOW.cp to 0x23,
        Ascii.PRNT_K_LOW.cp to 0x24, Ascii.PRNT_L_LOW.cp to 0x25, Ascii.PRNT_M_LOW.cp to 0x26, Ascii.PRNT_N_LOW.cp to 0x27,
        Ascii.PRNT_O_LOW.cp to 0x28, Ascii.PRNT_P_LOW.cp to 0x29, Ascii.PRNT_Q_LOW.cp to 0x2a, Ascii.PRNT_R_LOW.cp to 0x2b,
        Ascii.PRNT_S_LOW.cp to 0x2c, Ascii.PRNT_T_LOW.cp to 0x2d, Ascii.PRNT_U_LOW.cp to 0x2e, Ascii.PRNT_V_LOW.cp to 0x2f,
        Ascii.PRNT_W_LOW.cp to 0x30, Ascii.PRNT_X_LOW.cp to 0x31, Ascii.PRNT_Y_LOW.cp to 0x32, Ascii.PRNT_Z_LOW.cp to 0x33,

        Ascii.PRNT_ZERO.cp to 0x34, Ascii.PRNT_ONE.cp to 0x35, Ascii.PRNT_TWO.cp to 0x36, Ascii.PRNT_THREE.cp to 0x37,
        Ascii.PRNT_FOUR.cp to 0x38, Ascii.PRNT_FIVE.cp to 0x39, Ascii.PRNT_SIX.cp to 0x3a, Ascii.PRNT_SEVEN.cp to 0x3b,
        Ascii.PRNT_EIGHT.cp to 0x3c, Ascii.PRNT_NINE.cp to 0x3d, Ascii.PRNT_PLUS.cp to 0x3e, Ascii.PRNT_SLASH.cp to 0x3f,
    )

    public val bin2base: List<Int> = listOf(
        Ascii.PRNT_A_UP.cp, Ascii.PRNT_B_UP.cp,
        Ascii.PRNT_C_UP.cp, Ascii.PRNT_D_UP.cp, Ascii.PRNT_E_UP.cp, Ascii.PRNT_F_UP.cp,
        Ascii.PRNT_G_UP.cp, Ascii.PRNT_H_UP.cp, Ascii.PRNT_I_UP.cp, Ascii.PRNT_J_UP.cp,
        Ascii.PRNT_K_UP.cp, Ascii.PRNT_L_UP.cp, Ascii.PRNT_M_UP.cp, Ascii.PRNT_N_UP.cp,
        Ascii.PRNT_O_UP.cp, Ascii.PRNT_P_UP.cp, Ascii.PRNT_Q_UP.cp, Ascii.PRNT_R_UP.cp,
        Ascii.PRNT_S_UP.cp, Ascii.PRNT_T_UP.cp, Ascii.PRNT_U_UP.cp, Ascii.PRNT_V_UP.cp,
        Ascii.PRNT_W_UP.cp, Ascii.PRNT_X_UP.cp, Ascii.PRNT_Y_UP.cp, Ascii.PRNT_Z_UP.cp,

        Ascii.PRNT_A_LOW.cp, Ascii.PRNT_B_LOW.cp,
        Ascii.PRNT_C_LOW.cp, Ascii.PRNT_D_LOW.cp, Ascii.PRNT_E_LOW.cp, Ascii.PRNT_F_LOW.cp,
        Ascii.PRNT_G_LOW.cp, Ascii.PRNT_H_LOW.cp, Ascii.PRNT_I_LOW.cp, Ascii.PRNT_J_LOW.cp,
        Ascii.PRNT_K_LOW.cp, Ascii.PRNT_L_LOW.cp, Ascii.PRNT_M_LOW.cp, Ascii.PRNT_N_LOW.cp,
        Ascii.PRNT_O_LOW.cp, Ascii.PRNT_P_LOW.cp, Ascii.PRNT_Q_LOW.cp, Ascii.PRNT_R_LOW.cp,
        Ascii.PRNT_S_LOW.cp, Ascii.PRNT_T_LOW.cp, Ascii.PRNT_U_LOW.cp, Ascii.PRNT_V_LOW.cp,
        Ascii.PRNT_W_LOW.cp, Ascii.PRNT_X_LOW.cp, Ascii.PRNT_Y_LOW.cp, Ascii.PRNT_Z_LOW.cp,

        Ascii.PRNT_ZERO.cp, Ascii.PRNT_ONE.cp, Ascii.PRNT_TWO.cp, Ascii.PRNT_THREE.cp,
        Ascii.PRNT_FOUR.cp, Ascii.PRNT_FIVE.cp, Ascii.PRNT_SIX.cp, Ascii.PRNT_SEVEN.cp,
        Ascii.PRNT_EIGHT.cp, Ascii.PRNT_NINE.cp, Ascii.PRNT_PLUS.cp, Ascii.PRNT_SLASH.cp,
    )

    internal val STANDARD: CharArray = charArrayOf(
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
    )

    internal val MAP_STANDARD: Map<Char, Int> by lazy {
        var start = 0
        STANDARD.associateWith { start++ }
    }

    internal val URL_SAFE: CharArray = charArrayOf(
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'
    )

    internal val MAP_URL_SAFE: Map<Char, Int> by lazy {
        var start = 0
        URL_SAFE.associateWith { start++ }
    }

    public fun encode(src: ByteArray, alphabet: CharArray = STANDARD, padding: Char = '='): CharArray {
        require(alphabet.size == 64) { "Invalid alphabet length. Must be exactly 64 slots." }

        val fullBlkCnt = src.size / 3
        val remainder = src.size % 3
        val extra = if (remainder > 0) 1 else 0
        val dest = CharArray(4 * (fullBlkCnt + extra))

        (0 until fullBlkCnt).forEach { blkIdx ->
            val srcIdx = blkIdx * 3
            val destIdx = blkIdx * 4

            val bits = (src[srcIdx].toInt() and 0xff) shl 16 or (
                    (src[srcIdx + 1].toInt() and 0xff) shl 8) or
                    (src[srcIdx + 2].toInt() and 0xff)

            dest[destIdx] = alphabet[bits ushr 18 and 0x3f]
            dest[destIdx + 1] = alphabet[bits ushr 12 and 0x3f]
            dest[destIdx + 2] = alphabet[bits ushr 6 and 0x3f]
            dest[destIdx + 3] = alphabet[bits and 0x3f]
        }

        when (remainder) {
            1 -> {
                val byte0 = src[src.lastIndex].toInt() and 0xff

                dest[dest.lastIndex - 3] = alphabet[byte0 shr 2]
                dest[dest.lastIndex - 2] = alphabet[byte0 shl 4 and 0x3f]
                dest[dest.lastIndex - 1] = padding
                dest[dest.lastIndex] = padding
            }

            2 -> {
                val byte0 = src[src.lastIndex - 1].toInt() and 0xff
                val byte1 = src[src.lastIndex].toInt() and 0xff

                dest[dest.lastIndex - 3] = alphabet[byte0 shr 2]
                dest[dest.lastIndex - 2] = alphabet[(byte0 shl 4) and 0x3f or (byte1 shr 4)]
                dest[dest.lastIndex - 1] = alphabet[byte1 shl 2 and 0x3f]
                dest[dest.lastIndex] = padding
            }
        }

        return dest
    }

    public fun decode(src: CharArray, alphabet: Map<Char, Int> = MAP_STANDARD, padding: Char = '='): ByteArray {
        val fullBlkCnt = src.size / 4
        val remainder = when (padding) {
            src[src.lastIndex - 1] -> 1
            src[src.lastIndex] -> 2
            else -> 0
        }
        val extra = if (remainder > 0) 1 else 0
        val dest = ByteArray(3 * fullBlkCnt - if (remainder == 1) 1 else 0)

        (0 until fullBlkCnt - extra).forEach { blkIdx ->
            val srcIdx = blkIdx * 4
            val destIdx = blkIdx * 3

            val bits = (alphabet.getValue(src[srcIdx]).shl(18)) or
                    (alphabet.getValue(src[srcIdx + 1]).shl(12)) or
                    (alphabet.getValue(src[srcIdx + 2]).shl(6)) or
                    alphabet.getValue(src[srcIdx + 3])

            dest[destIdx] = (bits shr 16).toByte()
            dest[destIdx + 1] = (bits shr 8).toByte()
            dest[destIdx + 2] = bits.toByte()
        }

        when (remainder) {
            1 -> {
                val bits = (alphabet.getValue(src[src.lastIndex - 3]).shl(18)) or
                        (alphabet.getValue(src[src.lastIndex - 2]).shl(12))

                dest[dest.lastIndex - 1] = (bits shr 16).toByte()
                dest[dest.lastIndex] = (bits shr 8).toByte()
            }

            2 -> {
                val bits = (alphabet.getValue(src[src.lastIndex - 3]).shl(18)) or
                        (alphabet.getValue(src[src.lastIndex - 2]).shl(12)) or
                        (alphabet.getValue(src[src.lastIndex - 1]).shl(6))

                dest[dest.lastIndex - 2] = (bits shr 16).toByte()
                dest[dest.lastIndex - 1] = (bits shr 8).toByte()
                dest[dest.lastIndex] = bits.toByte()
            }
        }

        return dest
    }
}
