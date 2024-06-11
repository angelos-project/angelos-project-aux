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

public object TangutSupplement : UtfBlock {
    override val name: String = "Tangut Supplement"
    override val meta: String = "0x18d00..0x18d7f (9/119)"
    override val range: IntRange = 0x18d00..0x18d08
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf() }
}
