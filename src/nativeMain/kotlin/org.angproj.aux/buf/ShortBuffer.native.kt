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
    "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"
)
@OptIn(ExperimentalForeignApi::class)
public actual class ShortBuffer actual constructor(
    size: Int, idxLimit: Int
) : AbstractBufferType<Short>(size, typeSize, idxLimit) {

    public actual constructor(size: Int) : this(size, size)

    override fun create(size: Int, idxLimit: Int): ShortBuffer = ShortBuffer(size, idxLimit)

    actual override operator fun get(index: Int): Short {
        index.checkRange<Reify>()
        return (ptr + index * TypeSize.short).toCPointer<ShortVar>()!!.pointed.value
    }

    actual override operator fun set(index: Int, value: Short) {
        index.checkRange<Reify>()
        (ptr + index * TypeSize.short).toCPointer<ShortVar>()!!.pointed.value = value
    }

    override fun <T: AbstractSpeedCopy> calculateInto(dest: T, destOff: Int, idxFrom: Int, idxTo: Int) {
        val ts = idxSize.size
        innerCopy(dest as ShortBuffer, destOff * ts, idxFrom * ts, idxTo * ts)
    }

    public actual companion object {
        public actual val typeSize: TypeSize = TypeSize.SHORT
    }
}