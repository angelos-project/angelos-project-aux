/**
 * Copyright (c) 2024 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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
package org.angproj.aux.io

public enum class TypeBits(public val bits: Int) {
    BYTE(Byte.SIZE_BITS),
    U_BYTE(UByte.SIZE_BITS),
    SHORT(Short.SIZE_BITS),
    U_SHORT(UShort.SIZE_BITS),
    INT(Int.SIZE_BITS),
    U_INT(UInt.SIZE_BITS),
    LONG(Long.SIZE_BITS),
    U_LONG(ULong.SIZE_BITS),
    FLOAT(Float.SIZE_BITS),
    DOUBLE(Double.SIZE_BITS)
}