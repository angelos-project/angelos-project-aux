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
import org.angproj.aux.pkg.*
import org.angproj.aux.pkg.type.BlockType
import kotlin.jvm.JvmInline

@JvmInline
public value class StructType<P: Packageable>(public val value: P) : Enfoldable {

    /**
     * StructType will always first package a Packageable as a block and then use stream by convention.
     * Therefore, is the STREAM call to the Packageable unnecessary in this class.
     * Only if the Packageable is directly streamed by itself its foldSize(STREAM) is needed.
     * */
    override fun foldSize(foldFormat: FoldFormat): Int = value.foldSize(
        FoldFormat.BLOCK) + if(foldFormat == FoldFormat.STREAM) Enfoldable.OVERHEAD_LENGTH else 0

    public fun enfoldToBlock(outData: Storable, offset: Int = 0): Int = value.enfold(outData, offset)

    public fun enfoldToStream(outStream: BinaryWritable): Int {
        val block = BlockType(binOf(foldSize(FoldFormat.BLOCK)))
        enfoldToBlock(block, 0)
        return block.enfoldToStreamByConvention(outStream, conventionType)
    }

    public companion object : Unfoldable<StructType<Packageable>> {
        override val foldFormatSupport: List<FoldFormat> = listOf(FoldFormat.BLOCK, FoldFormat.STREAM)
        override val conventionType: Convention = Convention.STRUCT
        override val atomicSize: Int = 0

        public fun <P: Packageable, S: StructType<P>> unfoldFromBlock(
            inData: Retrievable, unpack: () -> P
        ): S = unfoldFromBlock(inData, 0, unpack)

        @Suppress("UNCHECKED_CAST")
        public fun <P: Packageable, S: StructType<P>> unfoldFromBlock(
            inData: Retrievable, offset: Int, unpack: () -> P
        ): S = StructType(unpack().also { s -> s.unfold(inData, offset) }) as S

        @Suppress("UNCHECKED_CAST")
        public fun <P: Packageable, S: StructType<P>> unfoldFromStream(
            inStream: BinaryReadable, unpack: () -> P
        ): S {
            val block = BlockType.unfoldFromStreamByConvention(inStream, conventionType)
            val strct = StructType(unpack().also { s -> s.unfold(block) })
            return strct as S
        }
    }
}