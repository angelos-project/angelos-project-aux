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
package org.angproj.aux.pkg.prime

import org.angproj.aux.io.BinaryReadable
import org.angproj.aux.io.BinaryWritable
import org.angproj.aux.io.Retrievable
import org.angproj.aux.io.Storable
import org.angproj.aux.pkg.Convention
import org.angproj.aux.pkg.Enfoldable
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.Unfoldable
import kotlin.jvm.JvmInline

@JvmInline
public value class UIntType(public val value: UInt) : Enfoldable {

    override fun foldSize(foldFormat: FoldFormat): Long = atomicSize.toLong()

    public fun enfoldToBlock(outData: Storable, offset: Int): Long {
        outData.storeUInt(offset, value)
        return foldSize(FoldFormat.BLOCK)
    }

    public fun enfoldToStream(outStream: BinaryWritable): Long {
        outStream.writeUInt(value)
        return foldSize(FoldFormat.STREAM)
    }

    public companion object : Unfoldable<UIntType> {
        override val foldFormatSupport: List<FoldFormat> = listOf(FoldFormat.BLOCK, FoldFormat.STREAM)
        override val conventionType: Convention = Convention.UINT
        override val atomicSize: Int = UInt.SIZE_BYTES
        public fun unfoldFromBlock(inData: Retrievable, offset: Int): UIntType = UIntType(inData.retrieveUInt(offset))

        public fun unfoldFromStream(inStream: BinaryReadable): UIntType = UIntType(inStream.readUInt())
    }
}