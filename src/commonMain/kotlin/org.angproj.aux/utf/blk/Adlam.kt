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

public object Adlam : UtfBlock {
    override val name: String = "Adlam"
    override val meta: String = "0x1e900..0x1e95f (88/8)"
    override val range: IntRange = 0x1e900..0x1e95f
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x1e94c,
            0x1e94d,
            0x1e94e,
            0x1e94f,
            0x1e95a,
            0x1e95b,
            0x1e95c,
            0x1e95d
        )
    }
}
