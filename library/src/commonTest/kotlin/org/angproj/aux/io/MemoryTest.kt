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
package org.angproj.aux.io

import org.angproj.aux.mem.MemoryFree
import org.angproj.aux.util.KotlinPlatformVariant
import org.angproj.aux.util.ifJvmOrNative
import kotlin.test.Test

class MemoryTest: AbstractSegmentValidator() {

    private val createNew: (size: Int) -> Memory = { MemoryFree.allocate(it) }

    @Test
    fun testByteWriteReadSync() = ifJvmOrNative { byteWriteReadSync(createNew) }

    @Test
    fun testShortReadAsync() = ifJvmOrNative { shortReadAsync(createNew) }

    @Test
    fun testShortWriteAsync() = ifJvmOrNative { shortWriteAsync(createNew) }

    @Test
    fun testIntReadAsync() = ifJvmOrNative { intReadAsync(createNew) }

    @Test
    fun testIntWriteAsync() = ifJvmOrNative { intWriteAsync(createNew) }

    @Test
    fun testLongReadAsync() = ifJvmOrNative { longReadAsync(createNew) }

    @Test
    fun testLongWriteAsync() = ifJvmOrNative { longWriteAsync(createNew) }

    @Test
    fun testByteRWOutbound() = ifJvmOrNative { byteRWOutbound(createNew) }

    @Test
    fun testShortRWOutbound() = ifJvmOrNative { shortRWOutbound(createNew) }

    @Test
    fun testIntRWOutbound() = ifJvmOrNative { intRWOutbound(createNew) }

    @Test
    fun testLongRWOutbound() = ifJvmOrNative { longRWOutbound(createNew) }

    @Test
    fun testTryCopyInto() = ifJvmOrNative { tryCopyInto(createNew) }

    @Test
    fun testTryCopyOfRange() = ifJvmOrNative { tryCopyOfRange(createNew) }

    @Test
    fun testTryCopyOf() = ifJvmOrNative { tryCopyOf(createNew) }

    @Test
    fun testOneSize() {
        ifJvmOrNative {
            val seg = MemoryFree.allocate(9)
            println(seg.size)
            println(seg.limit)
            seg.close()
        }
    }
}