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

public object MasaramGondi : UtfBlock {
    override val name: String = "Masaram Gondi"
    override val meta: String = "0x11d00..0x11d5f (75/21)"
    override val range: IntRange = 0x11d00..0x11d59
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x11d07,
            0x11d0a,
            0x11d37,
            0x11d38,
            0x11d39,
            0x11d3b,
            0x11d3e,
            0x11d48,
            0x11d49,
            0x11d4a,
            0x11d4b,
            0x11d4c,
            0x11d4d,
            0x11d4e,
            0x11d4f
        )
    }
}
