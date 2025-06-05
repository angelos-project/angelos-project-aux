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

public object MusicalSymbols : UtfBlock {
    override val name: String = "Musical Symbols"
    override val meta: String = "0x1d100..0x1d1ff (233/23)"
    override val range: IntRange = 0x1d100..0x1d1ea
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf(0x1d127, 0x1d128) }
}
