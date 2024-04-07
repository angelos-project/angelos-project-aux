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
package org.angproj.aux.rand

import org.angproj.aux.io.DataSize
import org.angproj.aux.sec.GarbageGarbler
import org.angproj.aux.util.DataBuffer
import kotlin.time.TimeSource

public class Entropy(
    private val garbler: GarbageGarbler,
    public val bufSize: DataSize = DataSize._256B
) {

    init {
        require(bufSize.size >= DataSize._256B.size)
        require(bufSize.size <= DataSize._128K.size)
    }

    private var buffer = DataBuffer(bufSize)
    private val moment = TimeSource.Monotonic.markNow()

    public fun <E> clock(action: () -> E): E {
        if(buffer.remaining == 0) {
            garbler.write(buffer.asByteArray())
            buffer = DataBuffer(bufSize)
        }
        buffer.writeByte(moment.elapsedNow().inWholeNanoseconds.toByte())
        return action()
    }
}