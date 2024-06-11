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

public object PrivateUseArea : UtfBlock {
    override val name: String = "Private Use Area"
    override val meta: String = "0xe000..0xf8ff (0/6400)"
    override val range: IntRange = 0xe000..0xf8ff
    override val noCtrl: Boolean = true
    override val noUse: List<Int> by lazy { listOf() }
}
