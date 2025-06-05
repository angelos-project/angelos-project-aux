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

public object Tags : UtfBlock {
    override val name: String = "Tags"
    override val meta: String = "0xe0000..0xe007f (97/31)"
    override val range: IntRange = 0xe0001..0xe007f
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0xe0002,
            0xe0003,
            0xe0004,
            0xe0005,
            0xe0006,
            0xe0007,
            0xe0008,
            0xe0009,
            0xe000a,
            0xe000b,
            0xe000c,
            0xe000d,
            0xe000e,
            0xe000f,
            0xe0010,
            0xe0011,
            0xe0012,
            0xe0013,
            0xe0014,
            0xe0015,
            0xe0016,
            0xe0017,
            0xe0018,
            0xe0019,
            0xe001a,
            0xe001b,
            0xe001c,
            0xe001d,
            0xe001e,
            0xe001f
        )
    }
}
