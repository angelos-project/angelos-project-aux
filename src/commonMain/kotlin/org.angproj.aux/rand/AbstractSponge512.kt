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
package org.angproj.aux.rand

public abstract class AbstractSponge512: AbstractSponge(9, 8) {
    override fun round() {
        val r0 = state[0] xor state[3] xor state[6]
        val r1 = state[1] xor state[4] xor state[7]
        val r2 = state[2] xor state[5] xor state[8]

        val temp = -state[8].inv() * 29
        state[8] = -state[7].inv() * 23
        state[7] = -state[6].inv() * 19
        state[6] = -state[5].inv() * 17
        state[5] = -state[4].inv() * 13
        state[4] = -state[3].inv() * 11
        state[3] = -state[2].inv() * 7
        state[2] = -state[1].inv() * 5
        state[1] = -state[0].inv() * 3
        state[0] = temp

        mask = (mask and counter and state[0] and state[1] and state[2]) xor
                ((r0 and state[3] and state[4] and state[5]) * 2) xor
                ((r1 and state[6] and state[7]) * 4) xor
                ((r2 and state[8]) * 8) xor
                (-counter.inv() * 16)

        state[0] = state[0] xor r0
        state[1] = state[1] xor r0
        state[2] = state[2] xor r0
        state[3] = state[3] xor r1
        state[4] = state[4] xor r1
        state[5] = state[5] xor r1
        state[6] = state[6] xor r2
        state[7] = state[7] xor r2
        state[8] = state[8] xor r2

        counter++
    }
}