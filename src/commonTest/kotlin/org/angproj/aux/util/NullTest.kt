package org.angproj.aux.util

import kotlin.test.Test
import kotlin.test.assertTrue

class NullTest {

    @Test
    fun testString() {
        assertTrue(Null.emptyString.isEmpty())
    }

    @Test
    fun testByteArray() {
        assertTrue(Null.emptyByteArray.isEmpty())
    }

    @Test
    fun testShortArray() {
        assertTrue(Null.emptyShortArray.isEmpty())
    }

    @Test
    fun testIntArray() {
        assertTrue(Null.emptyIntArray.isEmpty())
    }

    @Test
    fun testLongArray() {
        assertTrue(Null.emptyLongArray.isEmpty())
    }

    @Test
    fun testFloatArray() {
        assertTrue(Null.emptyFloatArray.isEmpty())
    }

    @Test
    fun testDoubleArray() {
        assertTrue(Null.emptyDoubleArray.isEmpty())
    }

    @Test
    fun testUuid4() {
        assertTrue(Null.emptyUuid4.isEmpty())
    }
}