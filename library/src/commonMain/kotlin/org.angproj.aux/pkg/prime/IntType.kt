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
import org.angproj.aux.pkg.*
import kotlin.jvm.JvmInline

@JvmInline
public value class IntType(public val value: Int) : Enfoldable {

    override fun foldSize(foldFormat: FoldFormat): Int = atomicSize

    public override fun enfoldBlock(outData: Storable, offset: Int): Int {
        outData.storeInt(offset, value)
        return foldSize(FoldFormat.BLOCK)
    }

    public override fun enfoldStream(outStream: BinaryWritable): Int {
        outStream.writeInt(value)
        return foldSize(FoldFormat.STREAM)
    }

    public companion object : Unfoldable<IntType> {
        override val foldFormatSupport: List<FoldFormat> = listOf(FoldFormat.BLOCK, FoldFormat.STREAM)
        override val conventionType: Convention = Convention.INT
        override val atomicSize: Int = Int.SIZE_BYTES

        public override fun unfoldBlock(inData: Retrievable, offset: Int): IntType = IntType(inData.retrieveInt(offset))

        public override fun unfoldStream(inStream: BinaryReadable): IntType = IntType(inStream.readInt())
    }
}