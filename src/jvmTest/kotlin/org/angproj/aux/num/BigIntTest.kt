package org.angproj.aux.num

import org.angproj.aux.sec.SecureEntropy
import org.angproj.aux.sec.SecureFeed
import org.angproj.aux.util.*
import org.angproj.aux.util.epochEntropy
import java.io.File
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.math.max
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
        val buffer = ByteArray(32)
        SecureEntropy.getEntropy(buffer)

        var counter: Long = 0
        var state0: Long = buffer.readLongAt(0)
        var state1: Long = buffer.readLongAt(8)
        var state2: Long = buffer.readLongAt(16)
        var state3: Long = buffer.readLongAt(24)

        val monteCarlo = Benchmark()
        val distribution = monteCarlo(10_000_000){
            if(it.mod(4) == 0) {
                counter++
                val temp = -state0.inv() * 3
                state0 = -state1.inv() * 5
                state1 = -state2.inv() * 7
                state2 = -state3.inv() * 11
                state3 = temp
            }
            when(it.mod(4)) {
                0 -> state0
                1 -> state1
                2 -> state2
                3 -> state3
                else -> error("")
            }
        }
        println(distribution)
        println((distribution - PI).absoluteValue)

        /*repeat(100) {
            counter++
            val temp = (-state0.inv() xor counter) * 3
            state0 = -state1.inv() * 5
            state1 = -state2.inv() * 7
            state2 = -state3.inv() * 11
            state3 = temp

            buffer.writeLongAt(0, state0)
            buffer.writeLongAt(8, state1)
            buffer.writeLongAt(16, state2)
            buffer.writeLongAt(24, state3)

            println(BinHex.encodeToHex(buffer))
        }*/
    }

    @Test
    fun testSomeEntropy() {
        var entropy: Long = 0xFFF73E99668196E9uL.toLong()
        var counter: Long = 0xFFFF7D5BF9259763uL.toLong()

        val monteCarlo = Benchmark()
        val distribution = monteCarlo(10_000_000){
            val (timestamp, nanos) = epochEntropy()
            counter++
            entropy = ((-entropy.inv() xor timestamp) * 3) xor ((-entropy.inv() xor nanos) * 5) * -counter.inv()

            entropy
        }
        println(distribution)
        println((distribution - PI).absoluteValue)

        /*repeat(100) {
            val (timestamp, nanos) = epochEntropy()
            counter++
            entropy = ((-entropy.inv() xor timestamp) * 3) xor ((-entropy.inv() xor nanos) * 5) * -counter.inv()
            buffer.writeLongAt(0, entropy)
            println(BinHex.encodeToHex(buffer))
        }*/
    }
}