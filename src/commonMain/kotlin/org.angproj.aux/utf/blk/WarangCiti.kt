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

public object WarangCiti : UtfBlock {
    override val name: String = "Warang Citi"
    override val meta: String = "0x118a0..0x118ff (84/12)"
    override val range: IntRange = 0x118a0..0x118ff
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x118f3,
            0x118f4,
            0x118f5,
            0x118f6,
            0x118f7,
            0x118f8,
            0x118f9,
            0x118fa,
            0x118fb,
            0x118fc,
            0x118fd,
            0x118fe
        )
    }
}
