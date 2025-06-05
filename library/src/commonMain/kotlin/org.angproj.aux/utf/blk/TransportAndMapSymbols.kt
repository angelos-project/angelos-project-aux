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

public object TransportAndMapSymbols : UtfBlock {
    override val name: String = "Transport and Map Symbols"
    override val meta: String = "0x1f680..0x1f6ff (118/10)"
    override val range: IntRange = 0x1f680..0x1f6fc
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf(0x1f6d8, 0x1f6d9, 0x1f6da, 0x1f6db, 0x1f6ed, 0x1f6ee, 0x1f6ef) }
}
