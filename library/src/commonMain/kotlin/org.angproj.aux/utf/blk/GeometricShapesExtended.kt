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

public object GeometricShapesExtended : UtfBlock {
    override val name: String = "Geometric Shapes Extended"
    override val meta: String = "0x1f780..0x1f7ff (103/25)"
    override val range: IntRange = 0x1f780..0x1f7f0
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x1f7da,
            0x1f7db,
            0x1f7dc,
            0x1f7dd,
            0x1f7de,
            0x1f7df,
            0x1f7ec,
            0x1f7ed,
            0x1f7ee,
            0x1f7ef
        )
    }
}
