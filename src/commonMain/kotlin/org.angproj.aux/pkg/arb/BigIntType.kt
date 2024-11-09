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
import org.angproj.aux.num.BigInt
import org.angproj.aux.num.bigIntOf
import org.angproj.aux.num.isNull
import org.angproj.aux.pkg.*
import kotlin.jvm.JvmInline


@JvmInline
public value class BigIntType(public val value: BigInt) : Enfoldable {

    override fun foldSize(foldFormat: FoldFormat): Int = when (foldFormat) {
        FoldFormat.BLOCK -> error("Unsupported fold format.")
        FoldFormat.STREAM -> (value.getByteSize() + Enfoldable.OVERHEAD_LENGTH)
    }

    public override fun enfoldStream(outStream: BinaryWritable): Int = outStream.measureBytes {
        require(!value.isNull()) { "Null BigInt" }
        val barr = value.toByteArray()
        Enfoldable.setType(outStream, conventionType)
        Enfoldable.setLength(outStream, barr.size)
        barr.forEach { outStream.writeByte(it) }
        Enfoldable.setEnd(outStream, conventionType)
    }.toInt()
    /*{
        require(!value.isNull()) { "Null BigInt" }
        val block = BlockType(value.toByteArray().toBinary())
        return block.enfoldToStreamByConvention(outStream, conventionType)
    }*/

    public companion object : Unfoldable<BigIntType> {
        override val foldFormatSupport: List<FoldFormat> = listOf(FoldFormat.STREAM)
        override val conventionType: Convention = Convention.BIGINT
        override val atomicSize: Int = 0

        public override fun unfoldStream(inStream: BinaryReadable): BigIntType {
            Unfoldable.getType(inStream, conventionType)
            val barrSize = Unfoldable.getLength(inStream)
            val barr = inStream.measureBytes(barrSize) { ByteArray(barrSize) { inStream.readByte() } }
            Unfoldable.getEnd(inStream, conventionType)
            return BigIntType(bigIntOf(barr))
        }
    }
}