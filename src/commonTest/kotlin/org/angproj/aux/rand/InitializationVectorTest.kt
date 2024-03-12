package org.angproj.aux.rand

import kotlin.test.Test

class InitializationVectorTest {

    val prime200 = listOf(
        3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101,
        103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197,
        199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293
    )

    val noblePrimes = listOf(
        5, 13, 17, 29, 37, 41, 53, 61, 73, 89, 97, 101, 109, 113, 137,
        149, 157, 173, 181, 193, 197, 229, 233, 241, 257, 269, 277, 281, 293
    )

    @Test
    fun testStuff() {
        InitializationVector.entries.forEach {
            println(it.iv.toString(2))
        }
    }

    @Test
    fun testIteration() {
        prime200.forEach { factor ->
                println("FACTOR: $factor")
            InitializationVector.entries.forEach { iv ->
                val curIV = iv.iv.toInt() and 0xfffff
                var state = curIV
                val total = mutableSetOf<Int>()
                repeat(1) { rnd ->
                    repeat(0xfffff) {
                        total.add(state)
                        state = (-state.inv() * factor) and 0xfffff
                    }
                }
                println((total.size / (0xfffff).toFloat()))
            }
        }
    }
}