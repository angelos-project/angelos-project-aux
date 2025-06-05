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

public object Multani : UtfBlock {
    override val name: String = "Multani"
    override val meta: String = "0x11280..0x112af (38/10)"
    override val range: IntRange = 0x11280..0x112a9
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf(0x11287, 0x11289, 0x1128e, 0x1129e) }
}
