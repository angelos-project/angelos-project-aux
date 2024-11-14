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

    public fun Short.swapEndian(): Short = I.swapShort<Unit>(this)
    public fun UShort.swapEndian(): UShort = I.convS2US<Unit>(I.swapShort<Unit>(I.convUS2S<Unit>(this)))
    public fun Int.swapEndian(): Int = I.swapInt<Unit>(this)
    public fun UInt.swapEndian(): UInt = I.convI2UI<Unit>(I.swapInt<Unit>(I.convUI2I<Unit>(this)))
    public fun Long.swapEndian(): Long = I.swapLong<Long>(this)
    public fun ULong.swapEndian(): ULong = I.convL2UL<Unit>(I.swapLong<Long>(I.convUL2L<Unit>(this)))
    public fun Float.swapEndian(): Float = I.convI2F<Unit>(I.swapInt<Unit>(I.convF2I<Unit>(this)))
    public fun Double.swapEndian(): Double = I.convL2D<Unit>(I.swapLong<Long>(I.convD2L<Unit>(this)))

    public fun Byte.conv2uB(): UByte = I.convB2UB<Unit>(this)
    public fun UByte.conv2B(): Byte = I.convUB2B<Unit>(this)
    public fun Short.conv2uS(): UShort = I.convS2US<Unit>(this)
    public fun UShort.conv2S(): Short = I.convUS2S<Unit>(this)
    public fun Int.conv2uI(): UInt = I.convI2UI<Unit>(this)
    public fun UInt.conv2I(): Int = I.convUI2I<Unit>(this)
    public fun Int.conv2F(): Float = I.convI2F<Unit>(this)
    public fun Float.conv2I(): Int = I.convF2I<Unit>(this)
    public fun Long.conv2uL(): ULong = I.convL2UL<Unit>(this)
    public fun ULong.conv2L(): Long = I.convUL2L<Unit>(this)
    public fun Long.conv2D(): Double = I.convL2D<Unit>(this)
    public fun Double.conv2L(): Long = I.convD2L<Unit>(this)
}


public object NumberAwareContext: NumberAware

public inline fun<reified T> withNumberAware(block: NumberAwareContext.() -> T): T = NumberAwareContext.block()

public inline fun<reified T> withNumberAware(
    array: ByteArray, block: NumberAwareContext.(array: ByteArray) -> T
): T = NumberAwareContext.block(array)