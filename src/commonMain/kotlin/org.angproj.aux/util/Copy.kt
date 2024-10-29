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

import org.angproj.aux.io.TypeSize


public interface Copyable {
    public val limit: Int

    public fun getLong(index: Int): Long
    public fun getByte(index: Int): Byte

    public fun setLong(index: Int, value: Long): Unit
    public fun setByte(index: Int, value: Byte): Unit
}

public interface Copy {

    private inline fun <reified S: Copyable> S.loop(slice: IntRange, pos: Int, action: (Int, Int) -> Unit): Int {
        val offset = slice.first
        val steps = (slice.last - slice.first) / TypeSize.long
        val size = steps * TypeSize.long
        if (steps > 0) (0 until size step TypeSize.long).forEach { action(offset + it, pos + it) }
        return size
    }

    private inline fun <reified S: Copyable, D: Copyable> S.loop(slice: IntRange, pos: Int, dst: D): Int {
        val offset = slice.first
        val steps = (slice.last - slice.first) / TypeSize.long
        val size = steps * TypeSize.long
        if (steps > 0) (0 until size step TypeSize.long).forEach {
            dst.setLong(pos + it, getLong(offset + it))
        }
        return size
    }

    private inline fun <reified S: Copyable> S.chunk(slice: IntRange, pos: Int, size: Int, action: (Int, Int) -> Unit): Int {
        val offset = slice.first
        val steps = (slice.last - slice.first) - size
        if (steps > 0) (size until size + steps).forEach { action(offset + it, pos + it) }
        return size + steps
    }

    private inline fun <reified S: Copyable, D: Copyable> S.chunk(slice: IntRange, pos: Int, size: Int, dst: D): Int {
        val offset = slice.first
        val steps = (slice.last - slice.first) - size
        if (steps > 0) (size until size + steps).forEach {
            dst.setByte(pos + it, getByte(offset + it))
        }
        return size + steps
    }

    public fun require(idxFrom: Int, idxTo: Int, dstOff: Int, src: Copyable, dst: Copyable) {
        val length = idxTo - idxFrom
        require(idxFrom <= idxTo) {
            "Start index ($idxFrom) is larger than end index ($idxTo)" }
        require(length >= 0) {
            "Length ($length) can not be negative" }
        require(idxFrom in 0..<src.limit) {
            "Start index ($idxFrom) not in memory range" }
        require(idxFrom + length in 0..src.limit) {
            "End index (${idxFrom + length}) outside of memory range" }
        require(dstOff in 0..<dst.limit) {
            "Destination offset ($dstOff) not in memory range" }
        require(dstOff + length in 0..dst.limit) {
            "End index (${dstOff + length}) outside of memory range" }
    }

    public fun innerCopy(
        idxFrom: Int, idxTo: Int, dstOff: Int, src: Copyable, dst: Copyable
    ): Int {
        val range = idxFrom..idxTo
        return src.chunk(range, dstOff, src.loop(range, dstOff, dst), dst)
    }

    public operator fun<E: Any> invoke(action: () -> E): E = action()
}
