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

public object SymbolsForLegacyComputing : UtfBlock {
    override val name: String = "Symbols for Legacy Computing"
    override val meta: String = "0x1fb00..0x1fbff (212/44)"
    override val range: IntRange = 0x1fb00..0x1fbf9
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x1fb93,
            0x1fbcb,
            0x1fbcc,
            0x1fbcd,
            0x1fbce,
            0x1fbcf,
            0x1fbd0,
            0x1fbd1,
            0x1fbd2,
            0x1fbd3,
            0x1fbd4,
            0x1fbd5,
            0x1fbd6,
            0x1fbd7,
            0x1fbd8,
            0x1fbd9,
            0x1fbda,
            0x1fbdb,
            0x1fbdc,
            0x1fbdd,
            0x1fbde,
            0x1fbdf,
            0x1fbe0,
            0x1fbe1,
            0x1fbe2,
            0x1fbe3,
            0x1fbe4,
            0x1fbe5,
            0x1fbe6,
            0x1fbe7,
            0x1fbe8,
            0x1fbe9,
            0x1fbea,
            0x1fbeb,
            0x1fbec,
            0x1fbed,
            0x1fbee,
            0x1fbef
        )
    }
}
