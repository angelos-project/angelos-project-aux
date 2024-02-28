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
import org.angproj.aux.pkg.*
import kotlin.jvm.JvmInline

@JvmInline
public value class IntArrayType(public val value: IntArray) : EnfoldableCollection {

    override fun foldSize(foldFormat: FoldFormat): Long  = when(foldFormat) {
        FoldFormat.BLOCK -> (Int.SIZE_BYTES * value.size).toLong()
        FoldFormat.STREAM -> (Int.SIZE_BYTES * value.size
                ).toLong() + Enfoldable.TYPE_SIZE + Enfoldable.COUNT_SIZE
        else -> error("Specify size for valid type.")
    }

    override fun enfold(outData: Storable, offset: Int): Long {
        value.indices.forEach {
            outData.storeInt((it * Int.SIZE_BYTES), value[it]) }
        return (Int.SIZE_BYTES * value.size).toLong()
    }

    override fun enfold(outStream: Writable): Long {
        Enfoldable.setType(outStream, Convention.ARRAY_INT)
        Enfoldable.setCount(outStream, value.size)
        value.forEach { outStream.writeInt(it) }
        return Enfoldable.TYPE_SIZE + Enfoldable.COUNT_SIZE + Int.SIZE_BYTES * value.size
    }

    public companion object : UnfoldableCollection<EnfoldableCollection> {

        override val foldFormat: FoldFormat = FoldFormat.BOTH

        override fun unfold(inData: Retrievable, offset: Int, count: Int): IntArrayType {
            val data = IntArray(count) { inData.retrieveInt(offset + it * count) }
            return IntArrayType(data)
        }

        override fun unfold(inStream: Readable): IntArrayType {
            require(Unfoldable.getType(inStream, Convention.ARRAY_INT))
            val count = Unfoldable.getCount(inStream)
            val data = IntArray(count) { inStream.readInt() }
            return IntArrayType(data)
        }
    }
}