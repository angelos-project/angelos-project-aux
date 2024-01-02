package org.angproj.aux.util

import kotlin.test.Test
import kotlin.test.assertTrue

class EpochTest {

    @Test
    fun epochTest() {
        assertTrue(Epoch.getEpochMilliSecs() > 0)
    }

    @Test
    fun testRandom() {
        val monteCarlo = Benchmark()
        repeat(10_000_000) {
            monteCarlo.scatterPoint(Epoch.entropy(), Epoch.entropy())
        }
        println(monteCarlo.distribution())
    }
}