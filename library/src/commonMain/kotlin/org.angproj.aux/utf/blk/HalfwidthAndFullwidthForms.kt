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

public object HalfwidthAndFullwidthForms : UtfBlock {
    override val name: String = "Halfwidth and Fullwidth Forms"
    override val meta: String = "0xff00..0xffef (225/15)"
    override val range: IntRange = 0xff01..0xffee
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0xffbf,
            0xffc0,
            0xffc1,
            0xffc8,
            0xffc9,
            0xffd0,
            0xffd1,
            0xffd8,
            0xffd9,
            0xffdd,
            0xffde,
            0xffdf,
            0xffe7
        )
    }
}
