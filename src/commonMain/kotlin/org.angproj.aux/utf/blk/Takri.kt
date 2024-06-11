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

public object Takri : UtfBlock {
    override val name: String = "Takri"
    override val meta: String = "0x11680..0x116cf (68/12)"
    override val range: IntRange = 0x11680..0x116c9
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf(0x116ba, 0x116bb, 0x116bc, 0x116bd, 0x116be, 0x116bf) }
}
