/**
 * Copyright (c) 2022-2023 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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

class Uuid4Test {
    @Test
    fun testUuid4() {
        assertEquals(uuid4().toString()[14].code, "4"[0].code)
        assertEquals(uuid4().toString().length, 36)
    }

    @Test
    fun testPrintUuid4() {
        repeat(100) { println(uuid4()) }
    }

    //@Test
    fun testMonteCarlo() {
        val monteCarlo = Benchmark()
        repeat(10_000_000) {
           val data = Uuid4.generateByteArray()
            monteCarlo.scatterPoint(data.readLongAt(0), data.readLongAt(8))
        }
        println(monteCarlo.distribution())
        println((monteCarlo.distribution() - PI).absoluteValue)
    }

    //@Test
    fun testMonteCarlo2() {
        val monteCarlo = Benchmark()
        repeat(10_000_000) {
            monteCarlo.scatterPoint(Uuid4.generateLong(), Uuid4.generateLong())
        }
        println(monteCarlo.distribution())
        println((monteCarlo.distribution() - PI).absoluteValue)
    }
}