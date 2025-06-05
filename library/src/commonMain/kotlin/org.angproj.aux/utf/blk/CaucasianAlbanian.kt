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

public object CaucasianAlbanian : UtfBlock {
    override val name: String = "Caucasian Albanian"
    override val meta: String = "0x10530..0x1056f (53/11)"
    override val range: IntRange = 0x10530..0x1056f
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x10564,
            0x10565,
            0x10566,
            0x10567,
            0x10568,
            0x10569,
            0x1056a,
            0x1056b,
            0x1056c,
            0x1056d,
            0x1056e
        )
    }
}
