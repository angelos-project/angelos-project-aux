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

public object EnclosedAlphanumericSupplement : UtfBlock {
    override val name: String = "Enclosed Alphanumeric Supplement"
    override val meta: String = "0x1f100..0x1f1ff (200/56)"
    override val range: IntRange = 0x1f100..0x1f1ff
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x1f1ae,
            0x1f1af,
            0x1f1b0,
            0x1f1b1,
            0x1f1b2,
            0x1f1b3,
            0x1f1b4,
            0x1f1b5,
            0x1f1b6,
            0x1f1b7,
            0x1f1b8,
            0x1f1b9,
            0x1f1ba,
            0x1f1bb,
            0x1f1bc,
            0x1f1bd,
            0x1f1be,
            0x1f1bf,
            0x1f1c0,
            0x1f1c1,
            0x1f1c2,
            0x1f1c3,
            0x1f1c4,
            0x1f1c5,
            0x1f1c6,
            0x1f1c7,
            0x1f1c8,
            0x1f1c9,
            0x1f1ca,
            0x1f1cb,
            0x1f1cc,
            0x1f1cd,
            0x1f1ce,
            0x1f1cf,
            0x1f1d0,
            0x1f1d1,
            0x1f1d2,
            0x1f1d3,
            0x1f1d4,
            0x1f1d5,
            0x1f1d6,
            0x1f1d7,
            0x1f1d8,
            0x1f1d9,
            0x1f1da,
            0x1f1db,
            0x1f1dc,
            0x1f1dd,
            0x1f1de,
            0x1f1df,
            0x1f1e0,
            0x1f1e1,
            0x1f1e2,
            0x1f1e3,
            0x1f1e4,
            0x1f1e5
        )
    }
}
