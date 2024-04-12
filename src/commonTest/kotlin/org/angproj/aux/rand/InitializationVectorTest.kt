package org.angproj.aux.rand

import org.angproj.aux.util.Benchmark
import org.angproj.aux.util.BinHex
import org.angproj.aux.util.readLongAt
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.test.Test

class InitializationVectorTest {

    //@Test
    fun testPrintEntropy() {
        val data = ByteArray(16)
        repeat(100) {
            InitializationVector.realTimeGatedEntropy(data)
            println(BinHex.encodeToHex(data))
        }
    }

    //@Test
    fun testMonteCarlo() {
        val monteCarlo = Benchmark()
        val data = ByteArray(16)
        repeat(10_000_000) {
            InitializationVector.realTimeGatedEntropy(data)
            monteCarlo.scatterPoint(data.readLongAt(0), data.readLongAt(8))
        }
        println(monteCarlo.distribution())
        println((monteCarlo.distribution() - PI).absoluteValue)
    }
}