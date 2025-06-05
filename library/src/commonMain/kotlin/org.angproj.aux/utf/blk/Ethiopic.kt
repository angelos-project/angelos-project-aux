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

public object Ethiopic : UtfBlock {
    override val name: String = "Ethiopic"
    override val meta: String = "0x1200..0x137f (358/26)"
    override val range: IntRange = 0x1200..0x137c
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x1249,
            0x124e,
            0x124f,
            0x1257,
            0x1259,
            0x125e,
            0x125f,
            0x1289,
            0x128e,
            0x128f,
            0x12b1,
            0x12b6,
            0x12b7,
            0x12bf,
            0x12c1,
            0x12c6,
            0x12c7,
            0x12d7,
            0x1311,
            0x1316,
            0x1317,
            0x135b,
            0x135c
        )
    }
}
