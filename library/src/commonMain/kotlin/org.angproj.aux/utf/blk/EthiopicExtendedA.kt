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

public object EthiopicExtendedA : UtfBlock {
    override val name: String = "Ethiopic Extended-A"
    override val meta: String = "0xab00..0xab2f (32/16)"
    override val range: IntRange = 0xab01..0xab2e
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0xab07,
            0xab08,
            0xab0f,
            0xab10,
            0xab17,
            0xab18,
            0xab19,
            0xab1a,
            0xab1b,
            0xab1c,
            0xab1d,
            0xab1e,
            0xab1f,
            0xab27
        )
    }
}
