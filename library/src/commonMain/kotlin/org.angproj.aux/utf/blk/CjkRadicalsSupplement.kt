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

public object CjkRadicalsSupplement : UtfBlock {
    override val name: String = "CJK Radicals Supplement"
    override val meta: String = "0x2e80..0x2eff (115/13)"
    override val range: IntRange = 0x2e80..0x2ef3
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf(0x2e9a) }
}
