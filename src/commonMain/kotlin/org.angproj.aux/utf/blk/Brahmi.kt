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

public object Brahmi : UtfBlock {
    override val name: String = "Brahmi"
    override val meta: String = "0x11000..0x1107f (115/13)"
    override val range: IntRange = 0x11000..0x1107f
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x1104e,
            0x1104f,
            0x11050,
            0x11051,
            0x11076,
            0x11077,
            0x11078,
            0x11079,
            0x1107a,
            0x1107b,
            0x1107c,
            0x1107d,
            0x1107e
        )
    }
}
