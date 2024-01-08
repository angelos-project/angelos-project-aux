package org.angproj.aux.util

import kotlin.test.Test

class RandomTest {

    @Test
    fun testEntropyRandom() {
        val generator = Random.receive(Random.lookup("EntropyRandom-SystemClock"))
        println(BinHex.encodeToHex(generator.getByteArray(8)))
    }

    @Test
    fun testNonceRandom() {
        val generator = Random.receive(Random.lookup("NonceRandom-Standard"))
        println(BinHex.encodeToHex(generator.getByteArray(8)))
    }

    @Test
    fun testSimpleRandom() {
        val generator = Random.receive(Random.lookup("SimpleRandom-Stupid"))
        println(BinHex.encodeToHex(generator.getByteArray(8)))
    }
}