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

public data class Convention(public val type: Short, public val end: Byte) {
    public companion object
}

public val Convention.Companion.BYTE: Convention by lazy { Convention(10000, 0) }
public val Convention.Companion.UBYTE: Convention by lazy { Convention(10001, 0) }
public val Convention.Companion.SHORT: Convention by lazy { Convention(10002, 0) }
public val Convention.Companion.USHORT: Convention by lazy { Convention(10003, 0) }
public val Convention.Companion.INT: Convention by lazy { Convention(10004, 0) }
public val Convention.Companion.UINT: Convention by lazy { Convention(10005, 0) }
public val Convention.Companion.LONG: Convention by lazy { Convention(10006, 0) }
public val Convention.Companion.ULONG: Convention by lazy { Convention(10007, 0) }
public val Convention.Companion.FLOAT: Convention by lazy { Convention(10008, 0) }
public val Convention.Companion.DOUBLE: Convention by lazy { Convention(10009, 0) }

public val Convention.Companion.ARRAY_BYTE: Convention by lazy { Convention(10015, -15) }
public val Convention.Companion.ARRAY_SHORT: Convention by lazy { Convention(10020, -20) }
public val Convention.Companion.ARRAY_INT: Convention by lazy { Convention(10021, -21) }
public val Convention.Companion.ARRAY_LONG: Convention by lazy { Convention(10022, -22) }
public val Convention.Companion.ARRAY_FLOAT: Convention by lazy { Convention(10023, -23) }
public val Convention.Companion.ARRAY_DOUBLE: Convention by lazy { Convention(10024, -24) }
public val Convention.Companion.ARRAY_UBYTE: Convention by lazy { Convention(10030, -30) }
public val Convention.Companion.ARRAY_USHORT: Convention by lazy { Convention(10031, -31) }
public val Convention.Companion.ARRAY_UINT: Convention by lazy { Convention(10032, -32) }
public val Convention.Companion.ARRAY_ULONG: Convention by lazy { Convention(10033, -33) }

public val Convention.Companion.BLOCK: Convention by lazy { Convention(10040, -40) }
public val Convention.Companion.DICT: Convention by lazy { Convention(10041, -41) }
public val Convention.Companion.LIST: Convention by lazy { Convention(10042, -42) }
public val Convention.Companion.OBJECT: Convention by lazy { Convention(10043, -43) }
public val Convention.Companion.STRUCT: Convention by lazy { Convention(10044, -44) }
public val Convention.Companion.CHECK: Convention by lazy { Convention(10045, -45) }
public val Convention.Companion.ARRAY: Convention by lazy { Convention(10046, -46) }

// public val Convention.Companion.BIGINT: Convention by lazy { Convention(10050, -50) }
// public val Convention.Companion.UUID4: Convention by lazy { Convention(10051, -51) }
public val Convention.Companion.STRING: Convention by lazy { Convention(10052, -52) }

public val Convention.Companion.RESERVED: Convention by lazy { Convention(10128, -128) }

/*public enum class Convention(public val type: Short, public val end: Byte) {
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

    ARRAY_BYTE(10015, -15),
    ARRAY_SHORT(10020, -20),
    ARRAY_INT(10021, -21),
    ARRAY_LONG(10022, -22),
    ARRAY_FLOAT(10023, -23),
    ARRAY_DOUBLE(10024, -24),
    ARRAY_UBYTE(10030, -30),
    ARRAY_USHORT(10031, -31),
    ARRAY_UINT(10032, -32),
    ARRAY_ULONG(10033, -33),

    BLOCK(10040, -40),
    DICT(10041, -41),
    LIST(10042, -42),
    OBJECT(10043, -43),
    STRUCT(10044, -44),
    CHECK(10045, -45),
    ARRAY(10046, -46),

    BIGINT(10050, -50),
    UUID4(10051, -51),
    STRING(10052, -52),

    RESERVED(10128, -128);
}*/