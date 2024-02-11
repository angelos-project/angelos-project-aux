package org.angproj.aux.util

import org.angproj.aux.sec.SecureEntropy
import kotlin.math.absoluteValue
import kotlin.math.sqrt

class Benchmark {
    private var r: Long = 0
    private var b: Long = 0

    val n: Long
        get() = r + b

    fun scatterPoint(x: Long, y: Long) {
        val a = x / longMax
        val b = y / longMax
        val c = sqrt(a * a + b * b)
        when (c < 1) {
            true -> this.r++
            else -> this.b++
        }
    }

    fun distribution(): Double {
        val n = (r + b).toDouble()
        return 4 * r / n
    }

    operator fun invoke(loops: Int, oneValue: (iter: Int) -> Long): Double {
        var iter = 0
        repeat(loops.absoluteValue) {
            scatterPoint(oneValue(iter++), oneValue(iter++))
        }
        return distribution()
    }

    companion object {
        const val longMax = Long.MAX_VALUE.toDouble()
    }
}