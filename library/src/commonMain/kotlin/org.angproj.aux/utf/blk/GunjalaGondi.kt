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

public object GunjalaGondi : UtfBlock {
    override val name: String = "Gunjala Gondi"
    override val meta: String = "0x11d60..0x11daf (63/17)"
    override val range: IntRange = 0x11d60..0x11da9
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x11d66,
            0x11d69,
            0x11d8f,
            0x11d92,
            0x11d99,
            0x11d9a,
            0x11d9b,
            0x11d9c,
            0x11d9d,
            0x11d9e,
            0x11d9f
        )
    }
}
