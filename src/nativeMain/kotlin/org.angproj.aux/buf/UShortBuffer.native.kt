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

import kotlinx.cinterop.*
import org.angproj.aux.io.TypeSize
import org.angproj.aux.io.innerMemCopyOfRange

@Suppress(
    "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"
)
@OptIn(ExperimentalForeignApi::class)
public actual class UShortBuffer actual constructor(
    size: Int, idxOff: Int, idxEnd: Int
) : AbstractBufferType<UShort>(size, typeSize, idxOff, idxEnd) {

    public actual constructor(size: Int) : this(size, 0, size)

    override fun create(size: Int, idxOff: Int, idxEnd: Int): UShortBuffer = UShortBuffer(size, idxOff, idxEnd)

    actual override operator fun get(index: Int): UShort {
        index.checkRange<Reify>()
        return (ptr + index * TypeSize.uShort).toCPointer<UShortVar>()!!.pointed.value
    }

    actual override operator fun set(index: Int, value: UShort) {
        index.checkRange<Reify>()
        (ptr + index * TypeSize.uShort).toCPointer<UShortVar>()!!.pointed.value = value
    }

    public actual companion object {
        public actual val typeSize: TypeSize = TypeSize.U_SHORT
    }
}

public actual fun UShortBuffer.copyOfRange(
    idxFrom: Int,
    idxTo: Int
): UShortBuffer = innerCopyOfRange(idxFrom, idxTo)

public actual fun UShortBuffer.copyOf(): UShortBuffer = innerCopyOfRange(0, size)