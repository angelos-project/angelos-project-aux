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

public object Nandinagari : UtfBlock {
    override val name: String = "Nandinagari"
    override val meta: String = "0x119a0..0x119ff (65/31)"
    override val range: IntRange = 0x119a0..0x119e4
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf(0x119a8, 0x119a9, 0x119d8, 0x119d9) }
}
