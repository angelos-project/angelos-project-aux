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

public object SuttonSignwriting : UtfBlock {
    override val name: String = "Sutton SignWriting"
    override val meta: String = "0x1d800..0x1daaf (672/16)"
    override val range: IntRange = 0x1d800..0x1daaf
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x1da8c,
            0x1da8d,
            0x1da8e,
            0x1da8f,
            0x1da90,
            0x1da91,
            0x1da92,
            0x1da93,
            0x1da94,
            0x1da95,
            0x1da96,
            0x1da97,
            0x1da98,
            0x1da99,
            0x1da9a,
            0x1daa0
        )
    }
}
