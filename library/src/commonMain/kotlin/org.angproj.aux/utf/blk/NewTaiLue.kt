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

public object NewTaiLue : UtfBlock {
    override val name: String = "New Tai Lue"
    override val meta: String = "0x1980..0x19df (83/13)"
    override val range: IntRange = 0x1980..0x19df
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0x19ac,
            0x19ad,
            0x19ae,
            0x19af,
            0x19ca,
            0x19cb,
            0x19cc,
            0x19cd,
            0x19ce,
            0x19cf,
            0x19db,
            0x19dc,
            0x19dd
        )
    }
}
