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

public object MathematicalAlphanumericSymbols : UtfBlock {
    override val name: String = "Mathematical Alphanumeric Symbols"
    override val meta: String = "0x1d400..0x1d7ff (996/28)"
    override val range: IntRange = 0x1d400..0x1d7ff
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x1d455,
            0x1d49d,
            0x1d4a0,
            0x1d4a1,
            0x1d4a3,
            0x1d4a4,
            0x1d4a7,
            0x1d4a8,
            0x1d4ad,
            0x1d4ba,
            0x1d4bc,
            0x1d4c4,
            0x1d506,
            0x1d50b,
            0x1d50c,
            0x1d515,
            0x1d51d,
            0x1d53a,
            0x1d53f,
            0x1d545,
            0x1d547,
            0x1d548,
            0x1d549,
            0x1d551,
            0x1d6a6,
            0x1d6a7,
            0x1d7cc,
            0x1d7cd
        )
    }
}
