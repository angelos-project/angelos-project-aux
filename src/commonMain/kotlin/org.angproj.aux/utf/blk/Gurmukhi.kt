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

public object Gurmukhi : UtfBlock {
    override val name: String = "Gurmukhi"
    override val meta: String = "0xa00..0xa7f (80/48)"
    override val range: IntRange = 0xa01..0xa76
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0xa04,
            0xa0b,
            0xa0c,
            0xa0d,
            0xa0e,
            0xa11,
            0xa12,
            0xa29,
            0xa31,
            0xa34,
            0xa37,
            0xa3a,
            0xa3b,
            0xa3d,
            0xa43,
            0xa44,
            0xa45,
            0xa46,
            0xa49,
            0xa4a,
            0xa4e,
            0xa4f,
            0xa50,
            0xa52,
            0xa53,
            0xa54,
            0xa55,
            0xa56,
            0xa57,
            0xa58,
            0xa5d,
            0xa5f,
            0xa60,
            0xa61,
            0xa62,
            0xa63,
            0xa64,
            0xa65
        )
    }
}
