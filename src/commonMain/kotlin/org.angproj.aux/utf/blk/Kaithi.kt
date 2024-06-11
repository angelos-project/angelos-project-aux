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

public object Kaithi : UtfBlock {
    override val name: String = "Kaithi"
    override val meta: String = "0x11080..0x110cf (68/12)"
    override val range: IntRange = 0x11080..0x110cd
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x110c3,
            0x110c4,
            0x110c5,
            0x110c6,
            0x110c7,
            0x110c8,
            0x110c9,
            0x110ca,
            0x110cb,
            0x110cc
        )
    }
}
