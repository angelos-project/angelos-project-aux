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
import org.angproj.aux.io.Retrievable
import org.angproj.aux.io.Storable
import org.angproj.aux.pkg.Convention
import org.angproj.aux.pkg.Enfoldable
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.Unfoldable
import org.angproj.aux.pkg.type.BlockType
import org.angproj.aux.util.Uuid4
import org.angproj.aux.util.isNull
import org.angproj.aux.util.uuid4Of
import kotlin.jvm.JvmInline

@JvmInline
public value class Uuid4Type(public val value: Uuid4) : Enfoldable {

    override fun foldSize(foldFormat: FoldFormat): Int = when (foldFormat) {
        FoldFormat.BLOCK -> atomicSize
        FoldFormat.STREAM -> atomicSize + Enfoldable.OVERHEAD_LENGTH
    }

    public fun enfoldToBlock(outData: Storable, offset: Int = 0): Int = BlockType(
        value.asBinary()).enfoldToBlock(outData, offset)

    public fun enfoldToStream(outStream: BinaryWritable): Int = BlockType(
        value.asBinary()).enfoldToStreamByConvention(outStream, conventionType)

    public companion object : Unfoldable<Uuid4Type> {
        override val foldFormatSupport: List<FoldFormat> = listOf(FoldFormat.BLOCK, FoldFormat.STREAM)
        override val conventionType: Convention = Convention.UUID4
        override val atomicSize: Int = 16

        public fun unfoldFromBlock(inData: Retrievable, uuid4: Uuid4, offset: Int = 0): Int {
            require(!uuid4.isNull()) { "Null UUID" }
            return BlockType.unfoldFromBlock(inData, offset, uuid4.asBinary())
        }

        public fun unfoldFromBlock(inData: Retrievable, offset: Int = 0): Uuid4Type {
            return Uuid4Type(uuid4Of(BlockType.unfoldFromBlock(inData, offset, atomicSize.toLong()).block))
        }

        public fun unfoldFromStream(inStream: BinaryReadable): Uuid4Type {
            return Uuid4Type(uuid4Of(BlockType.unfoldFromStreamByConvention(inStream, conventionType).block))
        }
    }
}