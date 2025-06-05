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

public object Rejang : UtfBlock {
    override val name: String = "Rejang"
    override val meta: String = "0xa930..0xa95f (37/11)"
    override val range: IntRange = 0xa930..0xa95f
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0xa954,
            0xa955,
            0xa956,
            0xa957,
            0xa958,
            0xa959,
            0xa95a,
            0xa95b,
            0xa95c,
            0xa95d,
            0xa95e
        )
    }
}
