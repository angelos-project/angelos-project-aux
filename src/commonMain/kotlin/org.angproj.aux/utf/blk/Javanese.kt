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

public object Javanese : UtfBlock {
    override val name: String = "Javanese"
    override val meta: String = "0xa980..0xa9df (91/5)"
    override val range: IntRange = 0xa980..0xa9df
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy { listOf(0xa9ce, 0xa9da, 0xa9db, 0xa9dc, 0xa9dd) }
}
