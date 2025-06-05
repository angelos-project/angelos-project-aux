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
package org.angproj.aux.res

import org.angproj.aux.io.DataSize
import org.angproj.aux.util.*

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public expect class Memory: Copyable, Cleanable {
    public val size: Int
    public val ptr: Long
    override val limit: Int

    override fun getLong(index: Int): Long
    override fun getByte(index: Int): Byte
    override fun setLong(index: Int, value: Long)
    override fun setByte(index: Int, value: Byte)
    override fun dispose()
}

public expect fun allocateMemory(size: Int): Memory

internal fun validateAskedMemorySize(size: Int) = require(size in 1..DataSize._1G.size) {
    "Tried to allocate an illegal amount ($size) of memory" }

@PublishedApi
internal inline fun <reified T: Any> Memory.copyInto(
    destination: Memory, destinationOffset: Int, idxFrom: Int, idxTo: Int
): Int = object : Copy {
    operator fun invoke(): Int {
        require(idxFrom, idxTo, destinationOffset,this@copyInto, destination)
        //return speedMemCpy(idxFrom, idxTo, destinationOffset, this@copyInto.ptr, destination.ptr)
        return innerCopy(idxFrom, idxTo, destinationOffset,this@copyInto, destination)
    }
}()

@PublishedApi
internal expect fun speedMemCpy(idxFrom: Int, idxTo: Int, dstOff: Int, src: Long, dst: Long): Int