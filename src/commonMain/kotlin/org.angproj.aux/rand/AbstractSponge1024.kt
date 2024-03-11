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

public abstract class AbstractSponge1024: AbstractSponge(16, 16) {
    override fun round() {
        val r0 = state[0] xor state[4] xor state[8] xor state[12]
        val r1 = state[1] xor state[5] xor state[9] xor state[13]
        val r2 = state[2] xor state[6] xor state[10] xor state[14]
        val r3 = state[3] xor state[7] xor state[11] xor state[15]

        val temp = -state[15].inv() * 59
        state[15] = -state[14].inv() * 53
        state[14] = -state[13].inv() * 47
        state[13] = -state[12].inv() * 43
        state[12] = -state[11].inv() * 41
        state[11] = -state[10].inv() * 37
        state[10] = -state[9].inv() * 31
        state[9] = -state[8].inv() * 29
        state[8] = -state[7].inv() * 23
        state[7] = -state[6].inv() * 19
        state[6] = -state[5].inv() * 17
        state[5] = -state[4].inv() * 13
        state[4] = -state[3].inv() * 11
        state[3] = -state[2].inv() * 7
        state[2] = -state[1].inv() * 5
        state[1] = -state[0].inv() * 3
        state[0] = temp

        mask = (r0 and state[0] and state[2] and state[4] and state[6]) xor
                ((r1 and -mask.inv() and state[8] and state[10]) * 2) xor
                ((r2 and state[12] and state[14]) * 4) xor
                ((r3 and state[15]) * 8) xor
                (-counter.inv() * 16)

        state[0] = state[0] xor r0
        state[1] = state[1] xor r0
        state[2] = state[2] xor r0
        state[3] = state[3] xor r0
        state[4] = state[4] xor r1
        state[5] = state[5] xor r1
        state[6] = state[6] xor r1
        state[7] = state[7] xor r1
        state[8] = state[8] xor r2
        state[9] = state[9] xor r2
        state[10] = state[10] xor r2
        state[11] = state[11] xor r2
        state[12] = state[12] xor r3
        state[13] = state[13] xor r3
        state[14] = state[14] xor r3
        state[15] = state[15] xor r3

        counter++
    }
}