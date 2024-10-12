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

import org.angproj.aux.io.*
import org.angproj.aux.pkg.Convention
import org.angproj.aux.pkg.Enfoldable
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.Unfoldable
import org.angproj.aux.pkg.type.BlockType
import kotlin.jvm.JvmInline

@JvmInline
public value class StringType(public val value: Text) : Enfoldable {

    override val foldFormat: FoldFormat
        get() = TODO("Not yet implemented")

    override fun foldSize(foldFormat: FoldFormat): Long = when (foldFormat) {
        FoldFormat.BLOCK -> error("Unsupported fold format.")
        FoldFormat.STREAM -> value.limit + Enfoldable.OVERHEAD_LENGTH
    }

    public fun enfoldToStream(outStream: Writable): Long {
        val block = BlockType(value.asBinary())
        return block.enfoldToStreamByConvention(outStream, conventionType)
    }

    public companion object : Unfoldable<StringType> {
        override val foldFormatSupport: List<FoldFormat> = listOf(FoldFormat.STREAM)
        override val conventionType: Convention = Convention.STRING
        override val atomicSize: Int = 0

        public fun unfoldFromStream(inStream: Readable): StringType {
            val block = BlockType.unfoldFromStreamByConvention(inStream, conventionType)
            return StringType(Text(block.block.segment))
        }
    }
}