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
package org.angproj.aux.pkg.mem

import org.angproj.aux.io.Readable
import org.angproj.aux.io.Retrievable
import org.angproj.aux.io.Storable
import org.angproj.aux.io.Writable
import org.angproj.aux.pkg.Convention
import org.angproj.aux.pkg.Enfoldable
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.Unfoldable
import kotlin.jvm.JvmInline

@JvmInline
public value class LongArrayType(public val value: LongArray) : Enfoldable {

    override val foldFormat: FoldFormat
        get() = TODO("Not yet implemented")

    override fun foldSize(foldFormat: FoldFormat): Long = when (foldFormat) {
        FoldFormat.BLOCK -> (atomicSize * value.size).toLong()
        FoldFormat.STREAM -> (atomicSize * value.size).toLong() + Enfoldable.OVERHEAD_COUNT
    }

    public fun enfoldToBlock(outData: Storable, offset: Int = 0): Long {
        value.indices.forEach {
            outData.storeLong((offset + it * atomicSize), value[it])
        }
        return foldSize(FoldFormat.BLOCK)
    }

    public fun enfoldToStream(outStream: Writable): Long {
        Enfoldable.setType(outStream, conventionType)
        Enfoldable.setCount(outStream, value.size)
        value.forEach { outStream.writeLong(it) }
        Enfoldable.setEnd(outStream, conventionType)
        return foldSize(FoldFormat.STREAM)
    }

    public companion object : Unfoldable<LongArrayType> {
        override val foldFormatSupport: List<FoldFormat> = listOf(FoldFormat.BLOCK, FoldFormat.STREAM)
        override val conventionType: Convention = Convention.ARRAY_LONG
        override val atomicSize: Int = Long.SIZE_BYTES

        public fun unfoldFromBlock(inData: Retrievable, count: Int): LongArrayType = unfoldFromBlock(inData, 0, count)

        public fun unfoldFromBlock(inData: Retrievable, offset: Int, count: Int): LongArrayType {
            val data = LongArray(count) { inData.retrieveLong(offset + it * atomicSize) }
            return LongArrayType(data)
        }

        public fun unfoldFromStream(inStream: Readable): LongArrayType {
            require(Unfoldable.getType(inStream, conventionType))
            val count = Unfoldable.getCount(inStream)
            val data = LongArray(count) { inStream.readLong() }
            require(Unfoldable.getEnd(inStream, conventionType))
            return LongArrayType(data)
        }
    }
}