package org.angproj.aux.sec

import org.angproj.aux.buf.BinaryBuffer
import org.angproj.aux.io.DataSize
import org.angproj.aux.util.Benchmark
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.test.Test
import kotlin.test.assertEquals


class SecureEntropyTest {

    @Test
    fun read() {
        val count = DataSize._1K.size / Long.SIZE_BYTES
        val buffer = BinaryBuffer(DataSize._1K)
        SecureEntropy.read(buffer._segment)
        val values = LongArray(count) { buffer.readLong() }
        assertEquals(values.toSet().size, count)
    }


    //@Test
    fun testMonteCarlo() {
        val monteCarlo = Benchmark()
        val buffer = BinaryBuffer(DataSize._1K)
        repeat(10_000_000) {
            if(buffer.remaining == 0) {
                buffer.reset()
                SecureEntropy.read(buffer._segment)
            }
            monteCarlo.scatterPoint(buffer.readLong(), buffer.readLong())
        }
        println(monteCarlo.distribution())
        println((monteCarlo.distribution() - PI).absoluteValue)


    }
}