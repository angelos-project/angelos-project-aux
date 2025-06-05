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

public object ChessSymbols : UtfBlock {
    override val name: String = "Chess Symbols"
    override val meta: String = "0x1fa00..0x1fa6f (98/14)"
    override val range: IntRange = 0x1fa00..0x1fa6d
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x1fa54,
            0x1fa55,
            0x1fa56,
            0x1fa57,
            0x1fa58,
            0x1fa59,
            0x1fa5a,
            0x1fa5b,
            0x1fa5c,
            0x1fa5d,
            0x1fa5e,
            0x1fa5f
        )
    }
}
