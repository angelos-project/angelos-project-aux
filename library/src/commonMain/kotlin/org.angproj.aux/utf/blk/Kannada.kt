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

public object Kannada : UtfBlock {
    override val name: String = "Kannada"
    override val meta: String = "0xc80..0xcff (91/37)"
    override val range: IntRange = 0xc80..0xcf3
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0xc8d,
            0xc91,
            0xca9,
            0xcb4,
            0xcba,
            0xcbb,
            0xcc5,
            0xcc9,
            0xcce,
            0xccf,
            0xcd0,
            0xcd1,
            0xcd2,
            0xcd3,
            0xcd4,
            0xcd7,
            0xcd8,
            0xcd9,
            0xcda,
            0xcdb,
            0xcdc,
            0xcdf,
            0xce4,
            0xce5,
            0xcf0
        )
    }
}
