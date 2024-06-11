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

public object AlchemicalSymbols : UtfBlock {
    override val name: String = "Alchemical Symbols"
    override val meta: String = "0x1f700..0x1f77f (124/4)"
    override val range: IntRange = 0x1f700..0x1f77f
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf(0x1f777, 0x1f778, 0x1f779, 0x1f77a) }
}
