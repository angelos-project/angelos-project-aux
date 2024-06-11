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

public object AegeanNumbers : UtfBlock {
    override val name: String = "Aegean Numbers"
    override val meta: String = "0x10100..0x1013f (57/7)"
    override val range: IntRange = 0x10100..0x1013f
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf(0x10103, 0x10104, 0x10105, 0x10106, 0x10134, 0x10135, 0x10136) }
}
