package org.angproj.aux.util

import kotlin.test.Test

class NonceTest {

    @Test
    fun testSomeEntropy() {
        repeat(10_001) {
            println(BinHex.encodeToHex(Nonce.someEntropy().toByteArray()))
        }
    }

    @Test
    fun testSomeEntropy2() {
        val entropy = ByteArray(32)
        repeat(10_001) {
            Nonce.someEntropy(entropy)
            println(BinHex.encodeToHex(entropy))
        }
    }

    @Test
    fun testPaulssonEntropy() {
        repeat(1000) {
            println(Epoch.entropy())
        }
    }
}