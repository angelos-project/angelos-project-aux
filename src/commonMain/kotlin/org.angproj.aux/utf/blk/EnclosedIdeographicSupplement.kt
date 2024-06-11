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

public object EnclosedIdeographicSupplement : UtfBlock {
    override val name: String = "Enclosed Ideographic Supplement"
    override val meta: String = "0x1f200..0x1f2ff (64/192)"
    override val range: IntRange = 0x1f200..0x1f265
    override val noCtrl: Boolean = true
    override val noUse: List<Int> by lazy {
        listOf(
            0x1f203,
            0x1f204,
            0x1f205,
            0x1f206,
            0x1f207,
            0x1f208,
            0x1f209,
            0x1f20a,
            0x1f20b,
            0x1f20c,
            0x1f20d,
            0x1f20e,
            0x1f20f,
            0x1f23c,
            0x1f23d,
            0x1f23e,
            0x1f23f,
            0x1f249,
            0x1f24a,
            0x1f24b,
            0x1f24c,
            0x1f24d,
            0x1f24e,
            0x1f24f,
            0x1f252,
            0x1f253,
            0x1f254,
            0x1f255,
            0x1f256,
            0x1f257,
            0x1f258,
            0x1f259,
            0x1f25a,
            0x1f25b,
            0x1f25c,
            0x1f25d,
            0x1f25e,
            0x1f25f
        )
    }
}
