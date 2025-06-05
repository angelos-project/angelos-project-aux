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

public object Sinhala : UtfBlock {
    override val name: String = "Sinhala"
    override val meta: String = "0xd80..0xdff (91/37)"
    override val range: IntRange = 0xd81..0xdf4
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0xd84,
            0xd97,
            0xd98,
            0xd99,
            0xdb2,
            0xdbc,
            0xdbe,
            0xdbf,
            0xdc7,
            0xdc8,
            0xdc9,
            0xdcb,
            0xdcc,
            0xdcd,
            0xdce,
            0xdd5,
            0xdd7,
            0xde0,
            0xde1,
            0xde2,
            0xde3,
            0xde4,
            0xde5,
            0xdf0,
            0xdf1
        )
    }
}
