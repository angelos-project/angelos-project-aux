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
public value class FloatType(public val value: Float) : Enfoldable {

    override fun foldSize(foldFormat: FoldFormat): Int = atomicSize

    public override fun enfoldBlock(outData: Storable, offset: Int): Int {
        outData.storeFloat(offset, value)
        return foldSize(FoldFormat.BLOCK)
    }

    public override fun enfoldStream(outStream: BinaryWritable): Int {
        outStream.writeFloat(value)
        return foldSize(FoldFormat.STREAM)
    }

    public companion object : Unfoldable<FloatType> {
        override val foldFormatSupport: List<FoldFormat> = listOf(FoldFormat.BLOCK, FoldFormat.STREAM)
        override val conventionType: Convention = Convention.FLOAT
        override val atomicSize: Int = Float.SIZE_BYTES

        public override fun unfoldBlock(inData: Retrievable, offset: Int): FloatType =
            FloatType(inData.retrieveFloat(offset))

        public override fun unfoldStream(inStream: BinaryReadable): FloatType = FloatType(inStream.readFloat())
    }
}