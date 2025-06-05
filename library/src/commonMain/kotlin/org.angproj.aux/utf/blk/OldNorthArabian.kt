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

public object OldNorthArabian : UtfBlock {
    override val name: String = "Old North Arabian"
    override val meta: String = "0x10a80..0x10a9f (32/0)"
    override val range: IntRange = 0x10a80..0x10a9f
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf() }
}
