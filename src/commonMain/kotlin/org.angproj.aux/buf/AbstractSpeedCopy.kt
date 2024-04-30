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
package org.angproj.aux.buf

import org.angproj.aux.io.TypeSize

public abstract class AbstractSpeedCopy protected constructor(
    public final override val size: Int,
    public val idxSize: TypeSize,
    protected val idxOff: Int = 0,
    protected val idxEnd: Int = idxOff + size
): SpeedCopy {

    public inline fun <reified T: Reifiable> Int.checkRange(): Unit = checkRangeByte<Reify>()

    public inline fun <reified T: Reifiable> Int.checkRangeByte(): Unit = when(this) {
        !in 0..<size -> throw IllegalArgumentException("Out of bounds.")
        else -> Unit
    }

    public inline fun <reified T: Reifiable> Int.checkRangeShort(): Unit = when(this) {
        !in 0..<size-1 -> throw IllegalArgumentException("Out of bounds.")
        else -> Unit
    }

    public inline fun <reified T: Reifiable> Int.checkRangeInt(): Unit = when(this) {
        !in 0..<size-3 -> throw IllegalArgumentException("Out of bounds.")
        else -> Unit
    }

    public inline fun <reified T: Reifiable> Int.checkRangeLong(): Unit = when(this) {
        !in 0..<size-7 -> throw IllegalArgumentException("Out of bounds.")
        else -> Unit
    }

    protected abstract fun create(size: Int, idxOff: Int, idxEnd: Int): AbstractSpeedCopy
    public abstract override fun copyOf(): AbstractSpeedCopy
    public abstract override fun copyOfRange(idxFrom: Int, idxTo: Int): AbstractSpeedCopy

    protected fun innerCopyOfRange(
        idxFrom: Int,
        idxTo: Int,
        action: (
            basePtr: Long,
            copyPtr: Long,
            offset: Int
                ) -> Unit
    ): AbstractSpeedCopy {
        val factor = TypeSize.long / idxSize.size

        val newIdxOff = (idxOff + idxFrom) % factor
        val newSize = idxTo - idxFrom
        val newIdxEnd = newIdxOff + newSize
        val baseIdx = (idxOff + idxFrom) - newIdxOff

        val copy = create(newSize, newIdxOff, newIdxEnd)

        val basePtr = getBasePtr(baseIdx)
        val copyPtr = copy.getPointer()

        (0 until copy.length step TypeSize.long).forEach { action(copyPtr, basePtr, it) }
        return copy
    }

    protected open fun getPointer(): Long = -1

    protected open fun getBasePtr(baseIdx: Int): Long = getPointer() + (baseIdx * idxSize.size)

    /*protected fun copyOfRange4(idxFrom: Int, idxTo: Int): Model {
        val basePtr = baseIdx / TypeSize.long
        val copyPtr = 0

        (0 until copy.length / TypeSize.long).forEach {
            copy.data[copyPtr + it] = data[basePtr + it]
        }
        return copy
    }*/
}
