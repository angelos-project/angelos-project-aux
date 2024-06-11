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

public object GreekExtended : UtfBlock {
    override val name: String = "Greek Extended"
    override val meta: String = "0x1f00..0x1fff (233/23)"
    override val range: IntRange = 0x1f00..0x1ffe
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x1f16,
            0x1f17,
            0x1f1e,
            0x1f1f,
            0x1f46,
            0x1f47,
            0x1f4e,
            0x1f4f,
            0x1f58,
            0x1f5a,
            0x1f5c,
            0x1f5e,
            0x1f7e,
            0x1f7f,
            0x1fb5,
            0x1fc5,
            0x1fd4,
            0x1fd5,
            0x1fdc,
            0x1ff0,
            0x1ff1,
            0x1ff5
        )
    }
}
