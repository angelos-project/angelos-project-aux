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
package org.angproj.aux.pkg.arb

import org.angproj.aux.buf.BinaryBuffer
import org.angproj.aux.io.binOf
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.TestStruct
import kotlin.test.Test
import kotlin.test.assertEquals


class StructTypeTest {

    private fun setInputTestStruct(): TestStruct = TestStruct.randomize()

    @Test
    fun enfoldUnfoldBlock() {
        val pl1 = setInputTestStruct()
        val lt = StructType(pl1)
        val buf = binOf(lt.foldSize(FoldFormat.BLOCK))
        val length = lt.enfoldBlock(buf, 0)

        assertEquals(length, buf.limit)
        assertEquals(lt.foldSize(FoldFormat.BLOCK), buf.limit)

        val pl2 = StructType.unfoldBlock(buf) { TestStruct() }.value

        assertEquals(pl1, pl2)
    }

    @Test
    fun enfoldUnfoldStream() {
        val pl1 = setInputTestStruct()
        val buf = BinaryBuffer()
        val lt = StructType(pl1)
        val length = lt.enfoldStream(buf)
        buf.flip()

        assertEquals(length, buf.limit)
        assertEquals(lt.foldSize(FoldFormat.STREAM), buf.limit)

        val pl2 = StructType.unfoldStream(buf) { TestStruct() }.value

        assertEquals(pl1, pl2)
    }
}