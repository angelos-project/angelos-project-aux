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
package org.angproj.aux.pipe

import org.angproj.aux.io.DataSize
import org.angproj.aux.io.Segment
import org.angproj.aux.io.isNull
import org.angproj.aux.mem.MemoryManager

public abstract class AbstractPipe(
    public val segSize: DataSize,
    public val bufSize: DataSize,
    protected val memMgr: MemoryManager<*>
) {

    init {
        require(bufSize.size >= segSize.size) { "A pipe must be configured for at least one segment" }
    }

    /**
     * Segment capacity of buffer
     * */
    public val queueCap: Int = bufSize.size / segSize.size

    /**
     * Current number of segments queued in buffer
     * */
    public val queueLen: Int
        get() = buf.size

    protected val buf: ArrayDeque<Segment<*>> = ArrayDeque(queueCap)

    protected var _bufUse: Int = 0
    /**
     * Total bytes in buffer that has not been absorbed
     * */
    public val bufUse: Int
        get() {
            if(_bufUse < 0) _bufUse = buf.sumOf { it.limit }
            return _bufUse
        }

    protected var _segCnt: Long = 0
    /**
     * Total segments fully absorbed
     * */
    public val totalSegmentCount: Long
        get() = _segCnt

    public fun<reified : Any> dispose() { while(buf.isNotEmpty()) recycle<Unit>(buf.removeLast()) }

    public  fun<reified : Any> allocate(size: DataSize): Segment<*> = memMgr.allocate(size)

    public fun<reified : Any> recycle(seg: Segment<*>) { if(!seg.isNull()) seg.close() }
}