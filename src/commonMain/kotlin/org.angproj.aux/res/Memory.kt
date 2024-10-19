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
import org.angproj.aux.util.Reifiable
import org.angproj.aux.util.Speed

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public expect class Memory: Speed, Cleanable {
    public val size: Int
    public val ptr: Long
}

public expect fun allocateMemory(size: Int): Memory
@PublishedApi
internal expect inline fun <reified T: Reifiable> Memory.speedLongGet(index: Long): Long
@PublishedApi
internal expect inline fun <reified T: Reifiable> Memory.speedLongSet(index: Long, value: Long)
@PublishedApi
internal expect inline fun <reified T: Reifiable> Memory.speedByteGet(index: Long): Byte
@PublishedApi
internal expect inline fun <reified T: Reifiable> Memory.speedByteSet(index: Long, value: Byte)

internal fun validateAskedMemorySize(size: Int) = require(size in 1..DataSize._1G.size) {
    "Tried to allocate an illegal amount ($size) of memory" }

@PublishedApi
internal inline fun <reified T: Reifiable> Memory.copyInto(
    destination: Memory, destinationOffset: Int, idxFrom: Int, idxTo: Int
): Int = secure(idxFrom, idxTo, destinationOffset, this, destination)
/*{
    val length = idxTo - idxFrom
    require(idxFrom <= idxTo) {
        "Start index ($idxFrom) is larger than end index ($idxTo)" }
    require(length >= 0) {
        "Length ($length) can not be negative" }
    require(idxFrom in 0..<size) {
        "Start index ($idxFrom) not in memory range" }
    require(idxFrom + length in 0..size) {
        "End index (${idxFrom + length}) outside of memory range" }
    require(destinationOffset in 0..<destination.size) {
        "Destination offset ($destinationOffset) not in memory range" }
    require(destinationOffset + length in 0..destination.size) {
        "End index (${destinationOffset + length}) outside of memory range" }

    val index = chunkLoop<Reify>(0, length, TypeSize.long) {
        destination.speedLongSet<Reify>(
            (destinationOffset + it).toLong(),
            speedLongGet<Reify>((idxFrom + it).toLong())
        )
    }
    chunkLoop<Reify>(index, length, TypeSize.byte) {
        destination.speedByteSet<Reify>(
            (destinationOffset + it).toLong(),
            speedByteGet<Reify>((idxFrom + it).toLong())
        )
    }
}*/