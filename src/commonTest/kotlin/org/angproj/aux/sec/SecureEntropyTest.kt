package org.angproj.aux.sec

import org.angproj.aux.util.Benchmark
import org.angproj.aux.io.DataSize
import org.angproj.aux.util.DataBuffer
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.test.Test
import kotlin.test.assertEquals


class SecureEntropyTest {

    @Test
    fun read() {
        val count = DataSize._1K.size / Long.SIZE_BYTES
        val buffer = DataBuffer(DataSize._1K)
        SecureEntropy.read(buffer.asByteArray())
        val values = LongArray(count) { buffer.readLong() }
        assertEquals(values.toSet().size, count)
    }

    @Test
    fun testRead() {
        val count = DataSize._1K.size / Long.SIZE_BYTES
        val buffer = DataBuffer(SecureEntropy.read(DataSize._1K.size))
        val values = LongArray(count) { buffer.readLong() }
        assertEquals(values.toSet().size, count)
    }

    @Test
    fun testMonteCarlo() {
        val monteCarlo = Benchmark()
        val buffer = DataBuffer(1024)
        repeat(10_000_000) {
            if(buffer.remaining == 0) {
                buffer.reset()
                SecureEntropy.read(buffer.asByteArray())
            }
            monteCarlo.scatterPoint(buffer.readLong(), buffer.readLong())
        }
        println(monteCarlo.distribution())
        println((monteCarlo.distribution() - PI).absoluteValue)


    }
}