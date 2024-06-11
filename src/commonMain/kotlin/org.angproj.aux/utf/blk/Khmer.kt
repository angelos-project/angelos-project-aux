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

public object Khmer : UtfBlock {
    override val name: String = "Khmer"
    override val meta: String = "0x1780..0x17ff (114/14)"
    override val range: IntRange = 0x1780..0x17f9
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf(0x17de, 0x17df, 0x17ea, 0x17eb, 0x17ec, 0x17ed, 0x17ee, 0x17ef) }
}
