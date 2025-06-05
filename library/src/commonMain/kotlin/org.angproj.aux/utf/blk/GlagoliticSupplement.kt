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

public object GlagoliticSupplement : UtfBlock {
    override val name: String = "Glagolitic Supplement"
    override val meta: String = "0x1e000..0x1e02f (38/10)"
    override val range: IntRange = 0x1e000..0x1e02a
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf(0x1e007, 0x1e019, 0x1e01a, 0x1e022, 0x1e025) }
}
