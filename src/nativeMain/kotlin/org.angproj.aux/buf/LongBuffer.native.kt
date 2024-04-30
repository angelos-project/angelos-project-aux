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

@Suppress(
    "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"
)
@OptIn(ExperimentalForeignApi::class)
public actual class LongBuffer actual constructor(
    size: Int, idxOff: Int, idxEnd: Int
) : AbstractBufferType<Long>(size, typeSize, idxOff, idxEnd) {

    public actual constructor(size: Int) : this(size, 0, size)

    override fun create(size: Int, idxOff: Int, idxEnd: Int): LongBuffer = LongBuffer(size, idxOff, idxEnd)
    override fun copyOf(): AbstractBufferType<Long> {
        TODO("Not yet implemented")
    }

    public override fun copyOfRange(idxFrom: Int, idxTo: Int): LongBuffer = copyOfRange2(idxFrom, idxTo) as LongBuffer

    actual override operator fun get(index: Int): Long {
        index.checkRange<Reify>()
        return (ptr + index * TypeSize.long).toCPointer<LongVar>()!!.pointed.value
    }

    actual override operator fun set(index: Int, value: Long) {
        index.checkRange<Reify>()
        (ptr + index * TypeSize.long).toCPointer<LongVar>()!!.pointed.value = value
    }

    public actual companion object {
        public actual val typeSize: TypeSize = TypeSize.LONG
    }
}