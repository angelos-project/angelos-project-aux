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
import org.angproj.aux.util.Reifiable
import org.angproj.aux.util.Reify

public abstract class AbstractSpeedCopy internal constructor(
    public final override val size: Int,
    public val idxSize: TypeSize,
    @PublishedApi internal val idxLimit: Int = size
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

    internal abstract fun create(size: Int, idxLimit: Int): AbstractSpeedCopy

    internal open fun getPointer(): Long = -1

    internal open fun getBasePtr(baseIdx: Int): Long = getPointer() + (baseIdx * idxSize.size)

    @PublishedApi
    internal fun <T: AbstractSpeedCopy> calculateInto(dest: T, destOff: Int, idxFrom: Int, idxTo: Int) {
        // TODO()
    }
}