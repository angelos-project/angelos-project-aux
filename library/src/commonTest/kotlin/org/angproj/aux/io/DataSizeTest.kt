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
import kotlin.test.assertFailsWith

class DataSizeTest {

    @Test
    fun testFindLowestAbove() {
        mapOf(
            0 to 32,
            2 to 32,
            17 to 32,
            31 to 32,
            33 to 64,
            1000 to 1024,
            1_000_000_000 to DataSize._1G.size
        ).forEach { assertEquals(DataSize.findLowestAbove(it.key).size, it.value) }

        assertFailsWith<IllegalArgumentException> { DataSize.findLowestAbove(-1) }
        assertFailsWith<IllegalArgumentException> { DataSize.findLowestAbove(DataSize._1G.size + 1) }
    }
}