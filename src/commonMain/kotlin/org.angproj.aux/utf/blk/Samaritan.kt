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

public object Samaritan : UtfBlock {
    override val name: String = "Samaritan"
    override val meta: String = "0x800..0x83f (61/3)"
    override val range: IntRange = 0x800..0x83e
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf(0x82e, 0x82f) }
}
