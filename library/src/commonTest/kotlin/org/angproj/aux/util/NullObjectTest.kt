package org.angproj.aux.util

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class NullObjectTest {

    @Test
    fun testString() {
        assertTrue(NullObject.string.isNull())
    }

    @Test
    fun testByteArray() {
        assertTrue(NullObject.byteArray.isNull())
        assertFalse(byteArrayOf().isNull())
    }

    @Test
    fun testShortArray() {
        assertTrue(NullObject.shortArray.isNull())
        assertFalse(shortArrayOf().isNull())
    }

    @Test
    fun testIntArray() {
        assertTrue(NullObject.intArray.isNull())
        assertFalse(intArrayOf().isNull())
    }

    @Test
    fun testLongArray() {
        assertTrue(NullObject.longArray.isNull())
        assertFalse(longArrayOf().isNull())
    }

    @Test
    fun testFloatArray() {
        assertTrue(NullObject.floatArray.isNull())
        assertFalse(floatArrayOf().isNull())
    }

    @Test
    fun testDoubleArray() {
        assertTrue(NullObject.doubleArray.isNull())
        assertFalse(doubleArrayOf().isNull())
    }
}