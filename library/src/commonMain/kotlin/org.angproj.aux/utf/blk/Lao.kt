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

public object Lao : UtfBlock {
    override val name: String = "Lao"
    override val meta: String = "0xe80..0xeff (83/45)"
    override val range: IntRange = 0xe81..0xedf
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0xe83,
            0xe85,
            0xe8b,
            0xea4,
            0xea6,
            0xebe,
            0xebf,
            0xec5,
            0xec7,
            0xecf,
            0xeda,
            0xedb
        )
    }
}
