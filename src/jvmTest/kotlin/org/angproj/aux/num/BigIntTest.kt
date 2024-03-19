package org.angproj.aux.num

import org.angproj.aux.sec.SecureRandom
import org.angproj.aux.util.*
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.test.Test

class BigIntBasicTest {

    val vectorList1 = Combinator.generateValueVector()
    val vectorList2 = Combinator.generateValueVector()

    @Test
    fun compareToTest() {
        Combinator.doMatrixIntTests(vectorList1, vectorList2) { xbi, ybi, x, y ->
            Pair(xbi.compareTo(ybi).state, x.compareTo(y))
        }
    }

    @Test
    fun negateTest() {
        Combinator.doVectorTests(vectorList1) { xbi, x ->
            Pair(xbi.negate(), x.negate())
        }
    }

    @Test
    fun absTest() {
        Combinator.doVectorTests(vectorList1) { xbi, x ->
            Pair(xbi.abs(), x.abs())
        }
    }

    @Test
    fun testSomeRandom() {
        val monteCarlo = Benchmark()
        val distribution = monteCarlo(10_000_000) {
            SecureRandom.readLong()
        }
        println(distribution)
        println((distribution - PI).absoluteValue)
    }
}