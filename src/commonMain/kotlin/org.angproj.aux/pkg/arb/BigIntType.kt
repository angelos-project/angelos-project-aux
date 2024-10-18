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

import org.angproj.aux.io.BinaryReadable
import org.angproj.aux.io.BinaryWritable
import org.angproj.aux.io.toBinary
import org.angproj.aux.io.toByteArray
import org.angproj.aux.num.BigInt
import org.angproj.aux.num.bigIntOf
import org.angproj.aux.pkg.Convention
import org.angproj.aux.pkg.Enfoldable
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.Unfoldable
import org.angproj.aux.pkg.type.BlockType
import kotlin.jvm.JvmInline

@JvmInline
public value class BigIntType(public val value: BigInt) : Enfoldable {

    override fun foldSize(foldFormat: FoldFormat): Long = when (foldFormat) {
        FoldFormat.BLOCK -> error("Unsupported fold format.")
        FoldFormat.STREAM -> value.getByteSize() + Enfoldable.OVERHEAD_LENGTH
    }

    public fun enfoldToStream(outStream: BinaryWritable): Long {
        val block = BlockType(value.toByteArray().toBinary())
        return block.enfoldToStreamByConvention(outStream, conventionType)
    }

    public companion object : Unfoldable<BigIntType> {
        override val foldFormatSupport: List<FoldFormat> = listOf(FoldFormat.STREAM)
        override val conventionType: Convention = Convention.BIGINT
        override val atomicSize: Int = 0

        public fun unfoldFromStream(inStream: BinaryReadable): BigIntType {
            val block = BlockType.unfoldFromStreamByConvention(inStream, conventionType)
            return BigIntType(bigIntOf(block.block.toByteArray()))
        }
    }
}