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

public object HanifiRohingya : UtfBlock {
    override val name: String = "Hanifi Rohingya"
    override val meta: String = "0x10d00..0x10d3f (50/14)"
    override val range: IntRange = 0x10d00..0x10d39
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x10d28,
            0x10d29,
            0x10d2a,
            0x10d2b,
            0x10d2c,
            0x10d2d,
            0x10d2e,
            0x10d2f
        )
    }
}
