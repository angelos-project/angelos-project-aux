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
import org.angproj.aux.pkg.*
import kotlin.jvm.JvmInline

@JvmInline
public value class DoubleType(public val value: Double) : Enfoldable {
    override val foldFormat: FoldFormat
        get() = TODO("Not yet implemented")
    override fun foldSize(foldFormat: FoldFormat): Long = Double.SIZE_BYTES.toLong()

    override fun enfold(outData: Storable, offset: Int): Long {
        outData.storeDouble(offset, value)
        return foldSize(FoldFormat.BLOCK)
    }

    override fun enfold(outStream: Writable): Long {
        outStream.writeDouble(value)
        return foldSize(FoldFormat.STREAM)
    }

    public companion object : Unfoldable<DoubleType> {
        override val foldFormatSupport: FoldFormat = FoldFormat.BOTH

        override fun unfold(inData: Retrievable, offset: Int): DoubleType = DoubleType(inData.retrieveDouble(offset))

        override fun unfold(inStream: Readable): DoubleType = DoubleType(inStream.readDouble())
    }
}