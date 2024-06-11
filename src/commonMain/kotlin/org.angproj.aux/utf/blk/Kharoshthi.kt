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

public object Kharoshthi : UtfBlock {
    override val name: String = "Kharoshthi"
    override val meta: String = "0x10a00..0x10a5f (68/28)"
    override val range: IntRange = 0x10a00..0x10a58
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x10a04,
            0x10a07,
            0x10a08,
            0x10a09,
            0x10a0a,
            0x10a0b,
            0x10a14,
            0x10a18,
            0x10a36,
            0x10a37,
            0x10a3b,
            0x10a3c,
            0x10a3d,
            0x10a3e,
            0x10a49,
            0x10a4a,
            0x10a4b,
            0x10a4c,
            0x10a4d,
            0x10a4e,
            0x10a4f
        )
    }
}
