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

public object IndicSiyaqNumbers : UtfBlock {
    override val name: String = "Indic Siyaq Numbers"
    override val meta: String = "0x1ec70..0x1ecbf (68/12)"
    override val range: IntRange = 0x1ec71..0x1ecb4
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf() }
}
