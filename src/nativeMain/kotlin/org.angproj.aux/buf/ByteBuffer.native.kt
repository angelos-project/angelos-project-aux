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
import org.angproj.aux.util.Reify

@Suppress(
    "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING",
)
@OptIn(ExperimentalForeignApi::class)
public actual class ByteBuffer actual constructor(
    size: Int, idxLimit: Int
) : AbstractBufferType<Byte>(size, typeSize, idxLimit) {

    public actual constructor(size: Int) : this(size, size)

    override fun create(size: Int, idxLimit: Int): ByteBuffer = ByteBuffer(size, idxLimit)

    actual override operator fun get(index: Int): Byte {
        index.checkRange<Reify>()
        return (ptr + index).toCPointer<ByteVar>()!!.pointed.value
    }

    actual override operator fun set(index: Int, value: Byte) {
        index.checkRange<Reify>()
        (ptr + index).toCPointer<ByteVar>()!!.pointed.value = value
    }

    public actual companion object {
        public actual val typeSize: TypeSize = TypeSize.BYTE
    }
}

public actual fun ByteBuffer.copyInto(destination: ByteBuffer, destinationOffset: Int, fromIndex: Int, toIndex: Int) {
    innerCopy(destination, destinationOffset, fromIndex, toIndex)
}