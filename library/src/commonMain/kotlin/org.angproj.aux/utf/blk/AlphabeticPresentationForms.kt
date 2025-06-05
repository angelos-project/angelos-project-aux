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

public object AlphabeticPresentationForms : UtfBlock {
    override val name: String = "Alphabetic Presentation Forms"
    override val meta: String = "0xfb00..0xfb4f (58/22)"
    override val range: IntRange = 0xfb00..0xfb4f
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0xfb07,
            0xfb08,
            0xfb09,
            0xfb0a,
            0xfb0b,
            0xfb0c,
            0xfb0d,
            0xfb0e,
            0xfb0f,
            0xfb10,
            0xfb11,
            0xfb12,
            0xfb18,
            0xfb19,
            0xfb1a,
            0xfb1b,
            0xfb1c,
            0xfb37,
            0xfb3d,
            0xfb3f,
            0xfb42,
            0xfb45
        )
    }
}
