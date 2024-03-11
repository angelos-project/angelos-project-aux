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

public object DevNull : Writer, Writable {
    override fun writeByte(value: Byte) {}
    override fun writeUByte(value: UByte) {}
    override fun writeChar(value: Char) {}
    override fun writeShort(value: Short) {}
    override fun writeUShort(value: UShort) {}
    override fun writeInt(value: Int) {}
    override fun writeUInt(value: UInt) {}
    override fun writeLong(value: Long) {}
    override fun writeULong(value: ULong) {}
    override fun writeFloat(value: Float) {}
    override fun writeDouble(value: Double) {}

    override fun write(data: ByteArray): Int = (data.size).also { data.fill(0) }
}