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
    protected abstract val marginSize: Int
    protected abstract fun create(size: Int): AbstractSpeedCopy
}
