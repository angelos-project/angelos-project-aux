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
        val entropy = Entropy()
        val monteCarlo = Benchmark()
        val buffer = binOf(DataSize._1M.size)
        measureTime {
            repeat(1024) {
                entropy.read(buffer)
                val wrapper = buffer.asWrapped()
                while(wrapper.position < wrapper.limit) monteCarlo.scatterPoint(wrapper.readLong(), wrapper.readLong())
            }
        }.also { println("Time: $it") }
        println("Distribution: " + monteCarlo.distribution())
        println("Error: " + (monteCarlo.distribution() - PI).absoluteValue)
    }
}