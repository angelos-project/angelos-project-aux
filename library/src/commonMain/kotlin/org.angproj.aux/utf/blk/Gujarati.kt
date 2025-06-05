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

public object Gujarati : UtfBlock {
    override val name: String = "Gujarati"
    override val meta: String = "0xa80..0xaff (91/37)"
    override val range: IntRange = 0xa81..0xaff
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0xa84,
            0xa8e,
            0xa92,
            0xaa9,
            0xab1,
            0xab4,
            0xaba,
            0xabb,
            0xac6,
            0xaca,
            0xace,
            0xacf,
            0xad1,
            0xad2,
            0xad3,
            0xad4,
            0xad5,
            0xad6,
            0xad7,
            0xad8,
            0xad9,
            0xada,
            0xadb,
            0xadc,
            0xadd,
            0xade,
            0xadf,
            0xae4,
            0xae5,
            0xaf2,
            0xaf3,
            0xaf4,
            0xaf5,
            0xaf6,
            0xaf7,
            0xaf8
        )
    }
}
