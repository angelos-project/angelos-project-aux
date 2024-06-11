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

public object SupplementalArrowsC : UtfBlock {
    override val name: String = "Supplemental Arrows-C"
    override val meta: String = "0x1f800..0x1f8ff (150/106)"
    override val range: IntRange = 0x1f800..0x1f8b1
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x1f80c,
            0x1f80d,
            0x1f80e,
            0x1f80f,
            0x1f848,
            0x1f849,
            0x1f84a,
            0x1f84b,
            0x1f84c,
            0x1f84d,
            0x1f84e,
            0x1f84f,
            0x1f85a,
            0x1f85b,
            0x1f85c,
            0x1f85d,
            0x1f85e,
            0x1f85f,
            0x1f888,
            0x1f889,
            0x1f88a,
            0x1f88b,
            0x1f88c,
            0x1f88d,
            0x1f88e,
            0x1f88f,
            0x1f8ae,
            0x1f8af
        )
    }
}
