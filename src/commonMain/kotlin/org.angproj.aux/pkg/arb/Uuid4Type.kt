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
import org.angproj.aux.pkg.Convention
import org.angproj.aux.pkg.Enfoldable
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.Unfoldable
import org.angproj.aux.pkg.type.BlockType
import org.angproj.aux.util.Uuid4
import org.angproj.aux.util.readLongAt
import kotlin.jvm.JvmInline

@JvmInline
public value class Uuid4Type(public val value: Uuid4) : Enfoldable {
    override val foldFormat: FoldFormat
        get() = TODO("Not yet implemented")

    override fun foldSize(foldFormat: FoldFormat): Long = when(foldFormat) {
        FoldFormat.BLOCK -> UUID4_SIZE
        FoldFormat.STREAM -> UUID4_SIZE + Enfoldable.OVERHEAD_BASIC
    }

    public fun enfoldToBlock(outData: Storable, offset: Int = 0): Long {
        val uuid = value.toByteArray()
        outData.storeLong(offset, uuid.readLongAt(0))
        outData.storeLong(offset + 8, uuid.readLongAt(8))
        return foldSize(FoldFormat.BLOCK)
    }

    public fun enfoldToStream(outStream: Writable): Long {
        val uuid = value.toByteArray()
        Enfoldable.setType(outStream, conventionType)
        outStream.writeLong(uuid.readLongAt(0))
        outStream.writeLong(uuid.readLongAt(8))
        Enfoldable.setType(outStream, conventionType)
        return foldSize(FoldFormat.STREAM)
    }

    public companion object : Unfoldable<Uuid4Type> {
        public const val UUID4_SIZE: Long = 16L

        override val foldFormatSupport: List<FoldFormat> = listOf(FoldFormat.BLOCK, FoldFormat.STREAM)
        override val conventionType: Convention = Convention.UUID4
        override val atomicSize: Int = 16

        public fun unfoldFromBlock(inData: Retrievable, offset: Int = 0): Uuid4Type {
            return Uuid4Type(Uuid4(BlockType.unfoldFromBlock(inData, offset, UUID4_SIZE).block))
        }

        public fun unfoldFromStream(inStream: Readable): Uuid4Type {
            val block = BlockType(UUID4_SIZE)
            block.storeLong(0, inStream.readLong())
            block.storeLong(8, inStream.readLong())
            return Uuid4Type(Uuid4(block.block))
        }
    }
}