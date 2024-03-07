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
package org.angproj.aux.pkg

public enum class Convention(public val type: Short, public val end: Byte) {
    BYTE(10000, 0),
    UBYTE(10001, 0),
    SHORT(10002, 0),
    USHORT(10003, 0),
    INT(10004, 0),
    UINT(10005, 0),
    LONG(10006, 0),
    ULONG(10007, 0),
    FLOAT(10008, 0),
    DOUBLE(10009, 0),

    //ARRAY_BYTE(10015, -15),
    ARRAY_SHORT(10020, -20),
    ARRAY_INT(10021, -21),
    ARRAY_LONG(10022, -22),
    ARRAY_FLOAT(10023, -23),
    ARRAY_DOUBLE(10024, -24),
    //ARRAY_UBYTE(10030, -30),
    //ARRAY_USHORT(10031, -31),
    //ARRAY_UINT(10032, -32),
    //ARRAY_ULONG(10033, 33),

    BLOCK(10040, -40),
    DICT(10041, -41),
    LIST(10042, -42),
    OBJECT(10043, -43),
    STRUCT(10044, -44),

    BIGINT(10050, -50),
    UUID4(10051, -51),
    STRING(10052, -52),

    RESERVED(10128, -128);
}