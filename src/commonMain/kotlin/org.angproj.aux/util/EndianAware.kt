/**
 * Copyright (c) 2023 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
 *
 * This software is available under the terms of the MIT license. Parts are licensed
 * under different terms if stated. The legal terms are attached to the LICENSE file
 * and are made available on:
 *
 *      https://opensource.org/licenses/MIT
 *
 * SPDX-License-Identifier: MIT
 *
 * Contributors:
 *      Kristoffer Paulsson - initial implementation
 */
package org.angproj.aux.util

public interface EndianAware {

    public fun Short.asBig(): Short = if (bigEndian) this else swapEndian()
    public fun ShortArray.asBigByteArray(): ByteArray = ByteArray(Short.SIZE_BYTES * this.size).also {
        this.forEachIndexed { idx, value ->
            it.writeShortAt(
                idx * Short.SIZE_BYTES, value.asBig()
            )
        }
    }

    public fun UShort.asBig(): UShort = if (bigEndian) this else swapEndian()
    public fun Char.asBig(): Char = if (bigEndian) this else swapEndian()
    public fun Int.asBig(): Int = if (bigEndian) this else swapEndian()
    public fun IntArray.asBigByteArray(): ByteArray = ByteArray(Int.SIZE_BYTES * this.size).also {
        this.forEachIndexed { idx, value -> it.writeIntAt(idx * Int.SIZE_BYTES, value.asBig()) }
    }

    public fun UInt.asBig(): UInt = if (bigEndian) this else swapEndian()
    public fun Long.asBig(): Long = if (bigEndian) this else swapEndian()
    public fun LongArray.asBigByteArray(): ByteArray = ByteArray(Long.SIZE_BYTES * this.size).also {
        this.forEachIndexed { idx, value -> it.writeLongAt(idx * Long.SIZE_BYTES, value.asBig()) }
    }

    public fun ULong.asBig(): ULong = if (bigEndian) this else swapEndian()
    public fun Float.asBig(): Float = if (bigEndian) this else swapEndian()
    public fun FloatArray.asBigByteArray(): ByteArray = ByteArray(Float.SIZE_BYTES * this.size).also {
        this.forEachIndexed { idx, value -> it.writeFloatAt(idx * Float.SIZE_BYTES, value.asBig()) }
    }

    public fun Double.asBig(): Double = if (bigEndian) this else swapEndian()
    public fun DoubleArray.asBigByteArray(): ByteArray = ByteArray(Double.SIZE_BYTES * this.size).also {
        this.forEachIndexed { idx, value -> it.writeDoubleAt(idx * Double.SIZE_BYTES, value.asBig()) }
    }

    public fun Short.asLittle(): Short = if (bigEndian) swapEndian() else this
    public fun ShortArray.asLittleByteArray(): ByteArray = ByteArray(Short.SIZE_BYTES * this.size).also {
        this.forEachIndexed { idx, value -> it.writeShortAt(idx * Short.SIZE_BYTES, value.asLittle()) }
    }

    public fun UShort.asLittle(): UShort = if (bigEndian) swapEndian() else this
    public fun Char.asLittle(): Char = if (bigEndian) swapEndian() else this
    public fun Int.asLittle(): Int = if (bigEndian) swapEndian() else this
    public fun IntArray.asLittleByteArray(): ByteArray = ByteArray(Int.SIZE_BYTES * this.size).also {
        this.forEachIndexed { idx, value -> it.writeIntAt(idx * Int.SIZE_BYTES, value.asLittle()) }
    }

    public fun UInt.asLittle(): UInt = if (bigEndian) swapEndian() else this
    public fun Long.asLittle(): Long = if (bigEndian) swapEndian() else this
    public fun LongArray.asLittleByteArray(): ByteArray = ByteArray(Long.SIZE_BYTES * this.size).also {
        this.forEachIndexed { idx, value -> it.writeLongAt(idx * Long.SIZE_BYTES, value.asLittle()) }
    }

    public fun ULong.asLittle(): ULong = if (bigEndian) swapEndian() else this
    public fun Float.asLittle(): Float = if (bigEndian) swapEndian() else this
    public fun FloatArray.asLittleByteArray(): ByteArray = ByteArray(Float.SIZE_BYTES * this.size).also {
        this.forEachIndexed { idx, value -> it.writeFloatAt(idx * Float.SIZE_BYTES, value.asLittle()) }
    }

    public fun Double.asLittle(): Double = if (bigEndian) swapEndian() else this
    public fun DoubleArray.asLittleByteArray(): ByteArray = ByteArray(Double.SIZE_BYTES * this.size).also {
        this.forEachIndexed { idx, value -> it.writeDoubleAt(idx * Double.SIZE_BYTES, value.asLittle()) }
    }

    public companion object {
        public val bigEndian: Boolean = !Endian.native.asLittleIfUnknown()
    }
}

public object EndianAwareContext: EndianAware

public inline fun<reified T> withEndianAware(block: EndianAwareContext.() -> T): T = EndianAwareContext.block()

public inline fun<reified T> withEndianAware(
    array: ByteArray, block: EndianAwareContext.(array: ByteArray) -> T
): T = EndianAwareContext.block(array)