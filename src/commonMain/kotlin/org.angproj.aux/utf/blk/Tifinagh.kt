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

public object Tifinagh : UtfBlock {
    override val name: String = "Tifinagh"
    override val meta: String = "0x2d30..0x2d7f (59/21)"
    override val range: IntRange = 0x2d30..0x2d7f
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x2d68,
            0x2d69,
            0x2d6a,
            0x2d6b,
            0x2d6c,
            0x2d6d,
            0x2d6e,
            0x2d71,
            0x2d72,
            0x2d73,
            0x2d74,
            0x2d75,
            0x2d76,
            0x2d77,
            0x2d78,
            0x2d79,
            0x2d7a,
            0x2d7b,
            0x2d7c,
            0x2d7d,
            0x2d7e
        )
    }
}
