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
public interface BinaryReadable: Readable, ByteCount {

    public override val count: Long

    /**
     * Read next byte.
     *
     * @return A byte of data.
     */
    public override fun readByte(): Byte

    /**
     * Read next unsigned byte.
     *
     * @return An unsigned byte of data.
     */
    public override fun readUByte(): UByte

    /**
     * Read next short integer.
     *
     * @return A short integer of data.
     */
    public override fun readShort(): Short

    /**
     * Read next unsigned short integer.
     *
     * @return An unsigned short integer of data.
     */
    public override fun readUShort(): UShort

    /**
     * Read next integer.
     *
     * @return An integer of data.
     */
    public override fun readInt(): Int

    /**
     * Read next unsigned integer.
     *
     * @return An unsigned integer of data.
     */
    public override fun readUInt(): UInt

    /**
     * Read next long integer.
     *
     * @return A long integer of data.
     */
    public override fun readLong(): Long

    /**
     * Read next unsigned long integer.
     *
     * @return An unsigned long integer of data
     */
    public override fun readULong(): ULong

    /**
     * Read next float.
     *
     * @return A float of data.
     */
    public override fun readFloat(): Float

    /**
     * Read next double.
     *
     * @return A double of data.
     */
    public override fun readDouble(): Double

    /**
     * Read next short integer.
     *
     * @return A short integer of data.
     */
    public override fun readRevShort(): Short

    /**
     * Read next unsigned short integer.
     *
     * @return An unsigned short integer of data.
     */
    public override fun readRevUShort(): UShort

    /**
     * Read next integer.
     *
     * @return An integer of data.
     */
    public override fun readRevInt(): Int

    /**
     * Read next unsigned integer.
     *
     * @return An unsigned integer of data.
     */
    public override fun readRevUInt(): UInt

    /**
     * Read next long integer.
     *
     * @return A long integer of data.
     */
    public override fun readRevLong(): Long

    /**
     * Read next unsigned long integer.
     *
     * @return An unsigned long integer of data
     */
    public override fun readRevULong(): ULong

    /**
     * Read next float.
     *
     * @return A float of data.
     */
    public override fun readRevFloat(): Float

    /**
     * Read next double.
     *
     * @return A double of data.
     */
    public override fun readRevDouble(): Double
}

/*public inline fun <reified T: BinaryReadable, E: Any> T.measureBytes(expected: Int, block: Int.() -> E): E {
    val curCnt = count
    return expected.block().also {
        check((count - curCnt).toInt() == expected) {
            "Expected $expected bytes but ${count - curCnt} was read"
        }
    }
}

public inline fun <reified T: BinaryReadable> T.measureBytes(block: () -> Unit): Int {
    val curCnt = count
    block()
    return (count - curCnt).toInt()
}*/