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

public object SymbolsAndPictographsExtendedA : UtfBlock {
    override val name: String = "Symbols and Pictographs Extended-A"
    override val meta: String = "0x1fa70..0x1faff (107/37)"
    override val range: IntRange = 0x1fa70..0x1faf8
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x1fa7d,
            0x1fa7e,
            0x1fa7f,
            0x1fa89,
            0x1fa8a,
            0x1fa8b,
            0x1fa8c,
            0x1fa8d,
            0x1fa8e,
            0x1fa8f,
            0x1fabe,
            0x1fac6,
            0x1fac7,
            0x1fac8,
            0x1fac9,
            0x1faca,
            0x1facb,
            0x1facc,
            0x1facd,
            0x1fadc,
            0x1fadd,
            0x1fade,
            0x1fadf,
            0x1fae9,
            0x1faea,
            0x1faeb,
            0x1faec,
            0x1faed,
            0x1faee,
            0x1faef
        )
    }
}
