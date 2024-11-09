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

import org.angproj.aux.io.*
import org.angproj.aux.pkg.*
import org.angproj.aux.pkg.arb.StructType
import org.angproj.aux.pkg.type.BlockType
import kotlin.jvm.JvmInline

@JvmInline
public value class ArrayType<P : Packageable>(public val value: Array<P>) : Enfoldable {

    override fun foldSize(foldFormat: FoldFormat): Int = when (foldFormat) {
        FoldFormat.BLOCK -> if (value.isEmpty()) 0 else Enfoldable.ITEM_SIZE + StructType(
            value.first()
        ).foldSize(FoldFormat.BLOCK) * value.size

        FoldFormat.STREAM -> if (value.isEmpty()) Enfoldable.OVERHEAD_COUNT else + StructType(
            value.first()
        ).foldSize(FoldFormat.BLOCK) * value.size + Enfoldable.OVERHEAD_COUNT + Enfoldable.ITEM_SIZE
    }

    public override fun enfoldBlock(outData: Storable, offset: Int): Int {
        var length = 0
        if (value.isNotEmpty()) {
            require(value.first().foldFormat() == FoldFormat.BLOCK) { "Unsupported fold format." }
            val itemSize = StructType(value.first()).foldSize(FoldFormat.BLOCK)
            Enfoldable.setItem(outData, offset + length, itemSize)
            length += Enfoldable.ITEM_SIZE
            value.forEach {
                StructType(it).enfoldBlock(outData, offset + length)
                length += itemSize
            }
        }
        return length
    }

    public override fun enfoldStream(outStream: BinaryWritable): Int = outStream.measureBytes {
        Enfoldable.setType(outStream, conventionType)
        Enfoldable.setCount(outStream, value.size)

        if (value.isNotEmpty()) {
            require(value.first().foldFormat() == FoldFormat.BLOCK) { "Unsupported fold format." }
            val itemSize = StructType(value.first()).foldSize(FoldFormat.BLOCK)
            Enfoldable.setItem(outStream, itemSize)
            val block = BlockType(binOf(itemSize))
            value.forEach {
                StructType(it).enfoldBlock(block, 0)
                BlockType.enfoldToStreamRaw(outStream, block.block)
            }
        }
        Enfoldable.setEnd(outStream, conventionType)
    }.toInt()

    public companion object : Unfoldable<ArrayType<Packageable>> {
        override val foldFormatSupport: List<FoldFormat> = listOf(FoldFormat.STREAM)
        override val conventionType: Convention = Convention.ARRAY
        public val contentFormat: List<FoldFormat> = listOf(FoldFormat.BLOCK)
        override val atomicSize: Int = 0

        public fun <P : Packageable> unfoldBlock(
            inData: Retrievable, array: Array<P>
        ): Int = unfoldBlock(inData, 0, array)

        public fun <P : Packageable> unfoldBlock(
            inData: Retrievable, offset: Int, array: Array<P>
        ): Int {
            var length = 0
            if (array.isNotEmpty()) {
                val itemSize = Unfoldable.getItem(inData, offset + length)
                length += Enfoldable.ITEM_SIZE
                val block = BlockType(binOf(itemSize))
                repeat(array.size) {
                    BlockType.unfoldFromBlock(inData, offset + length, block.block)
                    StructType.unfoldFromBlock(block, array[it])
                    length += itemSize
                }
            }
            return length
        }

        public fun <P : Packageable> unfoldStream(
            inStream: BinaryReadable, factory: (Int) -> Array<P>
        ): ArrayType<P> {
            require(Unfoldable.getType(inStream, conventionType))
            val count = Unfoldable.getCount(inStream)
            val value = if (count > 0) {
                val itemSize = Unfoldable.getItem(inStream)
                val block = BlockType(binOf(itemSize))
                factory(count).apply { forEach {
                    BlockType.unfoldFromStreamRaw(inStream, block.block)
                    StructType.unfoldFromBlock(block.block, it)
                } }
            } else factory(0)
            require(Unfoldable.getEnd(inStream, conventionType))
            return ArrayType(value)
        }
    }
}