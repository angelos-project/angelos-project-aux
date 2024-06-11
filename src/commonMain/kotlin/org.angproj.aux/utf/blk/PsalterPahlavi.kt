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

public object PsalterPahlavi : UtfBlock {
    override val name: String = "Psalter Pahlavi"
    override val meta: String = "0x10b80..0x10baf (29/19)"
    override val range: IntRange = 0x10b80..0x10baf
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x10b92,
            0x10b93,
            0x10b94,
            0x10b95,
            0x10b96,
            0x10b97,
            0x10b98,
            0x10b9d,
            0x10b9e,
            0x10b9f,
            0x10ba0,
            0x10ba1,
            0x10ba2,
            0x10ba3,
            0x10ba4,
            0x10ba5,
            0x10ba6,
            0x10ba7,
            0x10ba8
        )
    }
}
