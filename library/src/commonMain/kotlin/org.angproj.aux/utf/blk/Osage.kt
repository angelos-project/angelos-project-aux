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

public object Osage : UtfBlock {
    override val name: String = "Osage"
    override val meta: String = "0x104b0..0x104ff (72/8)"
    override val range: IntRange = 0x104b0..0x104fb
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf(0x104d4, 0x104d5, 0x104d6, 0x104d7) }
}
