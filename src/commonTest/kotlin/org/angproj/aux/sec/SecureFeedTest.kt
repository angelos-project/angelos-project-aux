package org.angproj.aux.sec

import org.angproj.aux.util.Benchmark
import org.angproj.aux.util.BufferSize
import org.angproj.aux.util.DataBuffer
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.test.Test
import kotlin.test.assertEquals

class SecureFeedTest {

    @Test
    fun read() {
        val count = BufferSize._8K.size / Long.SIZE_BYTES
        val buffer = DataBuffer(BufferSize._8K)
        SecureFeed.read(buffer.asByteArray())
        val values = LongArray(count) { buffer.readLong() }
        assertEquals(values.toSet().size, count)
    }

    @Test
    fun testRead() {
        val count = BufferSize._8K.size / Long.SIZE_BYTES
        val buffer = DataBuffer(SecureFeed.read(BufferSize._8K.size))
        val values = LongArray(count) { buffer.readLong() }
        assertEquals(values.toSet().size, count)
    }

    @Test
    fun testMonteCarlo() {
        val monteCarlo = Benchmark()
        val buffer = DataBuffer(1024)
        repeat(10_000_000) {
            if(buffer.remaining == 0) {
                buffer.reset(false)
                SecureFeed.read(buffer.asByteArray())
            }
            monteCarlo.scatterPoint(buffer.readLong(), buffer.readLong())
        }
        println(monteCarlo.distribution())
        println((monteCarlo.distribution() - PI).absoluteValue)
    }
}