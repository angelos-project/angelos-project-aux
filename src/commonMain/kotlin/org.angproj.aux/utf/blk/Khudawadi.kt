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

public object Khudawadi : UtfBlock {
    override val name: String = "Khudawadi"
    override val meta: String = "0x112b0..0x112ff (69/11)"
    override val range: IntRange = 0x112b0..0x112f9
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf(0x112eb, 0x112ec, 0x112ed, 0x112ee, 0x112ef) }
}
