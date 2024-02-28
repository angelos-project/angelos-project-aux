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
package org.angproj.aux.pkg.type

import org.angproj.aux.io.Readable
import org.angproj.aux.io.Retrievable
import org.angproj.aux.io.Storable
import org.angproj.aux.io.Writable
import org.angproj.aux.pkg.*
import kotlin.jvm.JvmInline

@JvmInline
public value class IntType(public val value: Int) : EnfoldablePrime {
    override fun foldSize(foldFormat: FoldFormat): Long = when(foldFormat) {
        FoldFormat.BLOCK -> Int.SIZE_BYTES.toLong()
        FoldFormat.STREAM -> Int.SIZE_BYTES.toLong() + Enfoldable.TYPE_SIZE
        else -> error("Specify size for valid type.")
    }

    override fun enfold(outData: Storable, offset: Int): Long {
        outData.storeInt(offset, value)
        return foldSize(FoldFormat.BLOCK)
    }

    override fun enfold(outStream: Writable): Long {
        Enfoldable.setType(outStream, Convention.INT)
        outStream.writeInt(value)
        return foldSize(FoldFormat.STREAM)
    }

    public companion object : UnfoldablePrime<IntType> {
        override val foldFormat: FoldFormat = FoldFormat.BOTH

        override fun unfold(inData: Retrievable, offset: Int): IntType = IntType(inData.retrieveInt(offset))

        override fun unfold(inStream: Readable): IntType {
            require(Unfoldable.getType(inStream, Convention.INT))
            return IntType(inStream.readInt())
        }
    }
}