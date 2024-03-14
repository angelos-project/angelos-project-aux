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

import org.angproj.aux.fsm.States

public enum class Sanitizer {
    START,
    RESTART,
    FINNISH,
    ERROR,
    ASCII,
    FIRST_OF_TWO,
    TWO_COMPLETED,
    FIRST_OF_THREE,
    SECOND_OF_THREE,
    THREE_COMPLETED,
    FIRST_OF_FOUR,
    SECOND_OF_FOUR,
    THIRD_OF_FOUR,
    FOUR_COMPLETED,
    FIRST_OF_FIVE,
    SECOND_OF_FIVE,
    THIRD_OF_FIVE,
    FOURTH_OF_FIVE,
    FIVE_COMPLETED,
    FIRST_OF_SIX,
    SECOND_OF_SIX,
    THIRD_OF_SIX,
    FOURTH_OF_SIX,
    FIFTH_OF_SIX,
    SIX_COMPLETED;

    public companion object: States<Sanitizer> {
        override val transitionMap: Map<Sanitizer, List<Sanitizer>> = mapOf(
            START to listOf(ASCII, FIRST_OF_TWO, FIRST_OF_THREE, FIRST_OF_FOUR, FIRST_OF_FIVE, FIRST_OF_SIX, FINNISH),
            RESTART to listOf(ASCII, FIRST_OF_TWO, FIRST_OF_THREE, FIRST_OF_FOUR, FIRST_OF_FIVE, FIRST_OF_SIX, FINNISH),
            ASCII to listOf(RESTART, FINNISH),
            FIRST_OF_TWO to listOf(TWO_COMPLETED),
            TWO_COMPLETED to listOf(RESTART, FINNISH),
            FIRST_OF_THREE to listOf(SECOND_OF_THREE),
            SECOND_OF_THREE to listOf(THREE_COMPLETED),
            THREE_COMPLETED to listOf(RESTART, FINNISH),
            FIRST_OF_FOUR to listOf(SECOND_OF_FOUR),
            SECOND_OF_FOUR to listOf(THIRD_OF_FOUR),
            THIRD_OF_FOUR to listOf(FOUR_COMPLETED),
            FOUR_COMPLETED to listOf(RESTART, FINNISH),
            FIRST_OF_FIVE to listOf(SECOND_OF_FIVE),
            SECOND_OF_FIVE to listOf(THIRD_OF_FIVE),
            THIRD_OF_FIVE to listOf(FOURTH_OF_FIVE),
            FOURTH_OF_FIVE to listOf(FIVE_COMPLETED),
            FIVE_COMPLETED to listOf(RESTART, FINNISH),
            FIRST_OF_SIX to listOf(SECOND_OF_SIX),
            SECOND_OF_SIX to listOf(THIRD_OF_SIX),
            THIRD_OF_SIX to listOf(FOURTH_OF_SIX),
            FOURTH_OF_SIX to listOf(FIFTH_OF_SIX),
            FIFTH_OF_SIX to listOf(SIX_COMPLETED),
            SIX_COMPLETED to listOf(RESTART, FINNISH),
            FINNISH to listOf(),
            ERROR to listOf()
        )

        override val beginsWith: Sanitizer = START
        override val endsWith: Sanitizer = FINNISH
    }
}