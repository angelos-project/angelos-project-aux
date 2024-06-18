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
 * Storable interface for saving data to any type of storable interface including buffers.
 *
 * @constructor Create Storable
 */
public interface Storable {
    /**
     * Store a byte unto said position.
     *
     * @param value A byte of data.
     */
    public fun storeByte(position: Int, value: Byte)

    /**
     * Store a unsigned byte unto said position.
     *
     * @param value An unsigned byte of data.
     */
    public fun storeUByte(position: Int, value: UByte)

    /**
     * Store a short integer unto said position.
     *
     * @param value A short integer of data.
     */
    public fun storeShort(position: Int, value: Short)

    /**
     * Store an unsigned short integer unto said position.
     *
     * @param value An unsigned short integer of data.
     */
    public fun storeUShort(position: Int, value: UShort)

    /**
     * Store an integer unto said position.
     *
     * @param value An integer of data.
     */
    public fun storeInt(position: Int, value: Int)

    /**
     * Store an unsigned integer unto said position.
     *
     * @param value An unsigned integer of data.
     */
    public fun storeUInt(position: Int, value: UInt)

    /**
     * Store a long integer unto said position.
     *
     * @param value A long integer of data.
     */
    public fun storeLong(position: Int, value: Long)

    /**
     * Store an unsigned long integer unto said position.
     *
     * @param value An unsigned long integer of data.
     */
    public fun storeULong(position: Int, value: ULong)

    /**
     * Store a float unto said position.
     *
     * @param value A float of data.
     */
    public fun storeFloat(position: Int, value: Float)

    /**
     * Store a double unto said position.
     *
     * @param value A double of data.
     */
    public fun storeDouble(position: Int, value: Double)
}