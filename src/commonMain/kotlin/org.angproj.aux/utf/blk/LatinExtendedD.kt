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

public object LatinExtendedD : UtfBlock {
    override val name: String = "Latin Extended-D"
    override val meta: String = "0xa720..0xa7ff (193/31)"
    override val range: IntRange = 0xa720..0xa7ff
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0xa7cb,
            0xa7cc,
            0xa7cd,
            0xa7ce,
            0xa7cf,
            0xa7d2,
            0xa7d4,
            0xa7da,
            0xa7db,
            0xa7dc,
            0xa7dd,
            0xa7de,
            0xa7df,
            0xa7e0,
            0xa7e1,
            0xa7e2,
            0xa7e3,
            0xa7e4,
            0xa7e5,
            0xa7e6,
            0xa7e7,
            0xa7e8,
            0xa7e9,
            0xa7ea,
            0xa7eb,
            0xa7ec,
            0xa7ed,
            0xa7ee,
            0xa7ef,
            0xa7f0,
            0xa7f1
        )
    }
}
