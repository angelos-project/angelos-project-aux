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

public object CyrillicExtendedD : UtfBlock {
    override val name: String = "Cyrillic Extended-D"
    override val meta: String = "0x1e030..0x1e08f (63/33)"
    override val range: IntRange = 0x1e030..0x1e08f
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x1e06e,
            0x1e06f,
            0x1e070,
            0x1e071,
            0x1e072,
            0x1e073,
            0x1e074,
            0x1e075,
            0x1e076,
            0x1e077,
            0x1e078,
            0x1e079,
            0x1e07a,
            0x1e07b,
            0x1e07c,
            0x1e07d,
            0x1e07e,
            0x1e07f,
            0x1e080,
            0x1e081,
            0x1e082,
            0x1e083,
            0x1e084,
            0x1e085,
            0x1e086,
            0x1e087,
            0x1e088,
            0x1e089,
            0x1e08a,
            0x1e08b,
            0x1e08c,
            0x1e08d,
            0x1e08e
        )
    }
}
