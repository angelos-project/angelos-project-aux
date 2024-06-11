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

public object Cham : UtfBlock {
    override val name: String = "Cham"
    override val meta: String = "0xaa00..0xaa5f (83/13)"
    override val range: IntRange = 0xaa00..0xaa5f
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0xaa37,
            0xaa38,
            0xaa39,
            0xaa3a,
            0xaa3b,
            0xaa3c,
            0xaa3d,
            0xaa3e,
            0xaa3f,
            0xaa4e,
            0xaa4f,
            0xaa5a,
            0xaa5b
        )
    }
}
