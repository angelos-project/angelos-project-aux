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

public object Tamil : UtfBlock {
    override val name: String = "Tamil"
    override val meta: String = "0xb80..0xbff (72/56)"
    override val range: IntRange = 0xb82..0xbfa
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0xb84,
            0xb8b,
            0xb8c,
            0xb8d,
            0xb91,
            0xb96,
            0xb97,
            0xb98,
            0xb9b,
            0xb9d,
            0xba0,
            0xba1,
            0xba2,
            0xba5,
            0xba6,
            0xba7,
            0xbab,
            0xbac,
            0xbad,
            0xbba,
            0xbbb,
            0xbbc,
            0xbbd,
            0xbc3,
            0xbc4,
            0xbc5,
            0xbc9,
            0xbce,
            0xbcf,
            0xbd1,
            0xbd2,
            0xbd3,
            0xbd4,
            0xbd5,
            0xbd6,
            0xbd8,
            0xbd9,
            0xbda,
            0xbdb,
            0xbdc,
            0xbdd,
            0xbde,
            0xbdf,
            0xbe0,
            0xbe1,
            0xbe2,
            0xbe3,
            0xbe4,
            0xbe5
        )
    }
}
