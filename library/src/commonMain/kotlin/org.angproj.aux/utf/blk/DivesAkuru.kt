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

public object DivesAkuru : UtfBlock {
    override val name: String = "Dives Akuru"
    override val meta: String = "0x11900..0x1195f (72/24)"
    override val range: IntRange = 0x11900..0x11959
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x11907,
            0x11908,
            0x1190a,
            0x1190b,
            0x11914,
            0x11917,
            0x11936,
            0x11939,
            0x1193a,
            0x11947,
            0x11948,
            0x11949,
            0x1194a,
            0x1194b,
            0x1194c,
            0x1194d,
            0x1194e,
            0x1194f
        )
    }
}
