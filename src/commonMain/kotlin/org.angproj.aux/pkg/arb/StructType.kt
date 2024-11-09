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

    public override fun enfoldBlock(outData: Storable, offset: Int): Int = value.enfold(outData, offset)

    public override fun enfoldStream(outStream: BinaryWritable): Int {
        val block = BlockType(binOf(foldSize(FoldFormat.BLOCK)))
        enfoldBlock(block, 0)
        return block.enfoldToStreamByConvention(outStream, conventionType)
    }

    public companion object : Unfoldable<StructType<Packageable>> {
        override val foldFormatSupport: List<FoldFormat> = listOf(FoldFormat.BLOCK, FoldFormat.STREAM)
        override val conventionType: Convention = Convention.STRUCT
        override val atomicSize: Int = 0

        public fun <P: Packageable> unfoldFromBlock(
            inData: Retrievable, s: P
        ): Int = s.unfold(inData, 0)

        public fun <P: Packageable> unfoldFromBlock(
            inData: Retrievable, offset: Int, s: P
        ): Int = s.unfold(inData, offset)

        // FIX or do away?
        public fun <P: Packageable> unfoldBlock(
            inData: Retrievable, unpack: () -> P
        ): StructType<P> = unfoldBlock(inData, 0, unpack)

        public fun <P: Packageable> unfoldBlock(
            inData: Retrievable, offset: Int, unpack: () -> P
        ): StructType<P> = StructType(unpack().also { s -> s.unfold(inData, offset) })

        public fun <P: Packageable> unfoldStream(
            inStream: BinaryReadable, unpack: () -> P
        ): StructType<P> {
            val block = BlockType.unfoldFromStreamByConvention(inStream, conventionType)
            return StructType(unpack().also { s -> s.unfold(block) })
        }
    }
}