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

public class Bytes private constructor(
    size: Int, idxOff: Int, idxEnd: Int
): AbstractBytes(size, idxOff, idxEnd) {

    public constructor(size: Int) : this(size, 0, size)

    override fun create(size: Int, idxOff: Int, idxEnd: Int): Bytes = Bytes(size, idxOff, idxEnd)

    /*protected fun copyOfRange2(idxFrom: Int, idxTo: Int): Bytes {
        val factor = TypeSize.long / idxSize.size
        //val newIdxOff = idxFrom % factor
        val newIdxOff = (idxOff + idxFrom) % factor
        val newSize = idxTo - idxFrom
        val newIdxEnd = newIdxOff + newSize
        val baseIdx = (idxOff + idxFrom) - newIdxOff

        val copy = create(newSize, newIdxOff, newIdxEnd)

        val basePtr = (baseIdx * idxSize.size) + newIdxOff
        val copyPtr = newIdxOff

        data.copyInto(copy.data, copyPtr, basePtr, basePtr + newSize)
        return copy
    }*/
}

public fun Bytes.copyOfRange(
    idxFrom: Int,
    idxTo: Int
): Bytes = innerCopyOfRange(idxFrom, idxTo)

public fun Bytes.copyOf(): Bytes = innerCopyOfRange(0, size)