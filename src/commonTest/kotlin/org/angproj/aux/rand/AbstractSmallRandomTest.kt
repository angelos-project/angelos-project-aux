package org.angproj.aux.rand

import org.angproj.aux.io.OldReader
import org.angproj.aux.util.Benchmark
import org.angproj.aux.util.DataBuffer
import org.angproj.aux.util.floorMod
import org.angproj.aux.util.writeIntAt
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.test.Test

class TestSmallRandom: AbstractSmallRandom(
    ByteArray(16).also { InitializationVector.realTimeGatedEntropy(it) }
), OldReader {

    override fun read(data: ByteArray): Int {
        require(data.size.floorMod(Int.SIZE_BYTES) == 0)
        (data.indices step Int.SIZE_BYTES).forEach { data.writeIntAt(it, round()) }
        return data.size
    }

    override fun read(length: Int): ByteArray {
        TODO("Will not be implemented!")
    }
}

class AbstractSmallRandomTest {
    @Test
    fun testMonteCarlo() {
        val monteCarlo = Benchmark()
        val random = TestSmallRandom()
        val buffer = DataBuffer()
        repeat(10_000_000) {
            if(buffer.remaining == 0) {
                buffer.reset()
                random.read(buffer.asByteArray())
            }
            monteCarlo.scatterPoint(buffer.readLong(), buffer.readLong())
        }
        println(monteCarlo.distribution())
        println((monteCarlo.distribution() - PI).absoluteValue)
    }
}