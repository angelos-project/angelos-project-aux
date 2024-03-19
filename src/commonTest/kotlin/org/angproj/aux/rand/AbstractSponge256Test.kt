package org.angproj.aux.rand

import org.angproj.aux.io.Reader
import org.angproj.aux.io.SizeMode
import org.angproj.aux.sec.SecureRandom
import org.angproj.aux.util.Benchmark
import org.angproj.aux.io.DataSize
import org.angproj.aux.util.DataBuffer
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

    override fun read(data: ByteArray): Int {
        require(data.size.floorMod(byteSize) == 0)
        fill(data) { round() }
        return data.size
    }

    override fun read(length: Int): ByteArray {
        TODO("Will not be implemented!")
    }
}

class AbstractSponge256Test {
    @Test
    fun testMonteCarlo() {
        val monteCarlo = Benchmark()
        val random = TestRandom256(LongArray(4) { SecureRandom.readLong() })
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