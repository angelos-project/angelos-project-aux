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
package org.angproj.aux.utf

public enum class SequenceType(public val size: Int) {
    START_ONE_LONG(1),
    START_TWO_LONG(2),
    START_THREE_LONG(3),
    START_FOUR_LONG(4),
    START_FIVE_LONG(5),
    START_SIX_LONG(6),
    FOLLOW_DATA(0),
    ILLEGAL(-1);

    public companion object {
        public fun qualify(octet: Byte): SequenceType {
            val seq = octet.toInt()
            return when {
                seq and 0B1000_0000 == 0B0000_0000 -> START_ONE_LONG
                seq and 0B1100_0000 == 0B1000_0000 -> FOLLOW_DATA
                seq and 0B1110_0000 == 0B1100_0000 -> START_TWO_LONG
                seq and 0B1111_0000 == 0B1110_0000 -> START_THREE_LONG
                seq and 0B1111_1000 == 0B1111_0000 -> START_FOUR_LONG
                seq and 0B1111_1100 == 0B1111_1000 -> START_FIVE_LONG // Just to deal with the illegal sequence
                seq and 0B1111_1110 == 0B1111_1100 -> START_SIX_LONG // Just to deal with the illegal sequence
                else -> ILLEGAL
            }
        }

        public fun extract(type: SequenceType, octet: Byte): Int {
            val seq = octet.toInt()
            return when(type) {
                START_ONE_LONG -> seq and 0B0111_1111
                FOLLOW_DATA -> seq and 0B0011_1111
                START_TWO_LONG -> seq and 0B0001_1111
                START_THREE_LONG -> seq and 0B0000_1111
                START_FOUR_LONG -> seq and 0B0000_0111
                START_FIVE_LONG -> seq and 0B0000_0011
                START_SIX_LONG -> seq and 0B0000_0001
                ILLEGAL -> 0
            }
        }
    }
}