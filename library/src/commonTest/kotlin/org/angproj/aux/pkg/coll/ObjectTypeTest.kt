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
package org.angproj.aux.pkg.coll

import org.angproj.aux.buf.BinaryBuffer
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.TestObject
import org.angproj.aux.util.uuid4
import kotlin.test.Test
import kotlin.test.assertEquals


class ObjectTypeTest {

    private fun setInputTestObject(): TestObject = TestObject(
        uuid4()
    )

    @Test
    fun enfoldUnfoldStream() {
        val pl1 = setInputTestObject()
        val buf = BinaryBuffer()
        val lt = ObjectType(pl1)
        lt.enfoldStream(buf)
        buf.flip()

        assertEquals(lt.foldSize(FoldFormat.STREAM), buf.limit)

        val pl2 = ObjectType.unfoldStream(buf) { TestObject() }.value

        assertEquals(pl1, pl2)
    }
}