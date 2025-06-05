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

public object Grantha : UtfBlock {
    override val name: String = "Grantha"
    override val meta: String = "0x11300..0x1137f (86/42)"
    override val range: IntRange = 0x11300..0x11374
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x11304,
            0x1130d,
            0x1130e,
            0x11311,
            0x11312,
            0x11329,
            0x11331,
            0x11334,
            0x1133a,
            0x11345,
            0x11346,
            0x11349,
            0x1134a,
            0x1134e,
            0x1134f,
            0x11351,
            0x11352,
            0x11353,
            0x11354,
            0x11355,
            0x11356,
            0x11358,
            0x11359,
            0x1135a,
            0x1135b,
            0x1135c,
            0x11364,
            0x11365,
            0x1136d,
            0x1136e,
            0x1136f
        )
    }
}
