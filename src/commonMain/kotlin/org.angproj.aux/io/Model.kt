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

import org.angproj.aux.buf.Reify
import org.angproj.aux.buf.innerCopyOfRange

public class Model private constructor(
    size: Int, idxOff: Int, idxEnd: Int
): AbstractModel(size, idxOff, idxEnd) {

    public constructor(size: Int) : this(size, 0, size)

    override fun create(size: Int, idxOff: Int, idxEnd: Int): Model = Model(size, idxOff, idxEnd)

    /*protected fun copyOfRange2(idxFrom: Int, idxTo: Int): Model {
        val factor = TypeSize.long / idxSize.size
        val newIdxOff = (idxOff + idxFrom) % factor
        val newSize = idxTo - idxFrom
        val newIdxEnd = newIdxOff + newSize
        val baseIdx = (idxOff + idxFrom) - newIdxOff

        val copy = create(newSize, newIdxOff, newIdxEnd)

        val basePtr = baseIdx / TypeSize.long
        val copyPtr = 0

        (0 until copy.length / TypeSize.long).forEach {
            copy.data[copyPtr + it] = data[basePtr + it]
        }
        return copy
    }*/

    public override fun close() { }

    public companion object {
        public val typeSize: TypeSize = TypeSize.BYTE
    }
}

public fun Model.copyOf(): Model = innerCopyOfRange(0, size)

public fun Model.copyOfRange(idxFrom: Int, idxTo: Int): Model = innerCopyOfRange(idxFrom, idxTo)