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
import org.angproj.aux.io.Writable
import org.angproj.aux.pkg.*
import org.angproj.aux.pkg.type.BlockType
import kotlin.jvm.JvmInline

@JvmInline
public value class StringType(public val value: ByteArray) : Enfoldable {

    public constructor(text: String) : this(text.encodeToByteArray())

    override fun toString(): String = value.decodeToString()

    override val foldFormat: FoldFormat
        get() = TODO("Not yet implemented")

    override fun foldSize(foldFormat: FoldFormat): Long = when(foldFormat) {
        FoldFormat.BLOCK -> error("Unsupported fold format.")
        FoldFormat.STREAM -> value.size + Enfoldable.OVERHEAD_LENGTH
    }

    public override fun enfold(outStream: Writable): Long {
        val block = BlockType(value)
        return block.enfold(outStream, conventionType)
    }

    public companion object : Unfoldable<StringType> {
        override val foldFormatSupport: List<FoldFormat> = listOf(FoldFormat.STREAM)
        override val conventionType: Convention = Convention.STRING

        public override fun unfold(inStream: Readable): StringType {
            val block = BlockType.unfold(inStream, conventionType)
            return StringType(block.block)
        }
    }
}