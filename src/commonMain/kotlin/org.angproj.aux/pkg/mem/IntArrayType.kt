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
public value class IntArrayType(public val value: IntArray) : Enfoldable {

    override val foldFormat: FoldFormat
        get() = TODO("Not yet implemented")

    override fun foldSize(foldFormat: FoldFormat): Long = when (foldFormat) {
        FoldFormat.BLOCK -> (Int.SIZE_BYTES * value.size).toLong()
        FoldFormat.STREAM -> (Int.SIZE_BYTES * value.size).toLong() + Enfoldable.OVERHEAD_COUNT
    }

    override fun enfold(outData: Storable, offset: Int): Long {
        value.indices.forEach {
            outData.storeInt((it * Int.SIZE_BYTES), value[it])
        }
        return foldSize(FoldFormat.BLOCK)
    }

    override fun enfold(outStream: Writable): Long {
        Enfoldable.setType(outStream, conventionType)
        Enfoldable.setCount(outStream, value.size)
        value.forEach { outStream.writeInt(it) }
        Enfoldable.setEnd(outStream, conventionType)
        return foldSize(FoldFormat.STREAM)
    }

    public companion object : Unfoldable<IntArrayType> {
        override val foldFormatSupport: List<FoldFormat> = listOf(FoldFormat.BLOCK, FoldFormat.STREAM)
        override val conventionType: Convention = Convention.ARRAY_INT

        override fun unfold(inData: Retrievable, offset: Int, count: Int): IntArrayType {
            val data = IntArray(count) { inData.retrieveInt(offset + it * count) }
            return IntArrayType(data)
        }

        override fun unfold(inStream: Readable): IntArrayType {
            require(Unfoldable.getType(inStream, conventionType))
            val count = Unfoldable.getCount(inStream)
            val data = IntArray(count) { inStream.readInt() }
            require(Unfoldable.getEnd(inStream, conventionType))
            return IntArrayType(data)
        }
    }
}