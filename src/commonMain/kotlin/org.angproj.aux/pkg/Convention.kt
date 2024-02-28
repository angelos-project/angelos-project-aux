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

public enum class Convention(public val type: Short) {
    BIG_INT(10240),
    UUID4(10239),

    BYTE(10000),
    UBTYE(10001),
    SHORT(10002),
    USHORT(10003),
    INT(10004),
    UINT(10005),
    LONG(10006),
    ULONG(10007),
    FLOAT(10008),
    DOUBLE(10009),

    //ARRAY_BYTE(10015),
    ARRAY_SHORT(10020),
    ARRAY_INT(10021),
    ARRAY_LONG(10022),
    ARRAY_FLOAT(10023),
    ARRAY_DOUBLE(10024),
    //ARRAY_UBYTE(10030),
    //ARRAY_USHORT(10031),
    //ARRAY_UINT(10032),
    //ARRAY_ULONG(10033),

    RESERVED(10128);
}