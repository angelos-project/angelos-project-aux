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

public object Modi : UtfBlock {
    override val name: String = "Modi"
    override val meta: String = "0x11600..0x1165f (79/17)"
    override val range: IntRange = 0x11600..0x11659
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x11645,
            0x11646,
            0x11647,
            0x11648,
            0x11649,
            0x1164a,
            0x1164b,
            0x1164c,
            0x1164d,
            0x1164e,
            0x1164f
        )
    }
}
