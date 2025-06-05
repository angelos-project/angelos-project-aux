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

public object EthiopicExtended : UtfBlock {
    override val name: String = "Ethiopic Extended"
    override val meta: String = "0x2d80..0x2ddf (79/17)"
    override val range: IntRange = 0x2d80..0x2dde
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x2d97,
            0x2d98,
            0x2d99,
            0x2d9a,
            0x2d9b,
            0x2d9c,
            0x2d9d,
            0x2d9e,
            0x2d9f,
            0x2da7,
            0x2daf,
            0x2db7,
            0x2dbf,
            0x2dc7,
            0x2dcf,
            0x2dd7
        )
    }
}
