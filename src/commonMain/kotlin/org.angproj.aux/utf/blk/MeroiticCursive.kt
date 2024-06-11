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

public object MeroiticCursive : UtfBlock {
    override val name: String = "Meroitic Cursive"
    override val meta: String = "0x109a0..0x109ff (90/6)"
    override val range: IntRange = 0x109a0..0x109ff
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf(0x109b8, 0x109b9, 0x109ba, 0x109bb, 0x109d0, 0x109d1) }
}
