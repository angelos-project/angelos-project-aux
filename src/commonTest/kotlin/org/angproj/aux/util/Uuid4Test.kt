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

import kotlin.test.Test
import kotlin.test.assertEquals

class Uuid4Test {
    @Test
    fun testUuid4() {
        assertEquals(uuid4().toString()[14].code, "4"[0].code)
        assertEquals(uuid4().toString().length, 36)
    }

    /* //@Test
    fun testNonce() {
        repeat(1000) {
            val monteCarlo = Benchmark()
            Nonce.reseedWithTimestamp()
            repeat(10_000_000) {
                val data = Nonce.getFastNonce()
                monteCarlo.scatterPoint(data.first, data.second)
            }
            println(monteCarlo.distribution())
        }
    }

    //@Test
    fun testPrintUuid4() {
        repeat(100) { println(uuid4()) }
    }

    //@Test
    fun testRandom() {
        repeat(1000) {
            val monteCarlo = Benchmark()
            val rand = Random.getRandom()
            repeat(10_000_000) {
                monteCarlo.scatterPoint(rand.nextLong(), rand.nextLong())
            }
            //println((monteCarlo.distribution() - PI).absoluteValue)
            println(monteCarlo.distribution())
        }
    }*/
}