package org.angproj.aux.num

import org.angproj.aux.io.Binary
import org.angproj.aux.io.toByteArray
import org.angproj.aux.sec.SecureRandom
import kotlin.math.absoluteValue


object Combinator {

    fun numberGenerator(range: IntRange, action: (num: ByteArray) -> Unit) {
        range.forEach {
            val bin = Binary(it.absoluteValue + 1)
            SecureRandom.read(bin.segment)
            bin.storeByte(0, if(it < 0) BigSigned.NEGATIVE.signed.toByte() else BigSigned.POSITIVE.signed.toByte())
            action(bin.toByteArray())
        }
    }

    fun innerNumberGenerator(range: IntRange, action: (num: ByteArray) -> Unit) {
        range.forEach {
            val bin = Binary(it.absoluteValue + 1)
            SecureRandom.read(bin.segment)
            bin.storeByte(0, if(it < 0) BigSigned.NEGATIVE.signed.toByte() else BigSigned.POSITIVE.signed.toByte())
            action(bin.toByteArray())
        }
    }

    fun intGenerator(range: IntRange, action: (num: Int) -> Unit) {
        range.forEach { _ ->
            action(SecureRandom.readInt())
        }
    }

    fun longGenerator(range: IntRange, action: (num: Long) -> Unit) {
        range.forEach { _ ->
            action(SecureRandom.readLong())
        }
    }
}