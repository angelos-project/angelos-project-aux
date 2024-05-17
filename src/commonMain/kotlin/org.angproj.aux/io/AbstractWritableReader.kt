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

import org.angproj.aux.buf.ByteBuffer

/**
 * Entrypoint where structured data can be written and streamed data be read from.
 * */
public abstract class AbstractWritableReader: Writable, OldReader, Sizeable {
    override fun read(data: ByteBuffer): Int {
        TODO("Not yet implemented")
    }

    override fun read(length: Int): ByteArray {
        TODO("Not yet implemented")
    }

    override fun writeByte(value: Byte) {
        TODO("Not yet implemented")
    }

    override fun writeUByte(value: UByte) {
        TODO("Not yet implemented")
    }

    override fun writeChar(value: Char) {
        TODO("Not yet implemented")
    }

    override fun writeShort(value: Short) {
        TODO("Not yet implemented")
    }

    override fun writeUShort(value: UShort) {
        TODO("Not yet implemented")
    }

    override fun writeInt(value: Int) {
        TODO("Not yet implemented")
    }

    override fun writeUInt(value: UInt) {
        TODO("Not yet implemented")
    }

    override fun writeLong(value: Long) {
        TODO("Not yet implemented")
    }

    override fun writeULong(value: ULong) {
        TODO("Not yet implemented")
    }

    override fun writeFloat(value: Float) {
        TODO("Not yet implemented")
    }

    override fun writeDouble(value: Double) {
        TODO("Not yet implemented")
    }
}