package org.angproj.aux.rand

import org.angproj.aux.buf.*
import org.angproj.aux.io.*
import org.angproj.aux.util.*
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.test.Test

class TestSmallRandom: AbstractSmallRandom(
    binOf(16).also { InitializationVector.realTimeGatedEntropy(it) }
), Reader {

    override fun read(bin: Binary): Int {
        require(bin.limit.floorMod(Int.SIZE_BYTES) == 0)
        val wrap = bin.asWrapped()
        repeat(bin.limit / Int.SIZE_BYTES) { _ -> wrap.writeInt(round()) }
        return bin.limit
    }
}

class AbstractSmallRandomTest {
    @Test
    fun testMonteCarlo() {
        val monteCarlo = Benchmark()
        val random = TestSmallRandom()
        val buffer = BinaryBuffer()
        repeat(10_000_000) {
            if(buffer.remaining == 0) {
                buffer.reset()
                random.read(buffer.asBinary())
            }
            monteCarlo.scatterPoint(buffer.readLong(), buffer.readLong())
        }
        println(monteCarlo.distribution())
        println((monteCarlo.distribution() - PI).absoluteValue)
    }
}