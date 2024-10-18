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

import org.angproj.aux.buf.IntBuffer
import org.angproj.aux.io.*
import org.angproj.aux.pkg.Convention
import org.angproj.aux.pkg.FoldFormat
import kotlin.jvm.JvmInline


@JvmInline
public value class IntArrayType(public override val value: IntBuffer): ArrayEnfoldable<Int, IntBuffer> {

    override fun foldSize(foldFormat: FoldFormat): Long = ArrayEnfoldable.arrayFoldSize(
        value, atomicSize, foldFormat)

    public fun enfoldToBlock(outData: Storable, offset: Int = 0): Long = ArrayEnfoldable.arrayEnfoldToBlock(
        value, atomicSize, outData, offset) { o, i, v -> o.storeInt(i, v) }

    public fun enfoldToStream(outStream: BinaryWritable): Long = ArrayEnfoldable.arrayEnfoldToStream(
        value, atomicSize, conventionType, outStream) { o, v -> o.writeInt(v) }

    public companion object : ArrayUnfoldable<Int, IntBuffer, IntArrayType> {
        override val factory: (count: Int) -> IntArrayType = { c -> IntArrayType(IntBuffer(c)) }

        override val foldFormatSupport: List<FoldFormat> = listOf(FoldFormat.BLOCK, FoldFormat.STREAM)
        override val conventionType: Convention = Convention.ARRAY_INT
        override val atomicSize: Int = TypeSize.int

        public fun unfoldFromBlock(
            inData: Retrievable,
            value: IntBuffer
        ): Long = unfoldFromBlock(inData, 0, value)

        public fun unfoldFromBlock(
            inData: Retrievable,
            offset: Int,
            value: IntBuffer
        ): Long = ArrayUnfoldable.arrayUnfoldFromBlock(
            inData, offset, value, atomicSize
        ) { d, i -> d.retrieveInt(i) }

        /*public fun unfoldFromBlock(
            inData: Retrievable,
            count: Int
        ): IntArrayType = unfoldFromBlock(inData, 0, count)

        public fun unfoldFromBlock(
            inData: Retrievable,
            offset: Int,
            count: Int
        ): IntArrayType = ArrayUnfoldable.arrayUnfoldFromBlock(
            inData, offset, count, atomicSize, factory) { d, i -> d.retrieveInt(i) }*/

        public fun unfoldFromStream(
            inStream: BinaryReadable
        ): IntArrayType = ArrayUnfoldable.arrayUnfoldFromStream(
            inStream, conventionType, factory) { s -> s.readInt() }
    }
}