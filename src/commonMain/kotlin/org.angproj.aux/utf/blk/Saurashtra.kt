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

public object Saurashtra : UtfBlock {
    override val name: String = "Saurashtra"
    override val meta: String = "0xa880..0xa8df (82/14)"
    override val range: IntRange = 0xa880..0xa8d9
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf(0xa8c6, 0xa8c7, 0xa8c8, 0xa8c9, 0xa8ca, 0xa8cb, 0xa8cc, 0xa8cd) }
}
