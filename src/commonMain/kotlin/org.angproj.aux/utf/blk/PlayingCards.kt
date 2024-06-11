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

public object PlayingCards : UtfBlock {
    override val name: String = "Playing Cards"
    override val meta: String = "0x1f0a0..0x1f0ff (82/14)"
    override val range: IntRange = 0x1f0a0..0x1f0f5
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf(0x1f0af, 0x1f0b0, 0x1f0c0, 0x1f0d0) }
}
