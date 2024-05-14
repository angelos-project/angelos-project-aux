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

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class MemoryTest: AbstractSegmentValidator() {

    private val createNew: (size: Int) -> Memory = { Memory(it) }

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
    fun testTryCopyInto() {
        val seg1 = createNew(arr1.size)
        val seg2 = createNew(arr2.size)

        // Copy and verify that #1 reflect each other
        (0 until seg1.size).forEach { seg1.setByte(it, arr1[it]) }
        (0 until seg1.size).forEach {
            assertEquals(seg1.getByte(it), arr1[it]) }

        // Copy and verify that #2 reflect each other
        (0 until seg2.size).forEach { seg2.setByte(it, arr2[it]) }
        (0 until seg2.size).forEach {
            assertEquals(seg2.getByte(it), arr2[it]) }

        // Prove that a chunk is fully saturated as the reflecting array
        assertFails { println(arr1[seg1.size]) }

        // Copy chunk 2 into the middle of chunk 1
        seg2.copyInto(seg1, 32, 0, 64)
        arr2.copyInto(arr1, 32, 0, 64)
        arr1.indices.forEach { // Verify similarity between the two operations carried out simultaneously
            assertEquals(seg1.getByte(it), arr1[it]) }

        seg1.close()
        seg2.close()
    }

    //@Test
    //fun testTryCopyOfRange() = tryCopyOfRange(createNew)

    @Test
    fun testFixTrix() {
    }
}