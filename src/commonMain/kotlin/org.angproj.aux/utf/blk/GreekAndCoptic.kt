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

public object GreekAndCoptic : UtfBlock {
    override val name: String = "Greek and Coptic"
    override val meta: String = "0x370..0x3ff (135/9)"
    override val range: IntRange = 0x370..0x3ff
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf(0x378, 0x379, 0x380, 0x381, 0x382, 0x383, 0x38b, 0x38d, 0x3a2) }
}
