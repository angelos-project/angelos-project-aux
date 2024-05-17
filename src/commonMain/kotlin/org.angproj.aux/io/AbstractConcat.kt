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

import org.angproj.aux.util.NullObject
import kotlin.math.min

public abstract class AbstractConcat(
    public override val dataSize: DataSize,
    protected val queueSize: Int
): OldReader, OldWriter, Sizeable {

    override val sizeMode: SizeMode = SizeMode.MAXIMUM

    protected val queue: MutableList<ByteArray> = mutableListOf()

    private var lastPos: Int = 0

    public val isEmpty: Boolean
        get() = queue.size == 0

    public val isFull: Boolean
        get() = queue.size == queueSize

    private var _eof: Boolean = false
    public val eof: Boolean
        get() = _eof

    public val isClosed: Boolean
        get() = eof == true and isEmpty

    protected fun readable(): Int = queue.sumOf { it.size } - lastPos
    protected fun calcReadSize(size: Int): Int = min(size, readable())

    private fun fill(data: ByteArray, size: Int): Int {
        var readSize = size

        val begin = min(readSize, dataSize.size).also { readSize -= it }
        val loop = readSize.div(dataSize.size).also { readSize %= dataSize.size }
        val finish = readSize

        var writeOffset = 0

        if(begin > 0) with(queue.removeAt(queue.lastIndex)) {
            copyInto(data, writeOffset, lastPos, begin)
            when(begin == this.size - lastPos) {
                true -> lastPos = 0
                else -> lastPos += begin
            }
            writeOffset += begin
        }

        repeat(loop) {
            with(queue.removeAt(queue.lastIndex)) {
                copyInto(data, writeOffset, lastPos, begin)
                writeOffset += dataSize.size
            }
        }

        if(finish > 0) with(queue.removeAt(queue.lastIndex)) {
            copyInto(data, writeOffset, lastPos, finish)
            lastPos = finish
            writeOffset += finish
        }

        return writeOffset
    }

    override fun read(data: ByteArray): Int {
        if(data.isEmpty() || isEmpty) return 0
        return fill(data, calcReadSize(data.size))
    }

    override fun read(length: Int): ByteArray {
        if(length == 0 || isEmpty) return NullObject.byteArray
        return ByteArray(calcReadSize(length)).also { fill(it, it.size) }
    }

    override fun write(data: ByteArray): Int {
        require(!_eof) { "Is closed due to EOF." }
        require(!isFull) { "Buffer queue is full." }
        require(data.size <= dataSize.size) { "Overflow, bytearray is over-sized." }

        if(data.isNotEmpty()) queue.add(0, data)
        if(data.size < dataSize.size) _eof = true

        return data.size
    }
}