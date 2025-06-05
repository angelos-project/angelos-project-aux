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

public object ArabicExtendedB : UtfBlock {
    override val name: String = "Arabic Extended-B"
    override val meta: String = "0x870..0x89f (41/7)"
    override val range: IntRange = 0x870..0x89f
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf(0x88f, 0x892, 0x893, 0x894, 0x895, 0x896, 0x897) }
}
