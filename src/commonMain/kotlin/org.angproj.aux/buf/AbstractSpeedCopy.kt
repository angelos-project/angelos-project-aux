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

public abstract class AbstractSpeedCopy(size: Int): SpeedCopy {

    //public constructor(protected val hiddenSize: Int, from: Int, to: Int) : this(to - from)

    public override val size: Int = 0
    public abstract val idxSize: TypeSize
    public val length: Int = size * idxSize.size
    protected val idxOff: Int = 0
    protected val idxEnd: Int = idxOff + size

    protected abstract fun speedLongGet(idx: Int): Long
    protected abstract fun speedLongSet(idx: Int, value: Long)

    public inline fun <reified E: AbstractSpeedCopy>copyOf(): E {
        TODO("Not yet implemented")
    }

    public inline fun <reified E: AbstractSpeedCopy> copyOfRange(fromIdx: Int, toIdx: Int): E {
        TODO("Not yet implemented")
    }
}
