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

public object TaiViet : UtfBlock {
    override val name: String = "Tai Viet"
    override val meta: String = "0xaa80..0xaadf (72/24)"
    override val range: IntRange = 0xaa80..0xaadf
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0xaac3,
            0xaac4,
            0xaac5,
            0xaac6,
            0xaac7,
            0xaac8,
            0xaac9,
            0xaaca,
            0xaacb,
            0xaacc,
            0xaacd,
            0xaace,
            0xaacf,
            0xaad0,
            0xaad1,
            0xaad2,
            0xaad3,
            0xaad4,
            0xaad5,
            0xaad6,
            0xaad7,
            0xaad8,
            0xaad9,
            0xaada
        )
    }
}
