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

import org.angproj.aux.io.Binary
import org.angproj.aux.io.DataSize
import org.angproj.aux.io.Reader
import org.angproj.aux.pipe.buildSink
import org.angproj.aux.pipe.buildSource
import org.angproj.aux.rand.InitializationVector.IV_3AC5
import org.angproj.aux.sec.GarbageGarbler
import org.angproj.aux.util.chunkLoop
import kotlin.time.TimeSource

public class Entropy(private val garbler: GarbageGarbler = GarbageGarbler()): Reader {

    private val source = buildSource { push(garbler).seg(DataSize._128B).buf(DataSize._128B).bin() }
    private val sink = buildSink { pull(garbler).seg(DataSize._1K).buf(DataSize._1K).bin() }

    private val moment = TimeSource.Monotonic.markNow()
    private var entropy: Long = IV_3AC5.iv * moment.elapsedNow().inWholeNanoseconds

    /**
     * Generates one byte ot true random by snapping the nanosecond timestamp when
     * something happened this could be a user generated event for example.
     * */
    public fun <E> snapTime(action: () -> E): E {
        entropy = (moment.elapsedNow().inWholeNanoseconds * entropy).rotateLeft(32)
        source.writeByte(entropy.toByte())
        return action()
    }

    /**
     * Allows reading up to 1GB of extraordinarily secure random data at a time based on presumably true entropy.
     * */
    override fun read(bin: Binary): Int {
        val index = chunkLoop<Unit>(0, bin.limit, Long.SIZE_BYTES) { bin.storeLong(it, sink.readLong()) }
        return chunkLoop<Unit>(index, bin.limit, Byte.SIZE_BYTES) { bin.storeByte(it, sink.readByte()) }
    }
}