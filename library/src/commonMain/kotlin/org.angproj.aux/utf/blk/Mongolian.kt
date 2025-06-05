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

public object Mongolian : UtfBlock {
    override val name: String = "Mongolian"
    override val meta: String = "0x1800..0x18af (158/18)"
    override val range: IntRange = 0x1800..0x18aa
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x181a,
            0x181b,
            0x181c,
            0x181d,
            0x181e,
            0x181f,
            0x1879,
            0x187a,
            0x187b,
            0x187c,
            0x187d,
            0x187e,
            0x187f
        )
    }
}
