/**
 * Copyright (c) 2022-2024 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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

/**
 * Imported from angelos-project-buffer package.
 */

/**
 * Readable interface for reading data from any type of readable interface including buffers.
 *
 * @constructor Create Readable
 */
public interface Readable {
    /**
     * Read next byte.
     *
     * @return A byte of data.
     */
    public fun readByte(): Byte

    /**
     * Read next unsigned byte.
     *
     * @return An unsigned byte of data.
     */
    public fun readUByte(): UByte

    /**
     * Read next character.
     *
     * @return A character of data.
     */
    public fun readChar(): Char

    /**
     * Read next short integer.
     *
     * @return A short integer of data.
     */
    public fun readShort(): Short

    /**
     * Read next unsigned short integer.
     *
     * @return An unsigned short integer of data.
     */
    public fun readUShort(): UShort

    /**
     * Read next integer.
     *
     * @return An integer of data.
     */
    public fun readInt(): Int

    /**
     * Read next unsigned integer.
     *
     * @return An unsigned integer of data.
     */
    public fun readUInt(): UInt

    /**
     * Read next long integer.
     *
     * @return A long integer of data.
     */
    public fun readLong(): Long

    /**
     * Read next unsigned long integer.
     *
     * @return An unsigned long integer of data
     */
    public fun readULong(): ULong

    /**
     * Read next float.
     *
     * @return A float of data.
     */
    public fun readFloat(): Float

    /**
     * Read next double.
     *
     * @return A double of data.
     */
    public fun readDouble(): Double
}