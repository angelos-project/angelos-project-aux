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

public object SmallFormVariants : UtfBlock {
    override val name: String = "Small Form Variants"
    override val meta: String = "0xfe50..0xfe6f (26/6)"
    override val range: IntRange = 0xfe50..0xfe6b
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf(0xfe53, 0xfe67) }
}
