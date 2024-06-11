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

public object OldItalic : UtfBlock {
    override val name: String = "Old Italic"
    override val meta: String = "0x10300..0x1032f (39/9)"
    override val range: IntRange = 0x10300..0x1032f
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x10324,
            0x10325,
            0x10326,
            0x10327,
            0x10328,
            0x10329,
            0x1032a,
            0x1032b,
            0x1032c
        )
    }
}
