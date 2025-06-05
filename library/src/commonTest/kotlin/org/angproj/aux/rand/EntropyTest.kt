package org.angproj.aux.rand

import org.angproj.aux.buf.asWrapped
import org.angproj.aux.io.DataSize
import org.angproj.aux.io.binOf
import org.angproj.aux.util.Benchmark
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.test.Test
import kotlin.time.measureTime


class EntropyTest {

    @Test
    fun snapTime() {
    }

    //@Test
    fun read() {
        val monteCarlo = Benchmark()
        val buffer = binOf(DataSize._1M.size)
        measureTime {
            repeat(1024) {
                Entropy.read(buffer)
                val wrapper = buffer.asWrapped()
                while(wrapper.position < wrapper.limit) monteCarlo.scatterPoint(wrapper.readLong(), wrapper.readLong())
            }
        }.also { println("Time: $it") }
        println("Distribution: " + monteCarlo.distribution())
        println("Error: " + (monteCarlo.distribution() - PI).absoluteValue)
    }

    //@Test
    fun testMonteCarlo() {
        val monteCarlo = Benchmark()
        val data = binOf(16)
        repeat(10_000_000) {
            Entropy.realTimeGatedEntropy(data)
            monteCarlo.scatterPoint(data.retrieveLong(0), data.retrieveLong(8))
        }
        println(monteCarlo.distribution())
        println((monteCarlo.distribution() - PI).absoluteValue)
    }
}