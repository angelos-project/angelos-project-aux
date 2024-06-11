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

public object Duployan : UtfBlock {
    override val name: String = "Duployan"
    override val meta: String = "0x1bc00..0x1bc9f (143/17)"
    override val range: IntRange = 0x1bc00..0x1bc9f
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x1bc6b,
            0x1bc6c,
            0x1bc6d,
            0x1bc6e,
            0x1bc6f,
            0x1bc7d,
            0x1bc7e,
            0x1bc7f,
            0x1bc89,
            0x1bc8a,
            0x1bc8b,
            0x1bc8c,
            0x1bc8d,
            0x1bc8e,
            0x1bc8f,
            0x1bc9a,
            0x1bc9b
        )
    }
}
