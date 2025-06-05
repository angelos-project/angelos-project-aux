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

public object Dogra : UtfBlock {
    override val name: String = "Dogra"
    override val meta: String = "0x11800..0x1184f (60/20)"
    override val range: IntRange = 0x11800..0x1183b
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf() }
}
