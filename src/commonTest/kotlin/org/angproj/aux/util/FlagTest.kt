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
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FlagTest {

    @Test
    fun flag0() {
        var value: Byte = 0
        assertFalse(value.checkFlag0())
        value = value.flipOnFlag0()
        assertTrue(value.checkFlag0())
        value = value.flipOffFlag0()
        assertFalse(value.checkFlag0())
    }

    @Test
    fun flag1() {
        var value: Byte = 0
        assertFalse(value.checkFlag1())
        value = value.flipOnFlag1()
        assertTrue(value.checkFlag1())
        value = value.flipOffFlag1()
        assertFalse(value.checkFlag1())
    }

    @Test
    fun flag2() {
        var value: Byte = 0
        assertFalse(value.checkFlag2())
        value = value.flipOnFlag2()
        assertTrue(value.checkFlag2())
        value = value.flipOffFlag2()
        assertFalse(value.checkFlag2())
    }

    @Test
    fun flag3() {
        var value: Byte = 0
        assertFalse(value.checkFlag3())
        value = value.flipOnFlag3()
        assertTrue(value.checkFlag3())
        value = value.flipOffFlag3()
        assertFalse(value.checkFlag3())
    }

    @Test
    fun flag4() {
        var value: Byte = 0
        assertFalse(value.checkFlag4())
        value = value.flipOnFlag4()
        assertTrue(value.checkFlag4())
        value = value.flipOffFlag4()
        assertFalse(value.checkFlag4())
    }

    @Test
    fun flag5() {
        var value: Byte = 0
        assertFalse(value.checkFlag5())
        value = value.flipOnFlag5()
        assertTrue(value.checkFlag5())
        value = value.flipOffFlag5()
        assertFalse(value.checkFlag5())
    }

    @Test
    fun flag6() {
        var value: Byte = 0
        assertFalse(value.checkFlag6())
        value = value.flipOnFlag6()
        assertTrue(value.checkFlag6())
        value = value.flipOffFlag6()
        assertFalse(value.checkFlag6())
    }

    @Test
    fun flag7() {
        var value: Byte = 0
        assertFalse(value.checkFlag7())
        value = value.flipOnFlag7()
        assertTrue(value.checkFlag7())
        value = value.flipOffFlag7()
        assertFalse(value.checkFlag7())
    }
}