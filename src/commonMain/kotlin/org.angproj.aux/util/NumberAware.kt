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

public interface NumberAware {

    public fun Short.swapEndian(): Short = withNumeral(this) { swapShort<Int>(it) }
    public fun UShort.swapEndian(): UShort = withNumeral(this) { swapShort<Int>(it.convUS2S<Int>()).convS2US<Int>() }
    public fun Int.swapEndian(): Int = withNumeral(this) { swapInt<Int>(it) }
    public fun UInt.swapEndian(): UInt = withNumeral(this) { swapInt<Int>(it.convUI2I<Int>()).convI2UI<Int>() }
    public fun Long.swapEndian(): Long = withNumeral(this) { swapLong<Long>(it) }
    public fun ULong.swapEndian(): ULong = withNumeral(this) { swapLong<Long>(it.convUL2L<Int>()).convL2UL<Int>() }
    public fun Float.swapEndian(): Float = withNumeral(this) { swapInt<Int>(it.convF2I<Int>()).convI2F<Int>() }
    public fun Double.swapEndian(): Double = withNumeral(this) { swapLong<Long>(it.convD2L<Int>()).convL2D<Int>() }

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
}


public object NumberAwareContext: NumberAware

public inline fun<reified T> withNumberAware(block: NumberAwareContext.() -> T): T = NumberAwareContext.block()

public inline fun<reified T> withNumberAware(
    array: ByteArray, block: NumberAwareContext.(array: ByteArray) -> T
): T = NumberAwareContext.block(array)