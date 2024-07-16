/**
 * Copyright (c) 2021-2024 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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

/**
 * Imported from angelos-project-buffer package.
 */

/**
 * Setting bit 0 in said Byte.
 *
 * @return The modified value.
 */
public fun Byte.flipOnFlag0(): Byte = (this.toInt() or 0B00000001).toByte()

/**
 * Setting bit 1 in said Byte.
 *
 * @return The modified value.
 */
public fun Byte.flipOnFlag1(): Byte = (this.toInt() or 0B00000010).toByte()

/**
 * Setting bit 2 in said Byte.
 *
 * @return The modified value.
 */
public fun Byte.flipOnFlag2(): Byte = (this.toInt() or 0B00000100).toByte()

/**
 * Setting bit 3 in said Byte.
 *
 * @return The modified value.
 */
public fun Byte.flipOnFlag3(): Byte = (this.toInt() or 0B00001000).toByte()

/**
 * Setting bit 4 in said Byte.
 *
 * @return The modified value.
 */
public fun Byte.flipOnFlag4(): Byte = (this.toInt() or 0B00010000).toByte()

/**
 * Setting bit 5 in said Byte.
 *
 * @return The modified value.
 */
public fun Byte.flipOnFlag5(): Byte = (this.toInt() or 0B00100000).toByte()

/**
 * Setting bit 6 in said Byte.
 *
 * @return The modified value.
 */
public fun Byte.flipOnFlag6(): Byte = (this.toInt() or 0B01000000).toByte()

/**
 * Setting bit 7 in said Byte.
 *
 * @return The modified value.
 */
public fun Byte.flipOnFlag7(): Byte = (this.toInt() or -0B10000000).toByte()

/**
 * Clearing bit 0 in said Byte.
 *
 * @return The modified value.
 */
public fun Byte.flipOffFlag0(): Byte = (this.toInt() and 0B11111110).toByte()

/**
 * Clearing bit 1 in said Byte.
 *
 * @return The modified value.
 */
public fun Byte.flipOffFlag1(): Byte = (this.toInt() and 0B11111101).toByte()

/**
 * Clearing bit 2 in said Byte.
 *
 * @return The modified value.
 */
public fun Byte.flipOffFlag2(): Byte = (this.toInt() and 0B11111011).toByte()

/**
 * Clearing bit 3 in said Byte.
 *
 * @return The modified value.
 */
public fun Byte.flipOffFlag3(): Byte = (this.toInt() and 0B11110111).toByte()

/**
 * Clearing bit 4 in said Byte.
 *
 * @return The modified value.
 */
public fun Byte.flipOffFlag4(): Byte = (this.toInt() and 0B11101111).toByte()

/**
 * Clearing bit 5 in said Byte.
 *
 * @return The modified value.
 */
public fun Byte.flipOffFlag5(): Byte = (this.toInt() and 0B11011111).toByte()

/**
 * Clearing bit 6 in said Byte.
 *
 * @return The modified value.
 */
public fun Byte.flipOffFlag6(): Byte = (this.toInt() and 0B10111111).toByte()

/**
 * Clearing bit 7 in said Byte.
 *
 * @return The modified value.
 */
public fun Byte.flipOffFlag7(): Byte = (this.toInt() and 0B01111111).toByte()

/**
 * Verify state of bit 0 in said Byte.
 *
 * @return Boolean result of check.
 */
public fun Byte.checkFlag0(): Boolean = (this.toInt() and 0B00000001) == 1

/**
 * Verify state of bit 1 in said Byte.
 *
 * @return Boolean result of check.
 */
public fun Byte.checkFlag1(): Boolean = (this.toInt() and 0B00000010) == 2

/**
 * Verify state of bit 2 in said Byte.
 *
 * @return Boolean result of check.
 */
public fun Byte.checkFlag2(): Boolean = (this.toInt() and 0B00000100) == 4

/**
 * Verify state of bit 3 in said Byte.
 *
 * @return Boolean result of check.
 */
public fun Byte.checkFlag3(): Boolean = (this.toInt() and 0B00001000) == 8

/**
 * Verify state of bit 4 in said Byte.
 *
 * @return Boolean result of check.
 */
public fun Byte.checkFlag4(): Boolean = (this.toInt() and 0B00010000) == 16

/**
 * Verify state of bit 5 in said Byte.
 *
 * @return Boolean result of check.
 */
public fun Byte.checkFlag5(): Boolean = (this.toInt() and 0B00100000) == 32

/**
 * Verify state of bit 6 in said Byte.
 *
 * @return Boolean result of check.
 */
public fun Byte.checkFlag6(): Boolean = (this.toInt() and 0B01000000) == 64

/**
 * Verify state of bit 7 in said Byte.
 *
 * @return Boolean result of check.
 */

public fun Byte.checkFlag7(): Boolean = (this.toInt() and -0B10000000) == -128

public fun interface InnerJoin<X: Number, Y: Number> {
    public operator fun invoke(upper: Y, lower: Y): X
}

