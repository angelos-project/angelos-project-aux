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

public object TaiTham : UtfBlock {
    override val name: String = "Tai Tham"
    override val meta: String = "0x1a20..0x1aaf (127/17)"
    override val range: IntRange = 0x1a20..0x1aad
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x1a5f,
            0x1a7d,
            0x1a7e,
            0x1a8a,
            0x1a8b,
            0x1a8c,
            0x1a8d,
            0x1a8e,
            0x1a8f,
            0x1a9a,
            0x1a9b,
            0x1a9c,
            0x1a9d,
            0x1a9e,
            0x1a9f
        )
    }
}
