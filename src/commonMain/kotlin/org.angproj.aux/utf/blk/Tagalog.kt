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

public object Tagalog : UtfBlock {
    override val name: String = "Tagalog"
    override val meta: String = "0x1700..0x171f (23/9)"
    override val range: IntRange = 0x1700..0x171f
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x1716,
            0x1717,
            0x1718,
            0x1719,
            0x171a,
            0x171b,
            0x171c,
            0x171d,
            0x171e
        )
    }
}
