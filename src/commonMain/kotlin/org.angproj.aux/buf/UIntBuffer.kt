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

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public expect class UIntBuffer private constructor(
    size: Int, idxOff: Int, idxEnd: Int
): AbstractBufferType<UInt> {
    public constructor(size: Int)

    public override operator fun get(index: Int): UInt
    public override operator fun set(index: Int, value: UInt)

    public companion object {
        public val typeSize: TypeSize
    }
}

public expect fun UIntBuffer.copyOf(): UIntBuffer

public expect fun UIntBuffer.copyOfRange(idxFrom: Int, idxTo: Int): UIntBuffer