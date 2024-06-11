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

public object Hatran : UtfBlock {
    override val name: String = "Hatran"
    override val meta: String = "0x108e0..0x108ff (26/6)"
    override val range: IntRange = 0x108e0..0x108ff
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf(0x108f3, 0x108f6, 0x108f7, 0x108f8, 0x108f9, 0x108fa) }
}
