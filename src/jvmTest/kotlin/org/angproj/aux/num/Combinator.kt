package org.angproj.aux.num

import org.angproj.aux.sec.SecureRandom
import kotlin.math.absoluteValue


object Combinator {

    fun numberGenerator(range: IntRange, action: (num: ByteArray) -> Unit) {
        range.forEach {
            val arr = SecureRandom.read(it.absoluteValue + 1)
            arr[0] = if(it < 0) BigSigned.NEGATIVE.signed.toByte() else BigSigned.POSITIVE.signed.toByte()
            action(arr)
        }
    }

    fun innerNumberGenerator(range: IntRange, action: (num: ByteArray) -> Unit) {
        range.forEach {
            val arr = SecureRandom.read(it.absoluteValue + 1)
            arr[0] = if(it < 0) BigSigned.NEGATIVE.signed.toByte() else BigSigned.POSITIVE.signed.toByte()
            action(arr)
        }
    }

    fun intGenerator(range: IntRange, action: (num: Int) -> Unit) {
        range.forEach {
            action(SecureRandom.readInt())
        }
    }

    fun longGenerator(range: IntRange, action: (num: Long) -> Unit) {
        range.forEach {
            action(SecureRandom.readLong())
        }
    }
}