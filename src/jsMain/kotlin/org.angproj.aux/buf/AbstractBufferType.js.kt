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

@Suppress(
    "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING",
)
public actual abstract class AbstractBufferType<E> actual constructor(
   size: Int, idxSize: TypeSize, idxOff: Int, idxEnd: Int
) : AbstractSpeedCopy(size, idxSize, idxOff, idxEnd), BufferType<E> {
    actual abstract override fun create(
        size: Int,
        idxOff: Int,
        idxEnd: Int
    ): AbstractBufferType<E>
}
