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

public object Oriya : UtfBlock {
    override val name: String = "Oriya"
    override val meta: String = "0xb00..0xb7f (91/37)"
    override val range: IntRange = 0xb01..0xb77
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0xb04,
            0xb0d,
            0xb0e,
            0xb11,
            0xb12,
            0xb29,
            0xb31,
            0xb34,
            0xb3a,
            0xb3b,
            0xb45,
            0xb46,
            0xb49,
            0xb4a,
            0xb4e,
            0xb4f,
            0xb50,
            0xb51,
            0xb52,
            0xb53,
            0xb54,
            0xb58,
            0xb59,
            0xb5a,
            0xb5b,
            0xb5e,
            0xb64,
            0xb65
        )
    }
}
