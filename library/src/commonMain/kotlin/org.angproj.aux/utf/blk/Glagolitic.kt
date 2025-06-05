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

public object Glagolitic : UtfBlock {
    override val name: String = "Glagolitic"
    override val meta: String = "0x2c00..0x2c5f (96/0)"
    override val range: IntRange = 0x2c00..0x2c5f
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf() }
}
