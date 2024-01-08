/**
 * Copyright (c) 2022 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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
package org.angproj.io.buf

/**
 * Imported from angelos-project-buffer package.
 */

/**
 * Retrievable interface for retrieving data from any type of retrievable interface including buffers.
 *
 * @constructor Create Retrievable
 */
public interface Retrievable {
    /**
     * Retrieve a byte from said position.
     *
     * @return A byte of data.
     */
    public fun retrieveByte(position: Int): Byte

    /**
     * Retrieve an unsigned byte from said position.
     *
     * @return An unsigned byte of data.
     */
    public fun retrieveUByte(position: Int): UByte

    /**
     * Retrieve a character from said position.
     *
     * @return A character of data.
     */
    public fun retrieveChar(position: Int): Char

    /**
     * Retrieve a short integer from said position.
     *
     * @return A short integer of data.
     */
    public fun retrieveShort(position: Int): Short

    /**
     * Retrieve an unsigned short integer from said position.
     *
     * @return An unsigned short integer of data.
     */
    public fun retrieveUShort(position: Int): UShort

    /**
     * Retrieve an integer from said position.
     *
     * @return An integer of data.
     */
    public fun retrieveInt(position: Int): Int

    /**
     * Retrieve an unsigned integer from said position.
     *
     * @return An unsigned integer of data.
     */
    public fun retrieveUInt(position: Int): UInt

    /**
     * Retrieve a long integer from said position.
     *
     * @return A long integer of data.
     */
    public fun retrieveLong(position: Int): Long

    /**
     * Retrieve an unsigned long integer from said position.
     *
     * @return An unsigned long integer of data.
     */
    public fun retrieveULong(position: Int): ULong

    /**
     * Retrieve a float from said position.
     *
     * @return A float of data.
     */
    public fun retrieveFloat(position: Int): Float

    /**
     * Retrieve a double from said position.
     *
     * @return A double of data.
     */
    public fun retrieveDouble(position: Int): Double
}