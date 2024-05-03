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
package org.angproj.aux.io

import org.angproj.aux.buf.innerCopyOfRange

public class Model private constructor(
    size: Int, idxOff: Int, idxEnd: Int
): AbstractModel(size, idxOff, idxEnd) {

    public constructor(size: Int) : this(size, 0, size)

    override fun create(size: Int, idxOff: Int, idxEnd: Int): Model = Model(size, idxOff, idxEnd)

    public companion object {
        public val typeSize: TypeSize = TypeSize.BYTE
    }
}

public fun Model.copyOf(): Model = innerCopyOfRange(0, size)

public fun Model.copyOfRange(idxFrom: Int, idxTo: Int): Model = innerCopyOfRange(idxFrom, idxTo)