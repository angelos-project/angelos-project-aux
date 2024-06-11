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

public object Telugu : UtfBlock {
    override val name: String = "Telugu"
    override val meta: String = "0xc00..0xc7f (100/28)"
    override val range: IntRange = 0xc00..0xc7f
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0xc0d,
            0xc11,
            0xc29,
            0xc3a,
            0xc3b,
            0xc45,
            0xc49,
            0xc4e,
            0xc4f,
            0xc50,
            0xc51,
            0xc52,
            0xc53,
            0xc54,
            0xc57,
            0xc5b,
            0xc5c,
            0xc5e,
            0xc5f,
            0xc64,
            0xc65,
            0xc70,
            0xc71,
            0xc72,
            0xc73,
            0xc74,
            0xc75,
            0xc76
        )
    }
}
