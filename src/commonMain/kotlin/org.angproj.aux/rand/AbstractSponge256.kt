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

public abstract class AbstractSponge256: AbstractSponge(4, 4) {
    override fun round() {
        val r0 = state[0] xor state[2]
        val r1 = state[1] xor state[3]

        val temp = -state[3].inv() * 11
        state[3] = -state[2].inv() * 7
        state[2] = -state[1].inv() * 5
        state[1] = -state[0].inv() * 3
        state[0] = temp

        mask = (state[0] and r0 and counter and mask) xor
                ((state[1] and r1 and -mask.inv()) * 2) xor
                ((state[2] and -counter.inv()) * 4) xor
                (state[3] * 8)

        state[0] = state[0] xor r0
        state[1] = state[1] xor r0
        state[2] = state[2] xor r1
        state[3] = state[3] xor r1

        counter++
    }
}