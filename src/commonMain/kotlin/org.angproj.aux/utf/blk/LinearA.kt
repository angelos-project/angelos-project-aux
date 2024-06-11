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

public object LinearA : UtfBlock {
    override val name: String = "Linear A"
    override val meta: String = "0x10600..0x1077f (341/43)"
    override val range: IntRange = 0x10600..0x10767
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x10737,
            0x10738,
            0x10739,
            0x1073a,
            0x1073b,
            0x1073c,
            0x1073d,
            0x1073e,
            0x1073f,
            0x10756,
            0x10757,
            0x10758,
            0x10759,
            0x1075a,
            0x1075b,
            0x1075c,
            0x1075d,
            0x1075e,
            0x1075f
        )
    }
}
