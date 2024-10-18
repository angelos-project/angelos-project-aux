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
package org.angproj.aux.pkg.coll

import org.angproj.aux.io.BinaryReadable
import org.angproj.aux.io.BinaryWritable
import org.angproj.aux.pkg.*
import org.angproj.aux.pkg.arb.StructType
import kotlin.jvm.JvmInline

@JvmInline
public value class ListType<P : Packageable>(public val value: List<P>) : Enfoldable {

    override fun foldSize(foldFormat: FoldFormat): Long =
        if (value.isEmpty()) Enfoldable.OVERHEAD_COUNT
        else with(value.first()) {
            var length: Long = 0
            when (foldFormat) {
                FoldFormat.BLOCK -> value.forEach { length += StructType(it).foldSize(FoldFormat.STREAM) }
                FoldFormat.STREAM -> value.forEach { length += ObjectType(it).foldSize(FoldFormat.STREAM) }
            }
            length + Enfoldable.OVERHEAD_CONTENT
        }

    public fun enfoldToStream(outStream: BinaryWritable): Long {
        Enfoldable.setType(outStream, conventionType)
        Enfoldable.setCount(outStream, value.size)
        var length = 0L

        if (value.isNotEmpty()) with(value.first()) {
            val foldFormat = foldFormat()
            Enfoldable.setContent(outStream, foldFormat.format)
            length += Enfoldable.CONTENT_SIZE // Conditionally including CONTENT_SIZE

            when (foldFormat) {
                FoldFormat.BLOCK -> value.forEach { length += StructType(it).enfoldToStream(outStream) }
                FoldFormat.STREAM -> value.forEach { length += ObjectType(it).enfoldToStream(outStream) }
            }
        }
        Enfoldable.setEnd(outStream, conventionType)
        // Using OVERHEAD_COUNT instead of OVERHEAD_CONTENT because CONTENT_SIZE is included in length.
        return Enfoldable.OVERHEAD_COUNT + length
    }

    public companion object : Unfoldable<ListType<Packageable>> {
        override val foldFormatSupport: List<FoldFormat> = listOf(FoldFormat.STREAM)
        override val conventionType: Convention = Convention.LIST
        public val contentFormat: List<FoldFormat> = listOf(FoldFormat.STREAM, FoldFormat.BLOCK)
        override val atomicSize: Int = 0

        public fun <P : Packageable> unfoldFromStream(
            inStream: BinaryReadable, unpack: () -> P
        ): ListType<P> {
            require(Unfoldable.getType(inStream, conventionType))
            val count = Unfoldable.getCount(inStream)
            val value = mutableListOf<P>()
            if (count > 0) {
                val foldFormat = Unfoldable.getContent(inStream)
                when (foldFormat) {
                    FoldFormat.BLOCK.format -> repeat(count) { value.add(StructType.unfoldFromStream(inStream) { unpack() }.value) }
                    FoldFormat.STREAM.format -> repeat(count) { value.add(ObjectType.unfoldFromStream(inStream) { unpack() }.value) }
                }
            }
            return ListType(value.toList())
        }
    }
}