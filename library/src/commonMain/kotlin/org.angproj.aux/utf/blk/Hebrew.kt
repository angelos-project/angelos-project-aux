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

public object Hebrew : UtfBlock {
    override val name: String = "Hebrew"
    override val meta: String = "0x590..0x5ff (88/24)"
    override val range: IntRange = 0x591..0x5f4
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x5c8,
            0x5c9,
            0x5ca,
            0x5cb,
            0x5cc,
            0x5cd,
            0x5ce,
            0x5cf,
            0x5eb,
            0x5ec,
            0x5ed,
            0x5ee
        )
    }
}
