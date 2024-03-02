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
public value class DoubleType(public val value: Double) : EnfoldablePrime {
    override fun foldSize(foldFormat: FoldFormat): Long = when(foldFormat) {
        FoldFormat.BLOCK -> Double.SIZE_BYTES.toLong()
        FoldFormat.STREAM -> Double.SIZE_BYTES.toLong() + Enfoldable.TYPE_SIZE
        else -> error("Specify size for valid type.")
    }

    override fun enfold(outData: Storable, offset: Int): Long {
        outData.storeDouble(offset, value)
        return foldSize(FoldFormat.BLOCK)
    }

    override fun enfold(outStream: Writable): Long {
        Enfoldable.setType(outStream, Convention.DOUBLE)
        outStream.writeDouble(value)
        return foldSize(FoldFormat.STREAM)
    }

    public companion object : UnfoldablePrime<DoubleType> {
        override val foldFormatSupport: FoldFormat = FoldFormat.BOTH

        override fun unfold(inData: Retrievable, offset: Int): DoubleType = DoubleType(inData.retrieveDouble(offset))

        override fun unfold(inStream: Readable): DoubleType {
            require(Unfoldable.getType(inStream, Convention.DOUBLE))
            return DoubleType(inStream.readDouble())
        }
    }
}