package org.angproj.aux.buf

import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class ByteBuffer2Test {

    @Test
    fun testByteBufferCopyRange() {
        /*val a = ByteArray(16) { Random.nextInt().toByte() }
        val m = ByteBuffer(16)

        (0 until m.size).forEach { m[it] = a[it] }
        (0 until m.size).forEach { assertEquals(m[it], a[it]) }

        (0 until 8).forEach { from ->
            val c: ByteBuffer = m.copyOfRange(from, 16)
            (0 until c.size).forEach {
                println("$from " + it)
                assertEquals(c[it], m[from + it]) }
        }
        (8 until 16).forEach { from ->
            val c: ByteBuffer = m.copyOfRange(0, from)
            (0 until c.size).forEach { assertEquals(c[it], m[it]) }
        }
        (0 until 8).forEach { from ->
            val c: ByteBuffer = m.copyOfRange(from, from + 8)
            (0 until c.size).forEach { assertEquals(c[it], m[from + it]) }
        }*/
    }

    @Test
    fun testDoubleBufferCopyRange() {
        /*val a = DoubleArray(16) { Random.nextDouble() }
        val m = DoubleBuffer(16)

        (0 until m.size).forEach { m[it] = a[it] }
        (0 until m.size).forEach { assertEquals(m[it], a[it]) }

        (0 until 8).forEach { from ->
            val c: DoubleBuffer = m.copyOfRange(from, 16)
            (0 until c.size).forEach {
                println("$from " + it)
                assertEquals(c[it], m[from + it]) }
        }
        (8 until 16).forEach { from ->
            val c: DoubleBuffer = m.copyOfRange(0, from)
            (0 until c.size).forEach { assertEquals(c[it], m[it]) }
        }
        (0 until 8).forEach { from ->
            val c: DoubleBuffer = m.copyOfRange(from, from + 8)
            (0 until c.size).forEach { assertEquals(c[it], m[from + it]) }
        }*/
    }

    @Test
    fun testFloatBufferCopyRange() {
        /*val a = FloatArray(16) { Random.nextFloat() }
        val m = FloatBuffer(16)

        (0 until m.size).forEach { m[it] = a[it] }
        (0 until m.size).forEach { assertEquals(m[it], a[it]) }

        (0 until 8).forEach { from ->
            val c: FloatBuffer = m.copyOfRange(from, 16)
            (0 until c.size).forEach {
                println("$from " + it)
                assertEquals(c[it], m[from + it]) }
        }
        (8 until 16).forEach { from ->
            val c: FloatBuffer = m.copyOfRange(0, from)
            (0 until c.size).forEach { assertEquals(c[it], m[it]) }
        }
        (0 until 8).forEach { from ->
            val c: FloatBuffer = m.copyOfRange(from, from + 8)
            (0 until c.size).forEach { assertEquals(c[it], m[from + it]) }
        }*/
    }

    @Test
    fun testIntBufferCopyRange() {
        /*val a = IntArray(16) { Random.nextInt() }
        val m = IntBuffer(16)

        (0 until m.size).forEach { m[it] = a[it] }
        (0 until m.size).forEach { assertEquals(m[it], a[it]) }

        (0 until 8).forEach { from ->
            val c = m.copyOfRange(from, 16)
            (0 until c.size).forEach {
                assertEquals(c[it], m[from + it]) }
        }
        (8 until 16).forEach { from ->
            val c = m.copyOfRange(0, from)
            (0 until c.size).forEach { assertEquals(c[it], m[it]) }
        }
        (0 until 8).forEach { from ->
            val c = m.copyOfRange(from, from + 8)
            (0 until c.size).forEach { assertEquals(c[it], m[from + it]) }
        }

        val c0 = m.copyOfRange(1, m.size-1)

        (0 until c0.size).forEach {
            assertEquals(c0[it], m[1 + it])
        }

        val c1 = c0.copyOfRange(1, c0.size-1)

        (0 until c1.size).forEach {
            assertEquals(c1[it], c0[1 + it])
        }*/
    }

    @Test
    fun testLongBufferCopyRange() {
        /*val a = LongArray(16) { Random.nextLong() }
        val m = LongBuffer(16)

        (0 until m.size).forEach { m[it] = a[it] }
        (0 until m.size).forEach { assertEquals(m[it], a[it]) }

        (0 until 8).forEach { from ->
            val c: LongBuffer = m.copyOfRange(from, 16)
            (0 until c.size).forEach {
                assertEquals(c[it], m[from + it]) }
        }
        (8 until 16).forEach { from ->
            val c: LongBuffer = m.copyOfRange(0, from)
            (0 until c.size).forEach { assertEquals(c[it], m[it]) }
        }
        (0 until 8).forEach { from ->
            val c: LongBuffer = m.copyOfRange(from, from + 8)
            (0 until c.size).forEach { assertEquals(c[it], m[from + it]) }
        }*/
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    @Test
    fun testUByteBufferCopyRange() {
        /*val a = UByteArray(16) { Random.nextInt().toByte().toUByte() }
        val m = UByteBuffer(16)

        (0 until m.size).forEach { m[it] = a[it] }
        (0 until m.size).forEach { assertEquals(m[it], a[it]) }

        (0 until 8).forEach { from ->
            val c: UByteBuffer = m.copyOfRange(from, 16)
            (0 until c.size).forEach {
                assertEquals(c[it], m[from + it]) }
        }
        (8 until 16).forEach { from ->
            val c: UByteBuffer = m.copyOfRange(0, from)
            (0 until c.size).forEach { assertEquals(c[it], m[it]) }
        }
        (0 until 8).forEach { from ->
            val c: UByteBuffer = m.copyOfRange(from, from + 8)
            (0 until c.size).forEach { assertEquals(c[it], m[from + it]) }
        }*/
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    @Test
    fun testUIntArrayBufferCopyRange() {
        /*val a = UIntArray(16) { Random.nextInt().toUInt() }
        val m = UIntBuffer(16)

        (0 until m.size).forEach { m[it] = a[it] }
        (0 until m.size).forEach { assertEquals(m[it], a[it]) }

        (0 until 8).forEach { from ->
            val c: UIntBuffer = m.copyOfRange(from, 16)
            (0 until c.size).forEach {
                assertEquals(c[it], m[from + it]) }
        }
        (8 until 16).forEach { from ->
            val c: UIntBuffer = m.copyOfRange(0, from)
            (0 until c.size).forEach { assertEquals(c[it], m[it]) }
        }
        (0 until 8).forEach { from ->
            val c: UIntBuffer = m.copyOfRange(from, from + 8)
            (0 until c.size).forEach { assertEquals(c[it], m[from + it]) }
        }*/
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    @Test
    fun testULongBufferBufferCopyRange() {
        /*val a = ULongArray(16) { Random.nextLong().toULong() }
        val m = ULongBuffer(16)

        (0 until m.size).forEach { m[it] = a[it] }
        (0 until m.size).forEach { assertEquals(m[it], a[it]) }

        (0 until 8).forEach { from ->
            val c: ULongBuffer = m.copyOfRange(from, 16)
            (0 until c.size).forEach {
                assertEquals(c[it], m[from + it]) }
        }
        (8 until 16).forEach { from ->
            val c: ULongBuffer = m.copyOfRange(0, from)
            (0 until c.size).forEach { assertEquals(c[it], m[it]) }
        }
        (0 until 8).forEach { from ->
            val c: ULongBuffer = m.copyOfRange(from, from + 8)
            (0 until c.size).forEach { assertEquals(c[it], m[from + it]) }
        }*/
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    @Test
    fun testUShortBufferCopyRange() {
        /*val a = UShortArray(16) { Random.nextInt().toUShort() }
        val m = UShortBuffer(16)

        (0 until m.size).forEach { m[it] = a[it] }
        (0 until m.size).forEach { assertEquals(m[it], a[it]) }

        (0 until 8).forEach { from ->
            val c: UShortBuffer = m.copyOfRange(from, 16)
            (0 until c.size).forEach {
                assertEquals(c[it], m[from + it]) }
        }
        (8 until 16).forEach { from ->
            val c: UShortBuffer = m.copyOfRange(0, from)
            (0 until c.size).forEach { assertEquals(c[it], m[it]) }
        }
        (0 until 8).forEach { from ->
            val c: UShortBuffer = m.copyOfRange(from, from + 8)
            (0 until c.size).forEach { assertEquals(c[it], m[from + it]) }
        }*/
    }
}