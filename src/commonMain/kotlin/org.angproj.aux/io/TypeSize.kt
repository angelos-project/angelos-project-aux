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

public enum class TypeSize(public val size: Int) {
    BYTE(Byte.SIZE_BYTES),
    U_BYTE(UByte.SIZE_BYTES),
    SHORT(Short.SIZE_BYTES),
    U_SHORT(UShort.SIZE_BYTES),
    INT(Int.SIZE_BYTES),
    U_INT(UInt.SIZE_BYTES),
    LONG(Long.SIZE_BYTES),
    U_LONG(ULong.SIZE_BYTES),
    FLOAT(Float.SIZE_BYTES),
    DOUBLE(Double.SIZE_BYTES);

    public companion object {
        public const val byte: Int = 1 // Byte.SIZE_BYTES
        public const val uByte: Int = 1 // UByte.SIZE_BYTES
        public const val short: Int = 2 // Short.SIZE_BYTES
        public const val uShort: Int = 2 // UShort.SIZE_BYTES
        public const val int: Int = 4 // Int.SIZE_BYTES
        public const val uInt: Int = 4 // UInt.SIZE_BYTES
        public const val long: Int = 8 // Long.SIZE_BYTES
        public const val uLong: Int = 8 // ULong.SIZE_BYTES
        public const val float: Int = 4 // Float.SIZE_BYTES
        public const val double: Int = 8 // Double.SIZE_BYTES
    }
}