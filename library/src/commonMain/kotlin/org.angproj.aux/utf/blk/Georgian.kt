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

public object Georgian : UtfBlock {
    override val name: String = "Georgian"
    override val meta: String = "0x10a0..0x10ff (88/8)"
    override val range: IntRange = 0x10a0..0x10ff
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf(0x10c6, 0x10c8, 0x10c9, 0x10ca, 0x10cb, 0x10cc, 0x10ce, 0x10cf) }
}
