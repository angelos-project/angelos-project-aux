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

public object Manichaean : UtfBlock {
    override val name: String = "Manichaean"
    override val meta: String = "0x10ac0..0x10aff (51/13)"
    override val range: IntRange = 0x10ac0..0x10af6
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf(0x10ae7, 0x10ae8, 0x10ae9, 0x10aea) }
}
