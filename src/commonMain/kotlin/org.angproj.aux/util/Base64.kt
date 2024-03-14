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
