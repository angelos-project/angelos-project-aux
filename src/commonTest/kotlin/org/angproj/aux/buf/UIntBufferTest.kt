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

import org.angproj.aux.TestInformationStub
import org.angproj.aux.util.NullObject
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class UIntBufferTest: AbstractArrayBufferTest<UInt>() {

    override val refValue: UInt = TestInformationStub.refUInt

    override fun setInput(): UIntBuffer {
        val lb = UIntBuffer(capValue)
        (0 until lb.limit).forEach {
            lb[it] = refValue
        }
        return lb
    }

    @Test
    fun testNullUIntBuffer() {
        assertTrue(NullObject.uIntBuffer.isNull())
        assertFalse(UIntBuffer().isNull())
    }
}