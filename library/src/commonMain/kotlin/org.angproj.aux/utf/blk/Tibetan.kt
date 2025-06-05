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

public object Tibetan : UtfBlock {
    override val name: String = "Tibetan"
    override val meta: String = "0xf00..0xfff (211/45)"
    override val range: IntRange = 0xf00..0xfda
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf(0xf48, 0xf6d, 0xf6e, 0xf6f, 0xf70, 0xf98, 0xfbd, 0xfcd) }
}
