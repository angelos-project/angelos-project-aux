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

public abstract class AbstractSpeedCopy internal constructor(
    public final override val size: Int,
    public val idxSize: TypeSize,
    @PublishedApi internal val idxOff: Int = 0,
    @PublishedApi internal val idxEnd: Int = idxOff + size
): SpeedCopy {

    public inline val lastIndex: Int
        get() = size-1

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

    internal abstract fun create(size: Int, idxOff: Int, idxEnd: Int): AbstractSpeedCopy

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

    internal open fun getPointer(): Long = -1

    internal open fun getBasePtr(baseIdx: Int): Long = getPointer() + (baseIdx * idxSize.size)

    @PublishedApi
    internal abstract fun speedCopy(ctx: Context): AbstractSpeedCopy

    @PublishedApi
    internal fun calculateContext(idxFrom: Int, idxTo: Int): Context {
        if(idxFrom !in 0.. size || idxTo !in 0.. size) throw IllegalArgumentException("Illegal range.")
        if(idxFrom > idxTo) throw IllegalStateException("Wrong sizes")

        val factor = TypeSize.long / idxSize.size
        val newIdxOff = (idxOff + idxFrom) % factor
        val newSize = idxTo - idxFrom
        val newIdxEnd = newIdxOff + newSize
        val baseIdx = (idxOff + idxFrom) - newIdxOff
        return Context(factor, newIdxOff, newSize, newIdxEnd, baseIdx)
    }

    internal data class Context(
        val factor: Int,
        val newIdxOff: Int,
        val newSize: Int,
        val newIdxEnd: Int,
        val baseIdx: Int
    ) {
        override fun toString(): String = "$factor, $newIdxOff, $newSize, $newIdxEnd, $baseIdx"
    }
}

@PublishedApi
internal inline fun<reified T: AbstractSpeedCopy> T.innerCopyOfRange(idxFrom: Int, idxTo: Int): T {
    val ctx = calculateContext(idxFrom, idxTo)
    return speedCopy(ctx) as T
}

