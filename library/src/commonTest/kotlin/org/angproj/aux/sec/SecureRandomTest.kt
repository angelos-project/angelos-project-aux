package org.angproj.aux.sec

import org.angproj.aux.buf.BinaryBuffer
import org.angproj.aux.io.DataSize
import org.angproj.aux.util.Benchmark
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SecureRandomTest {

    @Test
    fun readByte() {
        val sample = Array(256) { SecureRandom.readByte() }
        assertTrue(sample.toSet().size > 1)
    }

    @Test
    fun readUByte() {
        val sample = Array(256) { SecureRandom.readUByte() }
        assertTrue(sample.toSet().size > 1)
    }

    @Test
    fun readShort() {
        val sample = Array(256) { SecureRandom.readShort() }
        assertTrue(sample.toSet().size > 1)
    }

    @Test
    fun readUShort() {
        val sample = Array(256) { SecureRandom.readUShort() }
        assertTrue(sample.toSet().size > 1)
    }

    @Test
    fun readInt() {
        val sample = Array(256) { SecureRandom.readInt() }
        assertTrue(sample.toSet().size > 1)
    }

    @Test
    fun readUInt() {
        val sample = Array(256) { SecureRandom.readUInt() }
        assertTrue(sample.toSet().size > 1)
    }

    @Test
    fun readLong() {
        val sample = Array(256) { SecureRandom.readLong() }
        assertTrue(sample.toSet().size > 1)
    }

    @Test
    fun readULong() {
        val sample = Array(256) { SecureRandom.readULong() }
        assertTrue(sample.toSet().size > 1)
    }

    @Test
    fun readFloat() {
        val sample = Array(256) { SecureRandom.readFloat() }
        assertTrue(sample.toSet().size > 1)
    }

    @Test
    fun readDouble() {
        val sample = Array(256) { SecureRandom.readDouble() }
        assertTrue(sample.toSet().size > 1)
    }

    @Test
    fun read() {
        val count = DataSize._8K.size / Long.SIZE_BYTES
        val buffer = BinaryBuffer(DataSize._8K)
        SecureRandom.read(buffer._segment)
        val values = LongArray(count) { buffer.readLong() }
        assertEquals(values.toSet().size, count)
    }

    //@Test
    fun testMonteCarlo() {
        val monteCarlo = Benchmark()
        repeat(10_000_000) {
            monteCarlo.scatterPoint(SecureRandom.readLong(), SecureRandom.readLong())
        }
        println(monteCarlo.distribution())
        println((monteCarlo.distribution() - PI).absoluteValue)
    }
}