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

public object CombiningHalfMarks : UtfBlock {
    override val name: String = "Combining Half Marks"
    override val meta: String = "0xfe20..0xfe2f (16/0)"
    override val range: IntRange = 0xfe20..0xfe2f
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf() }
}
