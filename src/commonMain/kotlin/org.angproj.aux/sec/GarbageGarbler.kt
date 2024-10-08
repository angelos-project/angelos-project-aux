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
import org.angproj.aux.rand.AbstractSponge1024
import org.angproj.aux.util.DataBuffer
import org.angproj.aux.util.NullObject
import kotlin.time.Duration
import kotlin.time.TimeSource

public class GarbageGarbler(
    public val maxSize: DataSize = DataSize._1G,
    public val maxTime: Duration = Duration.parse("1 min"),
    public val maxQueue: Int = 8
): AbstractSponge1024(), OldReader, OldWriter {

    private val inQueue: ArrayDeque<ByteArray> = ArrayDeque(maxQueue)
    private var inBuffer = DataBuffer(NullObject.byteArray)

    private var dataCount = 0
    private var timeCount = TimeSource.Monotonic.markNow() + maxTime

    init {
        revitalize()
    }

    private fun revitalize() {
        if(inBuffer.remaining < DataSize._1K.size) {
            if(inQueue.isEmpty()) {
                val entropy = ByteArray(DataSize._1K.size)
                //InitializationVector.realTimeGatedEntropy(entropy)
                inQueue.addLast(entropy)
            }
            inBuffer = DataBuffer(inQueue.removeFirst())
        }
        (0 until visibleSize).forEach { absorb(inBuffer.readLong(), it) }
        scramble()
    }

    private fun cycle() {
        if((dataCount >= maxSize.size) || timeCount.hasPassedNow()) {
            revitalize()
            dataCount = 0
            timeCount = TimeSource.Monotonic.markNow() + maxTime
        }
        round()
    }

    private fun require(length: Int) {
        require(length.mod(DataSize._1K.size) == 0) { "Garble must be divisible by the length of the inner sponge." }
    }

    override fun read(length: Int): ByteArray {
        require(length)
        return ByteArray(length).also {
            /*fill(it) {
                dataCount += dataSize.size
                cycle()
            }*/
        }
    }

    override fun read(data: ByteArray): Int {
        require(data.size)
        /*fill(data) {
            dataCount += dataSize.size
            cycle()
        }*/
        return data.size
    }

    override fun write(data: ByteArray): Int {
        require(data.size)
        if(inQueue.size >= maxQueue) {
            inBuffer = DataBuffer(inQueue.removeFirst())
        }
        inQueue.addLast(data)
        return data.size
    }
}