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

    @PublishedApi
    internal open fun <T: AbstractSpeedCopy> calculateInto(dest: T, destOff: Int, idxFrom: Int, idxTo: Int) {
        throw UnsupportedOperationException("Each implementing class must reimplement this method.") }
}

public fun<T: AbstractSpeedCopy> T.copyInto(destination: T, destinationOffset: Int, fromIndex: Int, toIndex: Int) {
    calculateInto(destination, destinationOffset, fromIndex, toIndex)
}

public fun<T: AbstractSpeedCopy> T.copyOfRange(fromIndex: Int, toIndex: Int): T {
    val copy = create(toIndex - fromIndex, toIndex - fromIndex)
    calculateInto(copy, 0, fromIndex, toIndex)
    @Suppress("UNCHECKED_CAST")
    return copy as T
}

public fun<T: AbstractSpeedCopy> T.copyOf(): T {
    val copy = create(this.size, this.idxLimit)
    calculateInto(copy, 0, 0, size)
    @Suppress("UNCHECKED_CAST")
    return copy as T
}