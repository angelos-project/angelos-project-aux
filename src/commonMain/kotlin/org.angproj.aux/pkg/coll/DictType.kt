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
import org.angproj.aux.io.Text
import org.angproj.aux.pkg.*
import org.angproj.aux.pkg.arb.StringType
import org.angproj.aux.pkg.arb.StructType
import kotlin.jvm.JvmInline


@JvmInline
public value class DictType<P: Packageable>(public val value: Map<Text, P>) : Enfoldable {

    override fun foldSize(foldFormat: FoldFormat): Int =
        if (value.isEmpty()) Enfoldable.OVERHEAD_COUNT
        else {
            var length = 0
            val contentFoldFormat = value.getValue(value.keys.first()).foldFormat()

            when (contentFoldFormat) {
                FoldFormat.BLOCK -> value.forEach {
                    length += StringType(it.key).foldSize(FoldFormat.STREAM)
                    length += StructType(it.value).foldSize(FoldFormat.STREAM)
                }
                FoldFormat.STREAM -> value.forEach {
                    length += StringType(it.key).foldSize(FoldFormat.STREAM)
                    length += ObjectType(it.value).foldSize(FoldFormat.STREAM)
                }
            }
            length + Enfoldable.OVERHEAD_COUNT + Enfoldable.CONTENT_SIZE
        }

    public fun enfoldToStream(outStream: BinaryWritable): Int {
        Enfoldable.setType(outStream, conventionType)
        Enfoldable.setCount(outStream, value.size)
        var length = 0

        if (value.isNotEmpty()) {
            val foldFormat = value.getValue(value.keys.first()).foldFormat()
            Enfoldable.setContent(outStream, foldFormat.format)
            length += Enfoldable.CONTENT_SIZE // Conditionally including CONTENT_SIZE

            when (foldFormat) {
                FoldFormat.BLOCK -> value.forEach {
                    length += StringType(it.key).enfoldToStream(outStream)
                    length += StructType(it.value).enfoldToStream(outStream)
                }
                FoldFormat.STREAM -> value.forEach {
                    length += StringType(it.key).enfoldToStream(outStream)
                    length += ObjectType(it.value).enfoldToStream(outStream)
                }
            }
        }
        Enfoldable.setEnd(outStream, conventionType)
        // Using OVERHEAD_COUNT instead of OVERHEAD_CONTENT because CONTENT_SIZE is included in length.
        return length + Enfoldable.OVERHEAD_COUNT
    }

    public companion object : Unfoldable<DictType<Packageable>> {
        override val foldFormatSupport: List<FoldFormat> = listOf(FoldFormat.STREAM)
        override val conventionType: Convention = Convention.DICT
        override val atomicSize: Int = 0

        public fun <P : Packageable> unfoldFromStream(
            inStream: BinaryReadable, unpack: () -> P
        ): DictType<P> {
            require(Unfoldable.getType(inStream, conventionType))
            val count = Unfoldable.getCount(inStream)
            val value = mutableMapOf<Text, P>()
            if (count > 0) {
                // Expecting content type indicator if at least one key-value pair
                val foldFormat = Unfoldable.getContent(inStream)
                when (foldFormat) {
                    FoldFormat.BLOCK.format -> repeat(count) {
                        value.put(
                            StringType.unfoldFromStream(inStream).value,
                            StructType.unfoldFromStream(inStream) { unpack() }.value
                        )
                    }
                    FoldFormat.STREAM.format -> repeat(count) {
                        value.put(
                            StringType.unfoldFromStream(inStream).value,
                            ObjectType.unfoldFromStream(inStream) { unpack() }.value
                        )
                    }
                }
            }
            return DictType(value.toMap())
        }
    }
}