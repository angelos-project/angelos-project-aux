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

public object Nabataean : UtfBlock {
    override val name: String = "Nabataean"
    override val meta: String = "0x10880..0x108af (40/8)"
    override val range: IntRange = 0x10880..0x108af
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x1089f,
            0x108a0,
            0x108a1,
            0x108a2,
            0x108a3,
            0x108a4,
            0x108a5,
            0x108a6
        )
    }
}
