/**
 * Copyright (c) 2021-2023 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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
 * Swap endian on Short.
 *
 * @return
 */
fun Short.swapEndian(): Short = reverseShort(this)

/**
 * Swap endian on UShort.
 *
 * @return
 */
fun UShort.swapEndian(): UShort = reverseShort(this.toShort()).toUShort()

/**
 * Swap endian on Char.
 *
 * @return
 */
fun Char.swapEndian(): Char = reverseShort(this.code.toShort()).toInt().toChar()

/**
 * Swap endian on Int.
 *
 * @return
 */
fun Int.swapEndian(): Int = reverseInt(this)

/**
 * Swap endian on UInt.
 *
 * @return
 */
fun UInt.swapEndian(): UInt = reverseInt(this.toInt()).toUInt()

/**
 * Swap endian on Long.
 *
 * @return
 */
fun Long.swapEndian(): Long = reverseLong(this)

/**
 * Swap endian on ULong.
 *
 * @return
 */
fun ULong.swapEndian(): ULong = reverseLong(this.toLong()).toULong()

/**
 * Swap endian on Float.
 *
 * @return
 */
fun Float.swapEndian(): Float = Float.fromBits(reverseInt(this.toBits()))

/**
 * Swap endian on Double.
 *
 * @return
 */
fun Double.swapEndian(): Double = Double.fromBits(reverseLong(this.toBits()))