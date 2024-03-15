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
package org.angproj.aux.util

import org.angproj.aux.codec.AbstractBuffer

public class MultiBuffer(
    public val bufSize: BufferSize = BufferSize._4K,
    public val bufMax: Int = 4
): AbstractBuffer() {

    private var _index: Int = 0
    public val index: Int
        get() = _index

    public val queue: MutableList<DataBuffer> = mutableListOf(DataBuffer(bufSize))
    private lateinit var current: DataBuffer

    override lateinit var _data: ByteArray
    override var _position: Int = 0
    override var _limit: Int = 0

    init {
        loadIndexToCurrent()
    }
    override val size: Int
        get() = queue.size * bufSize.size

    override val position: Int
        get() {
            var pos = 0
            (0 until _index).forEach { pos += queue[it].limit }
            pos += _position
            return pos
        }

    override val limit: Int
        get() {
            var lim = 0
            queue.forEach { lim += it.limit }
            return lim
        }

    override val remaining: Int
        get() = limit - position

    private fun loadIndexToCurrent() {
        current.rewind()
        current = queue[_index]
        _data = current.asByteArray()
        _position = current.position
        _limit = current.limit
    }

    override fun rewind() {
        _index = 0
        loadIndexToCurrent()
    }

    override fun flip() {
        (_index+1 until queue.size).forEach { queue.removeAt(it) }
        current.flip()
        rewind()
    }

    override fun reset() {
        queue.forEach { it.reset() }
        rewind()
    }

    override fun resetWithErase() {
        queue.clear()
        queue.add(DataBuffer(bufSize))
        rewind()
    }

    override fun <E> withinReadLimit(length: Int, action: () -> E): E {
        if(_limit - _position < length) when(_index < queue.lastIndex) {
            true -> trySwap()
            else -> require(false) { "Buffer overflow, limit reached." }
        }
        val out = action()
        _position += length
        return out
    }

    override fun <E> withinWriteLimit(length: Int, action: () -> E) {
        if(_limit - _position < length) when (_index < queue.lastIndex) {
            true -> trySwap()
            false -> tryAppend()
        }
        action()
        _position += length
    }

    private fun trySwap() {
        require(queue.size < bufMax) { "Buffer overflow, reached final limit." }
        _index++
        loadIndexToCurrent()
    }

    private fun tryAppend() {
        require(queue.size < bufMax) { "Buffer overflow, maxed out." }
        current.limit = _position
        queue.add(DataBuffer(bufSize))
        _index++
        loadIndexToCurrent()
    }
}