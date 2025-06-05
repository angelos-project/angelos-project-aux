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

public object CypriotSyllabary : UtfBlock {
    override val name: String = "Cypriot Syllabary"
    override val meta: String = "0x10800..0x1083f (55/9)"
    override val range: IntRange = 0x10800..0x1083f
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x10806,
            0x10807,
            0x10809,
            0x10836,
            0x10839,
            0x1083a,
            0x1083b,
            0x1083d,
            0x1083e
        )
    }
}
