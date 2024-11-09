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

    public fun Short.swapEndian(): Short = withNumeral(this) { swapShort<Unit>(it) }
    public fun UShort.swapEndian(): UShort = withNumeral(this) { swapShort<Unit>(it.convUS2S<Unit>()).convS2US<Unit>() }
    public fun Int.swapEndian(): Int = withNumeral(this) { swapInt<Unit>(it) }
    public fun UInt.swapEndian(): UInt = withNumeral(this) { swapInt<Unit>(it.convUI2I<Unit>()).convI2UI<Unit>() }
    public fun Long.swapEndian(): Long = withNumeral(this) { swapLong<Long>(it) }
    public fun ULong.swapEndian(): ULong = withNumeral(this) { swapLong<Long>(it.convUL2L<Unit>()).convL2UL<Unit>() }
    public fun Float.swapEndian(): Float = withNumeral(this) { swapInt<Unit>(it.convF2I<Unit>()).convI2F<Unit>() }
    public fun Double.swapEndian(): Double = withNumeral(this) { swapLong<Long>(it.convD2L<Unit>()).convL2D<Unit>() }

    public fun Byte.conv2uB(): UByte = withNumeral(this) { it.convB2UB<Unit>() }
    public fun UByte.conv2B(): Byte = withNumeral(this) { it.convUB2B<Unit>() }
    public fun Short.conv2uS(): UShort = withNumeral(this) { it.convS2US<Unit>() }
    public fun UShort.conv2S(): Short = withNumeral(this) { it.convUS2S<Unit>() }
    public fun Int.conv2uI(): UInt = withNumeral(this) { it.convI2UI<Unit>() }
    public fun UInt.conv2I(): Int = withNumeral(this) { it.convUI2I<Unit>() }
    public fun Int.conv2F(): Float = withNumeral(this) { it.convI2F<Unit>() }
    public fun Float.conv2I(): Int = withNumeral(this) { it.convF2I<Unit>() }
    public fun Long.conv2uL(): ULong = withNumeral(this) { it.convL2UL<Unit>() }
    public fun ULong.conv2L(): Long = withNumeral(this) { it.convUL2L<Unit>() }
    public fun Long.conv2D(): Double = withNumeral(this) { it.convL2D<Unit>() }
    public fun Double.conv2L(): Long = withNumeral(this) { it.convD2L<Unit>() }
}


public object NumberAwareContext: NumberAware

public inline fun<reified T> withNumberAware(block: NumberAwareContext.() -> T): T = NumberAwareContext.block()

public inline fun<reified T> withNumberAware(
    array: ByteArray, block: NumberAwareContext.(array: ByteArray) -> T
): T = NumberAwareContext.block(array)