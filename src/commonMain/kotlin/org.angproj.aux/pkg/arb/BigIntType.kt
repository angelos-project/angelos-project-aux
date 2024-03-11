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
import org.angproj.aux.num.BigInt
import org.angproj.aux.pkg.Convention
import org.angproj.aux.pkg.Enfoldable
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.Unfoldable
import org.angproj.aux.pkg.type.BlockType
import org.angproj.aux.util.bigIntOf
import kotlin.jvm.JvmInline

@JvmInline
public value class BigIntType(public val value: BigInt) : Enfoldable {

    override val foldFormat: FoldFormat
        get() = TODO("Not yet implemented")

    override fun foldSize(foldFormat: FoldFormat): Long = when (foldFormat) {
        FoldFormat.BLOCK -> error("Unsupported fold format.")
        FoldFormat.STREAM -> value.getByteSize() + Enfoldable.OVERHEAD_LENGTH
    }

    public fun enfoldToStream(outStream: Writable): Long {
        val block = BlockType(value.toByteArray())
        return block.enfoldToStreamByConvention(outStream, conventionType)
    }

    public companion object : Unfoldable<BigIntType> {
        override val foldFormatSupport: List<FoldFormat> = listOf(FoldFormat.STREAM)
        override val conventionType: Convention = Convention.BIGINT
        override val atomicSize: Int = 0

        public fun unfoldFromStream(inStream: Readable): BigIntType {
            val block = BlockType.unfoldFromStreamByConvention(inStream, conventionType)
            return BigIntType(bigIntOf(block.block))
        }
    }
}