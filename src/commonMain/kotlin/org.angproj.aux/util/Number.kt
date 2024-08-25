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

import org.angproj.aux.util.withNumeral.convB2UB


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


public fun Byte.conv2uB(): UByte = withNumeral(this) { it.convB2UB<Int>() }
public fun UByte.conv2B(): Byte = withNumeral(this) { it.convUB2B<Int>() }
public fun Short.conv2uS(): UShort = withNumeral(this) { it.convS2US<Int>() }
public fun UShort.conv2S(): Short = withNumeral(this) { it.convUS2S<Int>() }
public fun Int.conv2uI(): UInt = withNumeral(this) { it.convI2UI<Int>() }
public fun UInt.conv2I(): Int = withNumeral(this) { it.convUI2I<Int>() }
public fun Int.conv2F(): Float = withNumeral(this) { it.convI2F<Int>() }
public fun Float.conv2I(): Int = withNumeral(this) { it.convF2I<Int>() }
public fun Long.conv2uL(): ULong = withNumeral(this) { it.convL2UL<Int>() }
public fun ULong.conv2L(): Long = withNumeral(this) { it.convUL2L<Int>() }
public fun Long.conv2D(): Double = withNumeral(this) { it.convL2D<Int>() }
public fun Double.conv2L(): Long = withNumeral(this) { it.convD2L<Int>() }


/**
 * Swap endian on Short.
 *
 * @return
 */
public fun Short.swapEndian(): Short = withNumberAware { swapEndian() }

/**
 * Swap endian on UShort.
 *
 * @return
 */
public fun UShort.swapEndian(): UShort = withNumberAware { swapEndian() }

/**
 * Swap endian on Char.
 *
 * @return
 */
public fun Char.swapEndian(): Char = withNumberAware { swapEndian() }

/**
 * Swap endian on Int.
 *
 * @return
 */
public fun Int.swapEndian(): Int = withNumberAware { swapEndian() }

/**
 * Swap endian on UInt.
 *
 * @return
 */
public fun UInt.swapEndian(): UInt = withNumberAware { swapEndian() }

/**
 * Swap endian on Long.
 *
 * @return
 */
public fun Long.swapEndian(): Long = withNumberAware { swapEndian() }

/**
 * Swap endian on ULong.
 *
 * @return
 */
public fun ULong.swapEndian(): ULong = withNumberAware { swapEndian() }

/**
 * Swap endian on Float.
 *
 * @return
 */
public fun Float.swapEndian(): Float = withNumberAware { swapEndian() }

/**
 * Swap endian on Double.
 *
 * @return
 */
public fun Double.swapEndian(): Double = withNumberAware { swapEndian() }