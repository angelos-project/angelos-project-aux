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
import org.angproj.aux.io.innerCopy

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public expect class ByteBuffer private constructor(
    size: Int, idxLimit: Int
): AbstractBufferType<Byte> {
    public constructor(size: Int)

    public override operator fun get(index: Int): Byte
    public override operator fun set(index: Int, value: Byte)

    public companion object {
        public val typeSize: TypeSize
    }
}