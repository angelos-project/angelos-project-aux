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

import org.angproj.aux.buf.ULongBuffer
import org.angproj.aux.buf.isNull
import org.angproj.aux.io.*
import org.angproj.aux.pkg.ARRAY_ULONG
import org.angproj.aux.pkg.Convention
import org.angproj.aux.pkg.FoldFormat
import kotlin.jvm.JvmInline


@JvmInline
public value class ULongArrayType(public override val value: ULongBuffer): ArrayEnfoldable<ULong, ULongBuffer> {

    override fun foldSize(foldFormat: FoldFormat): Int = ArrayEnfoldable.arrayFoldSize(
        value, atomicSize, foldFormat)

    public override fun enfoldBlock(outData: Storable, offset: Int): Int = ArrayEnfoldable.arrayEnfoldToBlock(
        value, atomicSize, outData, offset) { o, i, v -> o.storeULong(i, v) }

    public override fun enfoldStream(outStream: BinaryWritable): Int = ArrayEnfoldable.arrayEnfoldToStream(
        value, conventionType, outStream) { o, v -> o.writeULong(v) }

    public companion object : ArrayUnfoldable<ULong, ULongBuffer, ULongArrayType> {
        override val factory: (count: Int) -> ULongBuffer = { c -> ULongBuffer(c) }

        override val foldFormatSupport: List<FoldFormat> = listOf(FoldFormat.BLOCK, FoldFormat.STREAM)
        override val conventionType: Convention = Convention.ARRAY_ULONG
        override val atomicSize: Int = TypeSize.uLong

        public fun unfoldFromBlock(
            inData: Retrievable,
            value: ULongBuffer
        ): Int = unfoldFromBlock(inData, 0, value)

        public fun unfoldFromBlock(
            inData: Retrievable,
            offset: Int,
            value: ULongBuffer
        ): Int {
            require(!value.isNull()) { "Null Array!" }
            return ArrayUnfoldable.arrayUnfoldFromBlock(
                inData, offset, value, atomicSize
            ) { d, i -> d.retrieveULong(i) }
        }

        /*public fun unfoldFromBlock(
            inData: Retrievable,
            count: Int
        ): ULongArrayType = unfoldFromBlock(inData, 0, count)

        public fun unfoldFromBlock(
            inData: Retrievable,
            offset: Int,
            count: Int
        ): ULongArrayType = ArrayUnfoldable.arrayUnfoldFromBlock(
            inData, offset, count, atomicSize, factory) { d, i -> d.retrieveULong(i) }*/

        public override fun unfoldStream(
            inStream: BinaryReadable
        ): ULongArrayType = ULongArrayType(ArrayUnfoldable.arrayUnfoldFromStream(
            inStream, conventionType, factory) { s -> s.readULong() })

        public override fun unfoldFromStream(
            inStream: BinaryReadable,
            value: ULongBuffer
        ) {
            require(!value.isNull()) { "Null Array!" }
            ArrayUnfoldable.arrayUnfoldFromStream(inStream, conventionType, value) { s -> s.readULong() }
        }
    }
}