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

public class DataBuffer(data: ByteArray) : AbstractBuffer() {

    public constructor(size: Int) : this(ByteArray(size))

    public constructor(size: BufferSize = BufferSize._4K) : this(size.size)

    override val _data: ByteArray = data
    public fun asByteArray(): ByteArray = _data

    override val size: Int
        get() = _data.size

    override var _position: Int = 0
    public override val position: Int
        get() = _position

    override var _limit: Int = _data.size
    public override var limit: Int
        get() = _limit
        set(value) {
            require(value in _data.indices) { "Limit not within boundaries." }
            check(_limit == _data.size) { "Limit already set." }
            _limit = value
        }

    public override val remaining: Int
        get() = _limit - _position

    public override fun rewind() {
        _position = 0
    }

    public override fun flip() {
        _limit = _position
        rewind()
    }

    public override fun resetWithErase() {
        _data.fill(0)
        reset()
    }

    public override fun reset() {
        _limit = _data.size
        rewind()
    }

    override fun <E> withinReadLimit(length: Int, action: () -> E): E {
        require(remaining >= length) { "Buffer overflow, limit reached." }
        val out = action()
        _position += length
        return out
    }

    override fun <E> withinWriteLimit(length: Int, action: () -> E) {
        require(remaining >= length) { "Buffer overflow, limit reached." }
        action()
        _position += length
    }
}