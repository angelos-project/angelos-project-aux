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

public object IdeographicSymbolsAndPunctuation : UtfBlock {
    override val name: String = "Ideographic Symbols and Punctuation"
    override val meta: String = "0x16fe0..0x16fff (7/25)"
    override val range: IntRange = 0x16fe0..0x16ff1
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x16fe5,
            0x16fe6,
            0x16fe7,
            0x16fe8,
            0x16fe9,
            0x16fea,
            0x16feb,
            0x16fec,
            0x16fed,
            0x16fee,
            0x16fef
        )
    }
}
