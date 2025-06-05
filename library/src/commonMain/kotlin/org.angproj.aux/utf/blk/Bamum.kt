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

public object Bamum : UtfBlock {
    override val name: String = "Bamum"
    override val meta: String = "0xa6a0..0xa6ff (88/8)"
    override val range: IntRange = 0xa6a0..0xa6f7
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf() }
}
