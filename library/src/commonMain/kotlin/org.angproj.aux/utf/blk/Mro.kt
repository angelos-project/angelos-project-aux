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

public object Mro : UtfBlock {
    override val name: String = "Mro"
    override val meta: String = "0x16a40..0x16a6f (43/5)"
    override val range: IntRange = 0x16a40..0x16a6f
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf(0x16a5f, 0x16a6a, 0x16a6b, 0x16a6c, 0x16a6d) }
}
