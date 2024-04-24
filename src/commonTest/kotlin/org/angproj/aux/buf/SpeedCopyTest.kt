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

import org.angproj.aux.io.TypeSize
import kotlin.test.Test
import kotlin.test.assertEquals

class SpeedCopyTest {

    @Test
    fun testAddMarginTotalBytes() {
        (1..8).forEach {
            assertEquals(SpeedCopy.addMarginInTotalBytes(it, TypeSize.BYTE), 8)
            assertEquals(SpeedCopy.addMarginInTotalBytes(it, TypeSize.U_BYTE), 8)
        }

        (1..4).forEach {
            assertEquals(SpeedCopy.addMarginInTotalBytes(it, TypeSize.SHORT), 8)
            assertEquals(SpeedCopy.addMarginInTotalBytes(it, TypeSize.U_SHORT), 8)
        }

        (1..2).forEach {
            assertEquals(SpeedCopy.addMarginInTotalBytes(it, TypeSize.INT), 8)
            assertEquals(SpeedCopy.addMarginInTotalBytes(it, TypeSize.U_INT), 8)
            assertEquals(SpeedCopy.addMarginInTotalBytes(it, TypeSize.FLOAT), 8)
        }

        (1..1).forEach {
            assertEquals(SpeedCopy.addMarginInTotalBytes(it, TypeSize.LONG), 8)
            assertEquals(SpeedCopy.addMarginInTotalBytes(it, TypeSize.U_LONG), 8)
            assertEquals(SpeedCopy.addMarginInTotalBytes(it, TypeSize.DOUBLE), 8)
        }

        (9..16).forEach {
            assertEquals(SpeedCopy.addMarginInTotalBytes(it, TypeSize.BYTE), 16)
            assertEquals(SpeedCopy.addMarginInTotalBytes(it, TypeSize.U_BYTE), 16)
        }

        (5..8).forEach {
            assertEquals(SpeedCopy.addMarginInTotalBytes(it, TypeSize.SHORT), 16)
            assertEquals(SpeedCopy.addMarginInTotalBytes(it, TypeSize.U_SHORT), 16)
        }

        (3..4).forEach {
            assertEquals(SpeedCopy.addMarginInTotalBytes(it, TypeSize.INT), 16)
            assertEquals(SpeedCopy.addMarginInTotalBytes(it, TypeSize.U_INT), 16)
            assertEquals(SpeedCopy.addMarginInTotalBytes(it, TypeSize.FLOAT), 16)
        }

        (2..2).forEach {
            assertEquals(SpeedCopy.addMarginInTotalBytes(it, TypeSize.LONG), 16)
            assertEquals(SpeedCopy.addMarginInTotalBytes(it, TypeSize.U_LONG), 16)
            assertEquals(SpeedCopy.addMarginInTotalBytes(it, TypeSize.DOUBLE), 16)
        }
    }

    @Test
    fun testAddMarginByType() {
        (1..8).forEach {
            assertEquals(SpeedCopy.addMarginByIndexType(it, TypeSize.BYTE), 8)
            assertEquals(SpeedCopy.addMarginByIndexType(it, TypeSize.U_BYTE), 8)
        }

        (1..4).forEach {
            assertEquals(SpeedCopy.addMarginByIndexType(it, TypeSize.SHORT), 4)
            assertEquals(SpeedCopy.addMarginByIndexType(it, TypeSize.U_SHORT), 4)
        }

        (1..2).forEach {
            assertEquals(SpeedCopy.addMarginByIndexType(it, TypeSize.INT), 2)
            assertEquals(SpeedCopy.addMarginByIndexType(it, TypeSize.U_INT), 2)
            assertEquals(SpeedCopy.addMarginByIndexType(it, TypeSize.FLOAT), 2)
        }

        (1..1).forEach {
            assertEquals(SpeedCopy.addMarginByIndexType(it, TypeSize.LONG), 1)
            assertEquals(SpeedCopy.addMarginByIndexType(it, TypeSize.U_LONG), 1)
            assertEquals(SpeedCopy.addMarginByIndexType(it, TypeSize.DOUBLE), 1)
        }

        (9..16).forEach {
            assertEquals(SpeedCopy.addMarginByIndexType(it, TypeSize.BYTE), 16)
            assertEquals(SpeedCopy.addMarginByIndexType(it, TypeSize.U_BYTE), 16)
        }

        (5..8).forEach {
            assertEquals(SpeedCopy.addMarginByIndexType(it, TypeSize.SHORT), 8)
            assertEquals(SpeedCopy.addMarginByIndexType(it, TypeSize.U_SHORT), 8)
        }

        (3..4).forEach {
            assertEquals(SpeedCopy.addMarginByIndexType(it, TypeSize.INT), 4)
            assertEquals(SpeedCopy.addMarginByIndexType(it, TypeSize.U_INT), 4)
            assertEquals(SpeedCopy.addMarginByIndexType(it, TypeSize.FLOAT), 4)
        }

        (2..2).forEach {
            assertEquals(SpeedCopy.addMarginByIndexType(it, TypeSize.LONG), 2)
            assertEquals(SpeedCopy.addMarginByIndexType(it, TypeSize.U_LONG), 2)
            assertEquals(SpeedCopy.addMarginByIndexType(it, TypeSize.DOUBLE), 2)
        }
    }
}