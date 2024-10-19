/**
 * Copyright (c) 2022-2024 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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
package org.angproj.aux.util

import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Uuid4Test {
    @Test
    fun testUuid4() {
        assertEquals(uuid4().asBinary().retrieveByte(6).toInt() and 0xf0,0x40)
        assertEquals(uuid4().asBinary().retrieveByte(8).toInt() and 0xc0, 0x80)
        assertEquals(uuid4().toString().length, 36)
    }

    @Test
    fun testNullUuid4() {
        assertTrue(NullObject.uuid4.isNull())
        assertFalse(uuid4().isNull())
    }

    //@Test
    fun testPrintUuid4() {
        repeat(100) { println(uuid4()) }
    }

    //@Test
    fun testMonteCarlo() {
        val monteCarlo = Benchmark()
        repeat(10_000_000) {
            val data = uuid4().asBinary()
            monteCarlo.scatterPoint(data.retrieveLong(0), data.retrieveLong(8))
        }
        println(monteCarlo.distribution())
        println((monteCarlo.distribution() - PI).absoluteValue)
    }
}