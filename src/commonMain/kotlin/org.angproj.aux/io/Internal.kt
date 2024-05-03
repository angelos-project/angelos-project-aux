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

import org.angproj.aux.buf.AbstractSpeedCopy

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


internal inline fun<reified T: AbstractSpeedCopy> T.checkWithin(idxFrom: Int, idxTo: Int) {
    if(idxFrom !in 0..< size || idxTo !in 0..< size) throw IllegalArgumentException("Illegal range.")
    if(idxFrom > idxTo) throw IllegalStateException("Wrong sizes")
}

internal inline fun<reified T: AbstractSpeedCopy> T.calcFact(idxFrom: Int, idxTo: Int): Int = TypeSize.long / idxSize.size
internal inline fun<reified T: AbstractSpeedCopy> T.calcNewIdxOff(idxFrom: Int, factor: Int): Int = (idxOff + idxFrom) % factor
internal inline fun<reified T: AbstractSpeedCopy> T.calcNewSize(idxFrom: Int, idxTo: Int): Int = idxTo - idxFrom
internal inline fun<reified T: AbstractSpeedCopy> T.calcNewIdxEnd(newIdxOff: Int, newSize: Int): Int = newIdxOff + newSize
internal inline fun<reified T: AbstractSpeedCopy> T.calcBaseIdx(idxFrom: Int, newIdxOff: Int): Int = (idxOff + idxFrom) - newIdxOff

internal expect inline fun<reified T: AbstractSpeedCopy> T.longCopy(basePtr: Long, copyPtr: Long, offset: Int)

internal expect inline fun<reified T: AbstractSpeedCopy> T.innerMemCopyOfRange(
    idxFrom: Int, idxTo: Int
): T

/*internal inline fun<reified T: Bytes> T.innerByteCopyOfRange(idxFrom: Int, idxTo: Int): T {
    checkWithin(idxFrom, idxTo)
    val factor = calcFact(idxFrom, idxTo)
    val newIdxOff = calcNewIdxOff(idxFrom, factor)
    val newSize = calcNewSize(idxFrom, idxTo)
    val newIdxEnd = calcNewIdxEnd(newIdxOff, newSize)
    val baseIdx = calcBaseIdx(idxFrom, newIdxOff)

    val copy = create(newSize, newIdxOff, newIdxEnd) as T

    val basePtr = (baseIdx * idxSize.size) + newIdxOff
    val copyPtr = newIdxOff

    data.copyInto(copy.data, copyPtr, basePtr, basePtr + newSize)
    return copy
}*/

/*internal inline fun<reified T: Model> T.innerLongCopyOfRange(idxFrom: Int, idxTo: Int): T {
    checkWithin(idxFrom, idxTo)
    val factor = calcFact(idxFrom, idxTo)
    val newIdxOff = calcNewIdxOff(idxFrom, factor)
    val newSize = calcNewSize(idxFrom, idxTo)
    val newIdxEnd = calcNewIdxEnd(newIdxOff, newSize)
    val baseIdx = calcBaseIdx(idxFrom, newIdxOff)

    val copy = create(newSize, newIdxOff, newIdxEnd) as T

    val basePtr = baseIdx / TypeSize.long
    val copyPtr = 0

    (0 until copy.length / TypeSize.long).forEach {
        copy.data[copyPtr + it] = data[basePtr + it]
    }
    return copy
}*/