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

import org.angproj.aux.buf.asWrapped
import org.angproj.aux.buf.wrap
import org.angproj.aux.io.*
import org.angproj.aux.mem.BufMgr
import org.angproj.aux.rand.AbstractSponge1024
import org.angproj.aux.rand.InitializationVector
import org.angproj.aux.util.floorMod


public class GarbageGarbler(
    public val staleSize: DataSize = DataSize._1G,
): AbstractSponge1024(), PumpWriter, PumpReader {

    private var inputCnt: Long = 0
    private var outputCnt: Long = 0
    private var _count: Int = 0

    init {
        val start = binOf(byteSize)
        InitializationVector.realTimeGatedEntropy(start)
        start.wrap { repeat(16) { absorb(readLong(), it) } }
    }

    override val outputCount: Long
        get() = inputCnt + outputCnt

    override val inputCount: Long
        get() =  inputCnt

    override val inputStale: Boolean = false

    override val outputStale: Boolean
        get() = _count >= staleSize.size

    private fun require(length: Int) {
        require(length.floorMod(byteSize) == 0) { "Garble must be divisible by the length of the inner sponge." }
    }

    override fun write(data: Segment<*>): Int {
        require(data.limit)
        BufMgr.asWrap(data) {
            val wrap = asWrapped(0)
            repeat(data.limit / byteSize) {
                repeat(16) { absorb(wrap.readLong(), it) }
                scramble()
            }
        }
        inputCnt += data.limit
        _count = 0
        return data.limit
    }

    private fun fill(data: Segment<*>): Unit = BufMgr.asWrap(data) {
        val writable = asWrapped()
        repeat(data.limit / byteSize) {
            repeat(visibleSize) { writable.writeLong(squeeze(it))}
            round()
        }
    }

    public override fun read(data: Segment<*>): Int {
        require(data.limit)
        if(data.limit + _count > staleSize.size) return 0
        fill(data)
        outputCnt += data.size
        _count += data.size
        return data.size
    }
}