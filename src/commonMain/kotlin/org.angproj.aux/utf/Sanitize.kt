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

import org.angproj.aux.fsm.StateMachine
import org.angproj.aux.util.NullObject

internal class Sanitize() {
    var fsm: StateMachine<Sanitizer> = Sanitizer.create()
    private var octet: Byte = 0
    private var glyph: Glyph = 0
    var input: ByteArray = NullObject.byteArray
    private var inPos: Int = 0
    var output: IntArray = NullObject.intArray
    private var outPos: Int = 0

    public fun process(): Unit = when(fsm.state) {
        Sanitizer.START -> restart()
        Sanitizer.RESTART -> restart()
        Sanitizer.FINISH -> Unit
        Sanitizer.ASCII -> ascii()
        Sanitizer.FIRST_OF_TWO -> firstOfTwo()
        Sanitizer.TWO_COMPLETED -> twoCompleted()
        Sanitizer.FIRST_OF_THREE -> firstOfX(Sanitizer.SECOND_OF_THREE, SequenceType.START_THREE_LONG)
        Sanitizer.SECOND_OF_THREE -> yOfX(Sanitizer.THREE_COMPLETED)
        Sanitizer.THREE_COMPLETED -> xCompleted()
        Sanitizer.FIRST_OF_FOUR -> firstOfX(Sanitizer.SECOND_OF_FOUR, SequenceType.START_FOUR_LONG)
        Sanitizer.SECOND_OF_FOUR -> yOfX(Sanitizer.THIRD_OF_FOUR)
        Sanitizer.THIRD_OF_FOUR -> yOfX(Sanitizer.FOUR_COMPLETED)
        Sanitizer.FOUR_COMPLETED -> xCompleted()
        Sanitizer.FIRST_OF_FIVE -> firstOfX(Sanitizer.SECOND_OF_FIVE, SequenceType.START_FIVE_LONG)
        Sanitizer.SECOND_OF_FIVE -> yOfX(Sanitizer.THIRD_OF_FIVE)
        Sanitizer.THIRD_OF_FIVE -> yOfX(Sanitizer.FOURTH_OF_FIVE)
        Sanitizer.FOURTH_OF_FIVE -> yOfX(Sanitizer.FIVE_COMPLETED)
        Sanitizer.FIVE_COMPLETED -> xCompleted()
        Sanitizer.FIRST_OF_SIX -> firstOfX(Sanitizer.SECOND_OF_SIX, SequenceType.START_SIX_LONG)
        Sanitizer.SECOND_OF_SIX -> yOfX(Sanitizer.THIRD_OF_SIX)
        Sanitizer.THIRD_OF_SIX -> yOfX(Sanitizer.FOURTH_OF_SIX)
        Sanitizer.FOURTH_OF_SIX -> yOfX(Sanitizer.FIFTH_OF_SIX)
        Sanitizer.FIFTH_OF_SIX -> yOfX(Sanitizer.SIX_COMPLETED)
        Sanitizer.SIX_COMPLETED -> xCompleted()
        Sanitizer.ERROR -> Unit
    }

    public fun finish() {
        fsm.state = Sanitizer.FINISH
    }

    private fun xCompleted() {
        when(SequenceType.qualify(octet)) {
            SequenceType.FOLLOW_DATA -> {
                followToGlyph()
                printGlyph()
                fsm.state = Sanitizer.RESTART
            }
            else -> fsm.state = Sanitizer.ERROR
        }
    }

    private fun yOfX(next: Sanitizer) {
        when(SequenceType.qualify(octet)) {
            SequenceType.FOLLOW_DATA -> {
                followToGlyph()
                acquireOctet()
                fsm.state = next
            }
            else -> fsm.state = Sanitizer.ERROR
        }
    }

    private fun firstOfX(next: Sanitizer, type: SequenceType) {
        initialToGlyph(type)
        acquireOctet()
        fsm.state = next
    }

    private fun twoCompleted() {
        when(SequenceType.qualify(octet)) {
            SequenceType.FOLLOW_DATA -> {
                followToGlyph()
                printGlyph()
                fsm.state = Sanitizer.RESTART
            }
            else -> fsm.state = Sanitizer.ERROR
        }
    }

    private fun firstOfTwo() {
        initialToGlyph(SequenceType.START_TWO_LONG)
        acquireOctet()
        fsm.state = Sanitizer.TWO_COMPLETED
    }

    private fun initialToGlyph(type: SequenceType) {
        glyph = SequenceType.extract(type, octet)
    }

    private fun followToGlyph() {
        glyph = glyph shl 6 or SequenceType.extract(SequenceType.FOLLOW_DATA, octet)
    }

    private fun printGlyph() {
        output[outPos++] = glyph
        glyph = 0
    }

    private fun acquireOctet() {
        octet = input[inPos++]
    }

    private fun ascii() {
        initialToGlyph(SequenceType.START_ONE_LONG)
        printGlyph()
        fsm.state = Sanitizer.RESTART
    }

    private fun restart(): Unit = when {
        else -> acquireOctet().also {
            when(SequenceType.qualify(octet)) {
                SequenceType.START_ONE_LONG -> fsm.state = Sanitizer.ASCII
                SequenceType.START_TWO_LONG -> fsm.state = Sanitizer.FIRST_OF_TWO
                SequenceType.START_THREE_LONG -> fsm.state = Sanitizer.FIRST_OF_THREE
                SequenceType.START_FOUR_LONG -> fsm.state = Sanitizer.FIRST_OF_FOUR
                SequenceType.START_FIVE_LONG -> fsm.state = Sanitizer.FIRST_OF_FIVE
                SequenceType.START_SIX_LONG -> fsm.state = Sanitizer.FOURTH_OF_SIX
                else -> fsm.state = Sanitizer.ERROR
            }
        }
    }
}
