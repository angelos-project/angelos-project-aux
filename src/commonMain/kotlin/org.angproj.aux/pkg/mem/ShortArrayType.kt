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

import org.angproj.aux.buf.ShortBuffer
import org.angproj.aux.buf.isNull
import org.angproj.aux.io.*
import org.angproj.aux.pkg.Convention
import org.angproj.aux.pkg.FoldFormat
import kotlin.jvm.JvmInline


@JvmInline
public value class ShortArrayType(public override val value: ShortBuffer): ArrayEnfoldable<Short, ShortBuffer> {

    override fun foldSize(foldFormat: FoldFormat): Int = ArrayEnfoldable.arrayFoldSize(
        value, atomicSize, foldFormat)

    public override fun enfoldBlock(outData: Storable, offset: Int): Int = ArrayEnfoldable.arrayEnfoldToBlock(
        value, atomicSize, outData, offset) { o, i, v -> o.storeShort(i, v) }

    public override fun enfoldStream(outStream: BinaryWritable): Int = ArrayEnfoldable.arrayEnfoldToStream(
        value, conventionType, outStream) { o, v -> o.writeShort(v) }

    public companion object : ArrayUnfoldable<Short, ShortBuffer, ShortArrayType> {
        override val factory: (count: Int) -> ShortBuffer = { c -> ShortBuffer(c) }

        override val foldFormatSupport: List<FoldFormat> = listOf(FoldFormat.BLOCK, FoldFormat.STREAM)
        override val conventionType: Convention = Convention.ARRAY_SHORT
        override val atomicSize: Int = TypeSize.short

        public fun unfoldFromBlock(
            inData: Retrievable,
            value: ShortBuffer
        ): Int = unfoldFromBlock(inData, 0, value)

        public fun unfoldFromBlock(
            inData: Retrievable,
            offset: Int,
            value: ShortBuffer
        ): Int {
            require(!value.isNull()) { "Null Array!" }
            return ArrayUnfoldable.arrayUnfoldFromBlock(
                inData, offset, value, atomicSize
            ) { d, i -> d.retrieveShort(i) }
        }

        /*public fun unfoldFromBlock(
            inData: Retrievable,
            count: Int
        ): ShortArrayType = unfoldFromBlock(inData, 0, count)

        public fun unfoldFromBlock(
            inData: Retrievable,
            offset: Int,
            count: Int
        ): ShortArrayType = ArrayUnfoldable.arrayUnfoldFromBlock(
            inData, offset, count, atomicSize, factory) { d, i -> d.retrieveShort(i) }*/

        public override fun unfoldStream(
            inStream: BinaryReadable
        ): ShortArrayType = ShortArrayType(ArrayUnfoldable.arrayUnfoldFromStream(
            inStream, conventionType, factory) { s -> s.readShort() })

        public override fun unfoldFromStream(
            inStream: BinaryReadable,
            value: ShortBuffer
        ) {
            require(!value.isNull()) { "Null Array!" }
            ArrayUnfoldable.arrayUnfoldFromStream(inStream, conventionType, value) { s -> s.readShort() }
        }
    }
}