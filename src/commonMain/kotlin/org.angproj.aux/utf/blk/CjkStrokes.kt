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

public object CjkStrokes : UtfBlock {
    override val name: String = "CJK Strokes"
    override val meta: String = "0x31c0..0x31ef (37/11)"
    override val range: IntRange = 0x31c0..0x31ef
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x31e4,
            0x31e5,
            0x31e6,
            0x31e7,
            0x31e8,
            0x31e9,
            0x31ea,
            0x31eb,
            0x31ec,
            0x31ed,
            0x31ee
        )
    }
}
