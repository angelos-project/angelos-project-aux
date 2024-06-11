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
package org.angproj.aux.utf.blk

import org.angproj.aux.utf.UtfBlock

public object Bengali : UtfBlock {
    override val name: String = "Bengali"
    override val meta: String = "0x980..0x9ff (96/32)"
    override val range: IntRange = 0x980..0x9fe
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x984,
            0x98d,
            0x98e,
            0x991,
            0x992,
            0x9a9,
            0x9b1,
            0x9b3,
            0x9b4,
            0x9b5,
            0x9ba,
            0x9bb,
            0x9c5,
            0x9c6,
            0x9c9,
            0x9ca,
            0x9cf,
            0x9d0,
            0x9d1,
            0x9d2,
            0x9d3,
            0x9d4,
            0x9d5,
            0x9d6,
            0x9d8,
            0x9d9,
            0x9da,
            0x9db,
            0x9de,
            0x9e4,
            0x9e5
        )
    }
}
