package org.angproj.aux.rand

import org.angproj.aux.io.binOf
import org.angproj.aux.util.Benchmark
import kotlin.math.PI
import kotlin.math.absoluteValue

class InitializationVectorTest {

    //@Test
    fun testPrintEntropy() {
        val data = binOf(16)
        repeat(100) {
            InitializationVector.realTimeGatedEntropy(data)
            //println(BinHex.encodeToHex(data))
        }
    }

    //@Test
    fun testMonteCarlo() {
        val monteCarlo = Benchmark()
        val data = binOf(16)
        repeat(10_000_000) {
            InitializationVector.realTimeGatedEntropy(data)
            monteCarlo.scatterPoint(data.retrieveLong(0), data.retrieveLong(8))
        }
        println(monteCarlo.distribution())
        println((monteCarlo.distribution() - PI).absoluteValue)
    }
}