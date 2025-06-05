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

public object NyiakengPuachueHmong : UtfBlock {
    override val name: String = "Nyiakeng Puachue Hmong"
    override val meta: String = "0x1e100..0x1e14f (71/9)"
    override val range: IntRange = 0x1e100..0x1e14f
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x1e12d,
            0x1e12e,
            0x1e12f,
            0x1e13e,
            0x1e13f,
            0x1e14a,
            0x1e14b,
            0x1e14c,
            0x1e14d
        )
    }
}
