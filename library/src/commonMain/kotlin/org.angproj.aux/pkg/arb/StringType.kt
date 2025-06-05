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

import org.angproj.aux.buf.copyInto
import org.angproj.aux.io.*
import org.angproj.aux.mem.BufMgr
import org.angproj.aux.pkg.*
import org.angproj.aux.pkg.type.BlockType
import kotlin.jvm.JvmInline

@JvmInline
public value class StringType(public val value: Text) : Enfoldable {

    override fun foldSize(foldFormat: FoldFormat): Int = when (foldFormat) {
        FoldFormat.BLOCK -> error("Unsupported fold format.")
        FoldFormat.STREAM -> value.limit + Enfoldable.OVERHEAD_LENGTH
    }

    public override fun enfoldStream(outStream: BinaryWritable): Int {
        require(!value.isNull()) { "Null Text" }
        val block = BlockType(value.asBinary())
        return block.enfoldToStreamByConvention(outStream, conventionType)
    }

    public companion object : Unfoldable<StringType> {
        override val foldFormatSupport: List<FoldFormat> = listOf(FoldFormat.STREAM)
        override val conventionType: Convention = Convention.STRING
        override val atomicSize: Int = 0

        public override fun unfoldStream(inStream: BinaryReadable): StringType {
            val block = BlockType.unfoldFromStreamByConvention(inStream, conventionType)
            val txt = BufMgr.txt(block.block.limit).apply {
                block.block.copyInto(this, 0, 0, limit) }
            return StringType(txt)
        }
    }
}