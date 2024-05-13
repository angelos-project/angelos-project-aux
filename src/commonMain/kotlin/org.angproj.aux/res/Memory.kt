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
import org.angproj.aux.io.TypeSize
import org.angproj.aux.util.Reifiable
import org.angproj.aux.util.Reify

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public expect class Memory: Cleanable {
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

public inline fun <reified T: Reifiable> chunkLoop(index: Int, length: Int, slice: Int, action: (Int) -> Unit): Int {
    val steps = (length - index) / slice
    val size = steps * slice
    if (steps > 0) (index until (index + size) step slice).forEach { action(it) }
    return index + size
}

@PublishedApi
internal inline fun <reified T: Reifiable> Memory.copyInto(destination: Memory, destinationOffset: Int, fromIdx: Int, toIdx: Int) {
    val length = toIdx - fromIdx
    require(fromIdx <= toIdx) {
        "Start index ($fromIdx) is larger than end index ($toIdx)" }
    require(length >= 0) {
        "Length ($length) can not be negative" }
    require(fromIdx in 0..<size) {
        "Start index ($fromIdx) not in memory range" }
    require(fromIdx + length in 0..size) {
        "End index (${fromIdx + length}) outside of memory range" }
    require(destinationOffset in 0..<destination.size) {
        "Destination offset ($destinationOffset) not in memory range" }
    require(destinationOffset + length in 0..destination.size) {
        "End index (${destinationOffset + length}) outside of memory range" }

    val index = chunkLoop<Reify>(0, length, TypeSize.long) {
        destination.speedLongSet<Reify>(
            (destinationOffset + it).toLong(),
            speedLongGet<Reify>((fromIdx + it).toLong())
        )
    }
    chunkLoop<Reify>(index, length, TypeSize.byte) {
        destination.speedByteSet<Reify>(
            (destinationOffset + it).toLong(),
            speedByteGet<Reify>((fromIdx + it).toLong())
        )
    }
}