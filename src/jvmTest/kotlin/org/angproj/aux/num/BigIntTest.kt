package org.angproj.aux.num

import org.angproj.aux.sec.SecureEntropy
import org.angproj.aux.sec.SecureFeed
import org.angproj.aux.sec.SecureRandom
import org.angproj.aux.util.*
import java.io.File
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
    fun testNonceGenerateGigaByte() {
        val data = ByteArray(4096)
        generateGibaByte("secure_feed.bin", 32) {
            repeat(data.size / 64) { idx ->
                SecureFeed.getFeed(data, idx * 64) }
            data
        }
    }

    fun generateGibaByte(name: String, gigs: Long, block: () -> ByteArray) {
        val targetFile = File(name)
        val output = targetFile.outputStream()
        val times = gigs * 1024L * 1024L * 1024L / block().size.toLong() + 1
        repeat(times.toInt()) {
            output.write(block())
        }
        output.close()
    }

    @Test
    fun testSomeRandom() {
        val monteCarlo = Benchmark()
        val distribution = monteCarlo(10_000_000) {
            SecureRandom.getLong()
        }
        println(distribution)
        println((distribution - PI).absoluteValue)
    }

    @Test
    fun testSomeEntropy() {
        val monteCarlo = Benchmark()
        val distribution = monteCarlo(10_000_000) {
            SecureEntropy.getEntropy()
        }
        println(distribution)
        println((distribution - PI).absoluteValue)
    }
}