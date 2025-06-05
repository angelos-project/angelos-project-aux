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

public object Malayalam : UtfBlock {
    override val name: String = "Malayalam"
    override val meta: String = "0xd00..0xd7f (118/10)"
    override val range: IntRange = 0xd00..0xd7f
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0xd0d,
            0xd11,
            0xd45,
            0xd49,
            0xd50,
            0xd51,
            0xd52,
            0xd53,
            0xd64,
            0xd65
        )
    }
}
