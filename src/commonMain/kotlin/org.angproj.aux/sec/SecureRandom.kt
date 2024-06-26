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
package org.angproj.aux.sec

import org.angproj.aux.io.*
import org.angproj.aux.mem.Default
import org.angproj.aux.pipe.*
import org.angproj.aux.util.*
import kotlin.native.concurrent.ThreadLocal

/**
 * Portions a secure feed of random into a serviceable format of data for cryptographically secure use.
 * */
@ThreadLocal
public object SecureRandom : BinaryReadable, Reader {

    private val sink: BinarySink = PullPipe<BinaryType>(
        Default,
        PumpSource(SecureFeed),
        DataSize._1K,
        DataSize._1K
    ).getSink()

    override fun readByte(): Byte = sink.readByte()
    override fun readUByte(): UByte = sink.readUByte()
    override fun readShort(): Short = sink.readShort()
    override fun readUShort(): UShort = sink.readUShort()
    override fun readInt(): Int = sink.readInt()
    override fun readUInt(): UInt = sink.readUInt()
    override fun readLong(): Long = sink.readLong()
    override fun readULong(): ULong = sink.readULong()
    override fun readFloat(): Float = sink.readFloat()
    override fun readDouble(): Double = sink.readDouble()

    override fun read(data: Segment): Int {
        val binary = Binary(data)
        val index = chunkLoop<Reify>(0, binary.limit, Long.SIZE_BYTES) {
            binary.storeLong(it, readLong())
        }
        return chunkLoop<Reify>(index, binary.limit, Byte.SIZE_BYTES) {
            binary.storeByte(it, readByte())
        }
    }
}