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
public value class ULongType(public val value: ULong) : Enfoldable {

    override fun foldSize(foldFormat: FoldFormat): Int = atomicSize

    public fun enfoldToBlock(outData: Storable, offset: Int): Int {
        outData.storeULong(offset, value)
        return foldSize(FoldFormat.BLOCK)
    }

    public fun enfoldToStream(outStream: BinaryWritable): Int {
        outStream.writeULong(value)
        return foldSize(FoldFormat.STREAM)
    }

    public companion object : Unfoldable<ULongType> {
        override val foldFormatSupport: List<FoldFormat> = listOf(FoldFormat.BLOCK, FoldFormat.STREAM)
        override val conventionType: Convention = Convention.ULONG
        override val atomicSize: Int = ULong.SIZE_BYTES

        public fun unfoldFromBlock(inData: Retrievable, offset: Int): ULongType =
            ULongType(inData.retrieveULong(offset))

        public fun unfoldFromStream(inStream: BinaryReadable): ULongType = ULongType(inStream.readULong())
    }
}