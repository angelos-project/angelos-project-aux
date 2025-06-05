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

public object Wancho : UtfBlock {
    override val name: String = "Wancho"
    override val meta: String = "0x1e2c0..0x1e2ff (59/5)"
    override val range: IntRange = 0x1e2c0..0x1e2ff
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf(0x1e2fa, 0x1e2fb, 0x1e2fc, 0x1e2fd, 0x1e2fe) }
}
