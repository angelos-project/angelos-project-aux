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
public value class FloatArrayType(public val value: FloatArray) : EnfoldableCollection {

    override fun foldSize(foldFormat: FoldFormat): Long  = when(foldFormat) {
        FoldFormat.BLOCK -> (Float.SIZE_BYTES * value.size).toLong()
        FoldFormat.STREAM -> (Float.SIZE_BYTES * value.size
                ).toLong() + Enfoldable.TYPE_SIZE + Enfoldable.COUNT_SIZE
        else -> error("Specify size for valid type.")
    }

    override fun enfold(outData: Storable, offset: Int): Long {
        value.indices.forEach {
            outData.storeFloat((it * Double.SIZE_BYTES), value[it]) }
        return (Double.SIZE_BYTES * value.size).toLong()
    }

    override fun enfold(outStream: Writable): Long {
        Enfoldable.setType(outStream, Convention.ARRAY_FLOAT)
        Enfoldable.setCount(outStream, value.size)
        value.forEach { outStream.writeFloat(it) }
        return Enfoldable.TYPE_SIZE + Enfoldable.COUNT_SIZE + Float.SIZE_BYTES * value.size
    }

    public companion object : UnfoldableCollection<EnfoldableCollection> {

        override val foldFormat: FoldFormat = FoldFormat.BOTH

        override fun unfold(inData: Retrievable, offset: Int, count: Int): FloatArrayType {
            val data = FloatArray(count) { inData.retrieveFloat(offset + it * count) }
            return FloatArrayType(data)
        }

        override fun unfold(inStream: Readable): FloatArrayType {
            require(Unfoldable.getType(inStream, Convention.ARRAY_FLOAT))
            val count = Unfoldable.getCount(inStream)
            val data = FloatArray(count) { inStream.readFloat() }
            return FloatArrayType(data)
        }
    }
}