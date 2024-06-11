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

public object OldHungarian : UtfBlock {
    override val name: String = "Old Hungarian"
    override val meta: String = "0x10c80..0x10cff (108/20)"
    override val range: IntRange = 0x10c80..0x10cff
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x10cb3,
            0x10cb4,
            0x10cb5,
            0x10cb6,
            0x10cb7,
            0x10cb8,
            0x10cb9,
            0x10cba,
            0x10cbb,
            0x10cbc,
            0x10cbd,
            0x10cbe,
            0x10cbf,
            0x10cf3,
            0x10cf4,
            0x10cf5,
            0x10cf6,
            0x10cf7,
            0x10cf8,
            0x10cf9
        )
    }
}
