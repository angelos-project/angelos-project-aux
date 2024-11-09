package org.angproj.aux.rand

import org.angproj.aux.buf.BinaryBuffer
import org.angproj.aux.buf.asBinary
import org.angproj.aux.io.Binary
import org.angproj.aux.io.Reader
import org.angproj.aux.sec.SecureRandom
import org.angproj.aux.util.Benchmark
import org.angproj.aux.util.floorMod
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.math.min
import kotlin.test.Test
import kotlin.time.measureTime

class TestRandom512(iv: LongArray = LongArray(8)): AbstractSponge512(), Reader {

    init {
        (0 until min(visibleSize, iv.size)).forEach { absorb(iv[it], it) }
        scramble()
    }

    override fun read(bin: Binary): Int {
        require(bin.limit.floorMod(byteSize) == 0)
        fill(bin._segment) { round() }
        return bin.limit
    }
}

class AbstractSponge512Test {
    @Test
    fun testMonteCarlo() {
        val monteCarlo = Benchmark()
        val random = TestRandom512(LongArray(8) { SecureRandom.readLong() })
        val buffer = BinaryBuffer()
        measureTime {
            repeat(10_000_000) {
                if(buffer.remaining == 0) {
                    buffer.reset()
                    random.read(buffer.asBinary())
                }
                monteCarlo.scatterPoint(buffer.readLong(), buffer.readLong())
            }
        }.also { println(it) }
        println(monteCarlo.distribution())
        println((monteCarlo.distribution() - PI).absoluteValue)
    }
}