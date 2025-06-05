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

public object Vithkuqi : UtfBlock {
    override val name: String = "Vithkuqi"
    override val meta: String = "0x10570..0x105bf (70/10)"
    override val range: IntRange = 0x10570..0x105bc
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf(0x1057b, 0x1058b, 0x10593, 0x10596, 0x105a2, 0x105b2, 0x105ba) }
}
