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

import org.angproj.aux.io.Readable
import org.angproj.aux.io.Retrievable
import org.angproj.aux.io.Storable
import org.angproj.aux.io.Writable
import org.angproj.aux.pkg.Convention
import org.angproj.aux.pkg.Enfoldable
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.Unfoldable
import kotlin.jvm.JvmInline

@JvmInline
public value class UByteType(public val value: UByte) : Enfoldable {
    override val foldFormat: FoldFormat
        get() = TODO("Not yet implemented")

    override fun foldSize(foldFormat: FoldFormat): Long = UByte.SIZE_BYTES.toLong()

    override fun enfold(outData: Storable, offset: Int): Long {
        outData.storeUByte(offset, value)
        return foldSize(FoldFormat.BLOCK)
    }

    override fun enfold(outStream: Writable): Long {
        outStream.writeUByte(value)
        return foldSize(FoldFormat.STREAM)
    }

    public companion object : Unfoldable<UByteType> {
        override val foldFormatSupport: List<FoldFormat> = listOf(FoldFormat.BLOCK, FoldFormat.STREAM)
        override val conventionType: Convention = Convention.UBYTE

        override fun unfold(inData: Retrievable, offset: Int): UByteType = UByteType(inData.retrieveUByte(offset))

        override fun unfold(inStream: Readable): UByteType = UByteType(inStream.readUByte())
    }
}