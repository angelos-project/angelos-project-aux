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

public object OldPersian : UtfBlock {
    override val name: String = "Old Persian"
    override val meta: String = "0x103a0..0x103df (50/14)"
    override val range: IntRange = 0x103a0..0x103d5
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf(0x103c4, 0x103c5, 0x103c6, 0x103c7) }
}
