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

public object Ahom : UtfBlock {
    override val name: String = "Ahom"
    override val meta: String = "0x11700..0x1174f (65/15)"
    override val range: IntRange = 0x11700..0x11746
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf(0x1171b, 0x1171c, 0x1172c, 0x1172d, 0x1172e, 0x1172f) }
}
