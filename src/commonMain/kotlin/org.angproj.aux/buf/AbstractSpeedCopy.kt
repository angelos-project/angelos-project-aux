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

public abstract class AbstractSpeedCopy(public final override val size: Int, public val idxSize: TypeSize): SpeedCopy {

    //public constructor(protected val hiddenSize: Int, from: Int, to: Int) : this(to - from)

    /**
     * The size in bytes of the index type.
     * */
    //public abstract val idxSize: TypeSize

    /**
     * The index offset in relationship to the realSize of their own type.
     * */
    protected val idxOff: Int = 0

    /**
     * The index end in relationship to the realSize of their own type.
     * */
    protected val idxEnd: Int = idxOff + size

    protected abstract fun speedLongGet(idx: Int): Long
    protected abstract fun speedLongSet(idx: Int, value: Long)
}
