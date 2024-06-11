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

public object LatinExtendedG : UtfBlock {
    override val name: String = "Latin Extended-G"
    override val meta: String = "0x1df00..0x1dfff (37/219)"
    override val range: IntRange = 0x1df00..0x1df2a
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf(0x1df1f, 0x1df20, 0x1df21, 0x1df22, 0x1df23, 0x1df24) }
}
