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

public object ZnamennyMusicalNotation : UtfBlock {
    override val name: String = "Znamenny Musical Notation"
    override val meta: String = "0x1cf00..0x1cfcf (185/23)"
    override val range: IntRange = 0x1cf00..0x1cfc3
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x1cf2e,
            0x1cf2f,
            0x1cf47,
            0x1cf48,
            0x1cf49,
            0x1cf4a,
            0x1cf4b,
            0x1cf4c,
            0x1cf4d,
            0x1cf4e,
            0x1cf4f
        )
    }
}
