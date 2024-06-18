package org.angproj.aux.rand

import org.angproj.aux.buf.BinaryBuffer
import org.angproj.aux.io.Reader
import org.angproj.aux.io.Segment
import org.angproj.aux.sec.SecureRandom
import org.angproj.aux.util.Benchmark
import org.angproj.aux.util.floorMod
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.math.min
import kotlin.test.Test

class TestRandom256(iv: LongArray = LongArray(4)): AbstractSponge256(), Reader {

    init {
        (0 until min(visibleSize, iv.size)).forEach { absorb(iv[it], it) }
        scramble()
    }

    override fun read(data: Segment): Int {
        require(data.size.floorMod(byteSize) == 0)
        fill(data) { round() }
        return data.size
    }
}

class AbstractSponge256Test {
    @Test
    fun testMonteCarlo() {
        val monteCarlo = Benchmark()
        val random = TestRandom256(LongArray(4) { SecureRandom.readLong() })
        val buffer = BinaryBuffer()
        repeat(10_000_000) {
            if(buffer.remaining == 0) {
                buffer.reset()
                random.read(buffer.segment)
            }
            monteCarlo.scatterPoint(buffer.readLong(), buffer.readLong())
        }
        println(monteCarlo.distribution())
        println((monteCarlo.distribution() - PI).absoluteValue)
    }
}