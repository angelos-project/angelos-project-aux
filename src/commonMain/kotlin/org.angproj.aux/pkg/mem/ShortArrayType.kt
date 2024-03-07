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
public value class ShortArrayType(public val value: ShortArray) : Enfoldable {

    override val foldFormat: FoldFormat
        get() = TODO("Not yet implemented")

    override fun foldSize(foldFormat: FoldFormat): Long = when (foldFormat) {
        FoldFormat.BLOCK -> (Short.SIZE_BYTES * value.size).toLong()
        FoldFormat.STREAM -> (Short.SIZE_BYTES * value.size).toLong() + Enfoldable.OVERHEAD_COUNT
    }

    override fun enfold(outData: Storable, offset: Int): Long {
        value.indices.forEach {
            outData.storeShort((it * Short.SIZE_BYTES), value[it])
        }
        return foldSize(FoldFormat.BLOCK)
    }

    override fun enfold(outStream: Writable): Long {
        Enfoldable.setType(outStream, conventionType)
        Enfoldable.setCount(outStream, value.size)
        value.forEach { outStream.writeShort(it) }
        Enfoldable.setEnd(outStream, conventionType)
        return foldSize(FoldFormat.STREAM)
    }

    public companion object : Unfoldable<ShortArrayType> {
        override val foldFormatSupport: List<FoldFormat> = listOf(FoldFormat.BLOCK, FoldFormat.STREAM)
        override val conventionType: Convention = Convention.ARRAY_SHORT

        override fun unfold(inData: Retrievable, offset: Int, count: Int): ShortArrayType {
            val data = ShortArray(count) { inData.retrieveShort(offset + it * count) }
            return ShortArrayType(data)
        }

        override fun unfold(inStream: Readable): ShortArrayType {
            require(Unfoldable.getType(inStream, conventionType))
            val count = Unfoldable.getCount(inStream)
            val data = ShortArray(count) { inStream.readShort() }
            require(Unfoldable.getEnd(inStream, conventionType))
            return ShortArrayType(data)
        }
    }
}