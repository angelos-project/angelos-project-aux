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

public object SmallKanaExtension : UtfBlock {
    override val name: String = "Small Kana Extension"
    override val meta: String = "0x1b130..0x1b16f (9/55)"
    override val range: IntRange = 0x1b132..0x1b167
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x1b133,
            0x1b134,
            0x1b135,
            0x1b136,
            0x1b137,
            0x1b138,
            0x1b139,
            0x1b13a,
            0x1b13b,
            0x1b13c,
            0x1b13d,
            0x1b13e,
            0x1b13f,
            0x1b140,
            0x1b141,
            0x1b142,
            0x1b143,
            0x1b144,
            0x1b145,
            0x1b146,
            0x1b147,
            0x1b148,
            0x1b149,
            0x1b14a,
            0x1b14b,
            0x1b14c,
            0x1b14d,
            0x1b14e,
            0x1b14f,
            0x1b153,
            0x1b154,
            0x1b156,
            0x1b157,
            0x1b158,
            0x1b159,
            0x1b15a,
            0x1b15b,
            0x1b15c,
            0x1b15d,
            0x1b15e,
            0x1b15f,
            0x1b160,
            0x1b161,
            0x1b162,
            0x1b163
        )
    }
}
