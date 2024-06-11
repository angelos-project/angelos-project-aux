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

public object ArabicPresentationFormsA : UtfBlock {
    override val name: String = "Arabic Presentation Forms-A"
    override val meta: String = "0xfb50..0xfdff (631/57)"
    override val range: IntRange = 0xfb50..0xfdff
    override val noCtrl: Boolean = false
    override val noUse: List<Int> by lazy {
        listOf(
            0xfbc3,
            0xfbc4,
            0xfbc5,
            0xfbc6,
            0xfbc7,
            0xfbc8,
            0xfbc9,
            0xfbca,
            0xfbcb,
            0xfbcc,
            0xfbcd,
            0xfbce,
            0xfbcf,
            0xfbd0,
            0xfbd1,
            0xfbd2,
            0xfd90,
            0xfd91,
            0xfdc8,
            0xfdc9,
            0xfdca,
            0xfdcb,
            0xfdcc,
            0xfdcd,
            0xfdce,
            0xfdd0,
            0xfdd1,
            0xfdd2,
            0xfdd3,
            0xfdd4,
            0xfdd5,
            0xfdd6,
            0xfdd7,
            0xfdd8,
            0xfdd9,
            0xfdda,
            0xfddb,
            0xfddc,
            0xfddd,
            0xfdde,
            0xfddf,
            0xfde0,
            0xfde1,
            0xfde2,
            0xfde3,
            0xfde4,
            0xfde5,
            0xfde6,
            0xfde7,
            0xfde8,
            0xfde9,
            0xfdea,
            0xfdeb,
            0xfdec,
            0xfded,
            0xfdee,
            0xfdef
        )
    }
}
