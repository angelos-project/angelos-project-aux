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
import org.angproj.aux.rand.InitializationVector.IV_3AC5
import org.angproj.aux.sec.GarbageGarbler
import org.angproj.aux.util.DataBuffer
import kotlin.time.TimeSource

public class Entropy(
    private val garbler: GarbageGarbler,
    public val bufSize: DataSize = DataSize._256B
) {

    init {
        require(bufSize.size in DataSize._256B.size..DataSize._128K.size)
    }

    private var buffer = DataBuffer(bufSize)
    private val moment = TimeSource.Monotonic.markNow()
    private var entropy: Long = IV_3AC5.iv * moment.elapsedNow().inWholeNanoseconds


    public fun <E> snapTime(action: () -> E): E {
        if(buffer.remaining == 0) {
            garbler.write(buffer.asByteArray())
            buffer = DataBuffer(bufSize)
        }
        entropy = (moment.elapsedNow().inWholeNanoseconds * entropy).rotateLeft(32)
        buffer.writeByte(entropy.toByte())
        return action()
    }
}