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
import org.angproj.aux.mem.BufMgr
import org.angproj.aux.mem.Default
import org.angproj.aux.pipe.*
import org.angproj.aux.util.chunkLoop
import kotlin.native.concurrent.ThreadLocal

/**
 * Portions a secure feed of random into a serviceable format of data for cryptographically secure use.
 * */
@ThreadLocal
public object SecureRandom : BinaryReadable, PumpReader, Reader {

    private val sink: BinarySink = PullPipe<BinaryType>(
        Default,
        PumpSource(SecureFeed),
        DataSize._1K,
        DataSize._1K
    ).getSink()

    override val count: Long
        get() = sink.count

    private var _outputCnt: Long = 0
    override val outputCount: Long
        get() = _outputCnt

    override val outputStale: Boolean
        get() = !sink.isOpen()

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

    override fun readRevShort(): Short = sink.readRevShort()
    override fun readRevUShort(): UShort = sink.readRevUShort()
    override fun readRevInt(): Int = sink.readRevInt()
    override fun readRevUInt(): UInt = sink.readRevUInt()
    override fun readRevLong(): Long = sink.readRevLong()
    override fun readRevULong(): ULong = sink.readRevULong()
    override fun readRevFloat(): Float = sink.readRevFloat()
    override fun readRevDouble(): Double = sink.readRevDouble()

    override fun read(data: Segment<*>): Int = BufMgr.asWrap(data) {
        val index = chunkLoop<Unit>(0, limit, Long.SIZE_BYTES) {
            storeLong(it, readLong())
        }
        chunkLoop<Unit>(index, limit, Byte.SIZE_BYTES) {
            storeByte(it, readByte())
        }.also { _outputCnt += it }
    }

    override fun read(bin: Binary): Int = read(bin._segment)
}