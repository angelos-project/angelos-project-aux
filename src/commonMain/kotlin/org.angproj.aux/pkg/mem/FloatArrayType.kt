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
package org.angproj.aux.pkg.mem

import org.angproj.aux.buf.FloatBuffer
import org.angproj.aux.io.*
import org.angproj.aux.pkg.Convention
import org.angproj.aux.pkg.FoldFormat
import kotlin.jvm.JvmInline


@JvmInline
public value class FloatArrayType(public override val value: FloatBuffer): ArrayEnfoldable<Float, FloatBuffer> {

    override fun foldSize(foldFormat: FoldFormat): Long = ArrayEnfoldable.arrayFoldSize(
        value, atomicSize, foldFormat)

    public fun enfoldToBlock(outData: Storable, offset: Int = 0): Long = ArrayEnfoldable.arrayEnfoldToBlock(
        value, atomicSize, outData, offset) { o, i, v -> o.storeFloat(i, v) }

    public fun enfoldToStream(outStream: BinaryWritable): Long = ArrayEnfoldable.arrayEnfoldToStream(
        value, atomicSize, conventionType, outStream) { o, v -> o.writeFloat(v) }

    public companion object : ArrayUnfoldable<Float, FloatBuffer, FloatArrayType> {
        override val factory: (count: Int) -> FloatArrayType = { c -> FloatArrayType(FloatBuffer(c)) }

        override val foldFormatSupport: List<FoldFormat> = listOf(FoldFormat.BLOCK, FoldFormat.STREAM)
        override val conventionType: Convention = Convention.ARRAY_FLOAT
        override val atomicSize: Int = TypeSize.float

        public fun unfoldFromBlock(
            inData: Retrievable,
            value: FloatBuffer
        ): Long = unfoldFromBlock(inData, 0, value)

        public fun unfoldFromBlock(
            inData: Retrievable,
            offset: Int,
            value: FloatBuffer
        ): Long = ArrayUnfoldable.arrayUnfoldFromBlock(
            inData, offset, value, atomicSize
        ) { d, i -> d.retrieveFloat(i) }

        /*public fun unfoldFromBlock(
            inData: Retrievable,
            count: Int
        ): FloatArrayType = unfoldFromBlock(inData, 0, count)

        public fun unfoldFromBlock(
            inData: Retrievable,
            offset: Int,
            count: Int
        ): FloatArrayType = ArrayUnfoldable.arrayUnfoldFromBlock(
            inData, offset, count, atomicSize, factory) { d, i -> d.retrieveFloat(i) }*/

        public fun unfoldFromStream(
            inStream: BinaryReadable
        ): FloatArrayType = ArrayUnfoldable.arrayUnfoldFromStream(
            inStream, conventionType, factory) { s -> s.readFloat() }
    }
}