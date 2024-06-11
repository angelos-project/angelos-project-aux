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

public object Armenian : UtfBlock {
    override val name: String = "Armenian"
    override val meta: String = "0x530..0x58f (91/5)"
    override val range: IntRange = 0x531..0x58f
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf(0x557, 0x558, 0x58b, 0x58c) }
}
