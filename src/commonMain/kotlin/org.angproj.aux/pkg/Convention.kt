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
package org.angproj.aux.pkg

import org.angproj.aux.num.AbstractBigInt
import org.angproj.aux.num.BigInt
import org.angproj.aux.util.EndianAware
import org.angproj.aux.util.bigIntOf
import org.angproj.io.buf.Retrievable

public enum class Convention(
    public val type: Short,
    //public val load: () -> Unit,
    //public val save: () -> Unit
) {
    BIG_INT(10240),
    UUID4(10239);

    public companion object: EndianAware {
    }
}