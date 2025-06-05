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

public object Limbu : UtfBlock {
    override val name: String = "Limbu"
    override val meta: String = "0x1900..0x194f (68/12)"
    override val range: IntRange = 0x1900..0x194f
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x191f,
            0x192c,
            0x192d,
            0x192e,
            0x192f,
            0x193c,
            0x193d,
            0x193e,
            0x193f,
            0x1941,
            0x1942,
            0x1943
        )
    }
}
