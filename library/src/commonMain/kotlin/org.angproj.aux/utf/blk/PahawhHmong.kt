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

public object PahawhHmong : UtfBlock {
    override val name: String = "Pahawh Hmong"
    override val meta: String = "0x16b00..0x16b8f (127/17)"
    override val range: IntRange = 0x16b00..0x16b8f
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x16b46,
            0x16b47,
            0x16b48,
            0x16b49,
            0x16b4a,
            0x16b4b,
            0x16b4c,
            0x16b4d,
            0x16b4e,
            0x16b4f,
            0x16b5a,
            0x16b62,
            0x16b78,
            0x16b79,
            0x16b7a,
            0x16b7b,
            0x16b7c
        )
    }
}
