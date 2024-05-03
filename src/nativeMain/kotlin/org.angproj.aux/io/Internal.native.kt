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

import kotlinx.cinterop.*
import org.angproj.aux.buf.AbstractSpeedCopy

@OptIn(ExperimentalForeignApi::class)
internal actual inline fun <reified T : AbstractSpeedCopy> T.longCopy(
    basePtr: Long,
    copyPtr: Long,
    offset: Int
) {
    (copyPtr + offset).toCPointer<LongVar>()!!.pointed.value = (
            basePtr + offset).toCPointer<LongVar>()!!.pointed.value
}

internal actual inline fun <reified T : AbstractSpeedCopy> T.innerMemCopyOfRange(
    idxFrom: Int,
    idxTo: Int
): T {
    checkWithin(idxFrom, idxTo)
    val factor = calcFact(idxFrom, idxTo)
    val newIdxOff = calcNewIdxOff(idxFrom, factor)
    val newSize = calcNewSize(idxFrom, idxTo)
    val newIdxEnd = calcNewIdxEnd(newIdxOff, newSize)
    val baseIdx = calcBaseIdx(idxFrom, newIdxOff)

    val copy = create(newSize, newIdxOff, newIdxEnd) as T

    val basePtr = getBasePtr(baseIdx)
    val copyPtr = copy.getPointer()

    (0 until copy.length step TypeSize.long).forEach { longCopy(copyPtr, basePtr, it) }
    return copy
}