public fun interface Split< X: Number, Y: Number> {
    public operator fun invoke(value: X): Y
}

public val joinLong: InnerJoin<Long, Int> = InnerJoin {
        u: Int, l: Int -> ((u.toLong() shl 32 and -0x100000000) or (l.toLong() and 0xFFFFFFFF)) }
public val joinInt: InnerJoin<Int, Short> = InnerJoin {
        u: Short, l: Short -> (u.toInt() shl 16 and -0x10000) or (l.toInt() and 0xFFFF) }
public val joinShort: InnerJoin<Short, Byte> = InnerJoin {
        u: Byte, l: Byte -> ((u.toInt() shl 8 and 0xFF00) or (l.toInt() and 0xFF)).toShort() }

public val upperLong: Split<Long, Int> = Split { (it ushr 32).toInt() }
public val upperInt: Split<Int, Short> = Split { (it ushr 16).toShort() }
public val upperShort: Split<Short, Byte> = Split { (it.toInt() ushr 8).toByte() }

public val lowerLong: Split<Long, Int> = Split { it.toInt() }
public val lowerInt: Split<Int, Short> = Split { it.toShort() }
public val lowerShort: Split<Short, Byte> = Split { it.toByte() }

public object Num {

    public fun interface Swap<T: Number> {
        public operator fun invoke(value: T): T
    }

    public fun interface Convert<X, Y> {
        public operator fun invoke(value: X): Y
    }

    public val swapLong: Swap<Long> = Swap { joinLong(swapInt(lowerLong(it)), swapInt(upperLong(it))) }
    public val swapInt: Swap<Int> = Swap { joinInt(swapShort(lowerInt(it)), swapShort(upperInt(it))) }
    public val swapShort: Swap<Short> = Swap { joinShort(lowerShort(it), upperShort(it)) }

    public val convL2UL: Convert<Long, ULong> = Convert { it.toULong() }
    public val convUL2L: Convert<ULong, Long> = Convert { it.toLong() }
    public val convL2D: Convert<Long, Double> = Convert { Double.fromBits(it) }
    public val convD2L: Convert<Double, Long> = Convert { it.toBits() }

    public val convI2UI: Convert<Int, UInt> = Convert { it.toUInt() }
    public val convUI2I: Convert<UInt, Int> = Convert { it.toInt() }
    public val convI2F: Convert<Int, Float> = Convert { Float.fromBits(it) }
    public val convF2I: Convert<Float, Int> = Convert { it.toBits() }

    public val convS2US: Convert<Short, UShort> = Convert { it.toUShort() }
    public val convUS2S: Convert<UShort, Short> = Convert { it.toShort() }

    public val convB2UB: Convert<Byte, UByte> = Convert { it.toUByte() }
    public val convUB2B: Convert<UByte, Byte> = Convert { it.toByte() }
}

public fun Byte.conv2uB(): UByte = Num.convB2UB(this)
public fun UByte.conv2D(): Byte = Num.convUB2B(this)
public fun Short.conv2uS(): UShort = Num.convS2US(this)
public fun UShort.conv2S(): Short = Num.convUS2S(this)
public fun Int.conv2uI(): UInt = Num.convI2UI(this)
public fun UInt.conv2I(): Int = Num.convUI2I(this)
public fun Int.conv2F(): Float = Num.convI2F(this)
public fun Float.conv2I(): Int = Num.convF2I(this)
public fun Long.conv2uL(): ULong = Num.convL2UL(this)
public fun ULong.conv2L(): Long = Num.convUL2L(this)
public fun Long.conv2D(): Double = Num.convL2D(this)
public fun Double.conv2L(): Long = Num.convD2L(this)


/**
 * Swap endian on Short.
 *
 * @return
 */
public fun Short.swapEndian(): Short = Num.swapShort(this)

/**
 * Swap endian on UShort.
 *
 * @return
 */
public fun UShort.swapEndian(): UShort = this.conv2S().swapEndian().conv2uS()

/**
 * Swap endian on Char.
 *
 * @return
 */
public fun Char.swapEndian(): Char = this.code.toShort().swapEndian().toInt().toChar()

/**
 * Swap endian on Int.
 *
 * @return
 */
public fun Int.swapEndian(): Int = Num.swapInt(this)

/**
 * Swap endian on UInt.
 *
 * @return
 */
public fun UInt.swapEndian(): UInt = this.conv2I().swapEndian().conv2uI()

/**
 * Swap endian on Long.
 *
 * @return
 */
public fun Long.swapEndian(): Long = Num.swapLong(this)

/**
 * Swap endian on ULong.
 *
 * @return
 */
public fun ULong.swapEndian(): ULong = this.conv2L().swapEndian().conv2uL()

/**
 * Swap endian on Float.
 *
 * @return
 */
public fun Float.swapEndian(): Float = this.conv2I().swapEndian().conv2F()

/**
 * Swap endian on Double.
 *
 * @return
 */
public fun Double.swapEndian(): Double = this.conv2L().swapEndian().conv2D()