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
package org.angproj.aux.pkg.arb

import org.angproj.aux.io.Readable
import org.angproj.aux.io.Retrievable
import org.angproj.aux.io.Storable
import org.angproj.aux.io.Writable
import org.angproj.aux.pkg.*
import org.angproj.aux.pkg.type.BlockType
import kotlin.jvm.JvmInline

@JvmInline
public value class StructType<P: BlockPackageable>(public val value: P) : Enfoldable {
    override val foldFormat: FoldFormat
        get() = TODO("Not yet implemented")

    override fun foldSize(foldFormat: FoldFormat): Long {
        val size = value.foldSize(foldFormat)
        return when(foldFormat) {
            FoldFormat.BLOCK -> size
            FoldFormat.STREAM -> size + Enfoldable.OVERHEAD_LENGTH
        }
    }

    public override fun enfold(outData: Storable, offset: Int): Long {
        value.enfold(outData, offset)
        return foldSize(FoldFormat.BLOCK)
    }

    public override fun enfold(outStream: Writable): Long {
        val block = BlockType(foldSize(FoldFormat.BLOCK).toInt())
        enfold(block, 0)
        return block.enfold(outStream, conventionType)
    }

    public companion object : Unfoldable<StructType<BlockPackageable>> {
        override val foldFormatSupport: List<FoldFormat> = listOf(FoldFormat.BLOCK, FoldFormat.STREAM)
        override val conventionType: Convention = Convention.STRUCT

        public fun unfold(
            inData: Retrievable, offset: Int, unpack: (Retrievable, Int) -> BlockPackageable
        ) : StructType<BlockPackageable> = StructType(unpack(inData, offset))

        public fun unfold(inStream: Readable, unpack: (Retrievable, Int) -> BlockPackageable
        ) : StructType<BlockPackageable> {
            val block = BlockType.unfold(inStream, conventionType)
            return StructType(unpack(block, 0))
        }
    }
}