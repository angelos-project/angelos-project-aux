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

public object Bhaiksuki : UtfBlock {
    override val name: String = "Bhaiksuki"
    override val meta: String = "0x11c00..0x11c6f (97/15)"
    override val range: IntRange = 0x11c00..0x11c6c
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x11c09,
            0x11c37,
            0x11c46,
            0x11c47,
            0x11c48,
            0x11c49,
            0x11c4a,
            0x11c4b,
            0x11c4c,
            0x11c4d,
            0x11c4e,
            0x11c4f
        )
    }
}
