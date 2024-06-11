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

public object TamilSupplement : UtfBlock {
    override val name: String = "Tamil Supplement"
    override val meta: String = "0x11fc0..0x11fff (51/13)"
    override val range: IntRange = 0x11fc0..0x11fff
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x11ff2,
            0x11ff3,
            0x11ff4,
            0x11ff5,
            0x11ff6,
            0x11ff7,
            0x11ff8,
            0x11ff9,
            0x11ffa,
            0x11ffb,
            0x11ffc,
            0x11ffd,
            0x11ffe
        )
    }
}
