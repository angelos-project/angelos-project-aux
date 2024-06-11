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

public object ArabicMathematicalAlphabeticSymbols : UtfBlock {
    override val name: String = "Arabic Mathematical Alphabetic Symbols"
    override val meta: String = "0x1ee00..0x1eeff (143/113)"
    override val range: IntRange = 0x1ee00..0x1eef1
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x1ee04,
            0x1ee20,
            0x1ee23,
            0x1ee25,
            0x1ee26,
            0x1ee28,
            0x1ee33,
            0x1ee38,
            0x1ee3a,
            0x1ee3c,
            0x1ee3d,
            0x1ee3e,
            0x1ee3f,
            0x1ee40,
            0x1ee41,
            0x1ee43,
            0x1ee44,
            0x1ee45,
            0x1ee46,
            0x1ee48,
            0x1ee4a,
            0x1ee4c,
            0x1ee50,
            0x1ee53,
            0x1ee55,
            0x1ee56,
            0x1ee58,
            0x1ee5a,
            0x1ee5c,
            0x1ee5e,
            0x1ee60,
            0x1ee63,
            0x1ee65,
            0x1ee66,
            0x1ee6b,
            0x1ee73,
            0x1ee78,
            0x1ee7d,
            0x1ee7f,
            0x1ee8a,
            0x1ee9c,
            0x1ee9d,
            0x1ee9e,
            0x1ee9f,
            0x1eea0,
            0x1eea4,
            0x1eeaa,
            0x1eebc,
            0x1eebd,
            0x1eebe,
            0x1eebf,
            0x1eec0,
            0x1eec1,
            0x1eec2,
            0x1eec3,
            0x1eec4,
            0x1eec5,
            0x1eec6,
            0x1eec7,
            0x1eec8,
            0x1eec9,
            0x1eeca,
            0x1eecb,
            0x1eecc,
            0x1eecd,
            0x1eece,
            0x1eecf,
            0x1eed0,
            0x1eed1,
            0x1eed2,
            0x1eed3,
            0x1eed4,
            0x1eed5,
            0x1eed6,
            0x1eed7,
            0x1eed8,
            0x1eed9,
            0x1eeda,
            0x1eedb,
            0x1eedc,
            0x1eedd,
            0x1eede,
            0x1eedf,
            0x1eee0,
            0x1eee1,
            0x1eee2,
            0x1eee3,
            0x1eee4,
            0x1eee5,
            0x1eee6,
            0x1eee7,
            0x1eee8,
            0x1eee9,
            0x1eeea,
            0x1eeeb,
            0x1eeec,
            0x1eeed,
            0x1eeee,
            0x1eeef
        )
    }
}
