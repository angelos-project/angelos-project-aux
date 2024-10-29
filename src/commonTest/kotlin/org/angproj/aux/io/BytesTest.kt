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

import org.angproj.aux.mem.Default
import kotlin.test.Test

class BytesTest: AbstractSegmentValidator() {

    private val createNew: (size: Int) -> Bytes = { Default.allocate(it) }

    @Test
    fun testByteWriteReadSync() = byteWriteReadSync(createNew)

    @Test
    fun testShortReadAsync() = shortReadAsync(createNew)

    @Test
    fun testShortWriteAsync() = shortWriteAsync(createNew)

    @Test
    fun testIntReadAsync() = intReadAsync(createNew)

    @Test
    fun testIntWriteAsync() = intWriteAsync(createNew)

    @Test
    fun testLongReadAsync() = longReadAsync(createNew)

    @Test
    fun testLongWriteAsync() = longWriteAsync(createNew)

    @Test
    fun testByteRWOutbound() = byteRWOutbound(createNew)

    @Test
    fun testShortRWOutbound() = shortRWOutbound(createNew)

    @Test
    fun testIntRWOutbound() = intRWOutbound(createNew)

    @Test
    fun testLongRWOutbound() = longRWOutbound(createNew)

    @Test
    fun testTryCopyInto() = tryCopyInto(createNew)

    @Test
    fun testTryCopyOfRange() = tryCopyOfRange(createNew)

    @Test
    fun testTryCopyOf() = tryCopyOf(createNew)

    @Test
    fun testOneSize() {
        val seg = Default.allocate(9)
        println(seg.size)
        println(seg.limit)
        seg.close()
    }
}