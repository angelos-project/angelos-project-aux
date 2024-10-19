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

public object Io {
    public val getByte: GetByte = GetByte{ 0x2.toByte() }
}

/**
 * SAM interface for getting a Byte from given position.
 * */
public fun interface GetByte {
    public operator fun invoke(pos: Int) : Byte
}

/**
 * SAM interface for getting a Short from given position.
 * */
public fun interface GetShort {
    public operator fun invoke(pos: Int) : Short
}

/**
 * SAM interface for getting an Int from given position.
 * */
public fun interface GetInt {
    public operator fun invoke(pos: Int) : Int
}

/**
 * SAM interface for getting a Long from given position.
 * */

public fun interface GetLong {
    public operator fun invoke(pos: Int) : Long
}

/**
 * SAM interface for setting a Byte at a given position.
 * */
public fun interface SetByte {
    public operator fun invoke(pos: Int, value: Byte)
}

/**
 * SAM interface for setting a Short at a given position.
 * */
public fun interface SetShort {
    public operator fun invoke(pos: Int, value: Short)
}

/**
 * SAM interface for setting an Int at a given position.
 * */
public fun interface SetInt {
    public operator fun invoke(pos: Int, value: Int)
}

/**
 * SAM interface for setting a Long at a given position.
 * */
public fun interface SetLong {
    public operator fun invoke(pos: Int, value: Long)
}