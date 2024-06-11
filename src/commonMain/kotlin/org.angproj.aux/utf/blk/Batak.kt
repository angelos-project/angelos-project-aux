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

public object Batak : UtfBlock {
    override val name: String = "Batak"
    override val meta: String = "0x1bc0..0x1bff (56/8)"
    override val range: IntRange = 0x1bc0..0x1bff
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf(0x1bf4, 0x1bf5, 0x1bf6, 0x1bf7, 0x1bf8, 0x1bf9, 0x1bfa, 0x1bfb) }
}
