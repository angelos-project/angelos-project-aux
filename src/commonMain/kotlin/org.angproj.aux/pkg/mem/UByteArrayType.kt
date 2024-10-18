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

import org.angproj.aux.buf.UByteBuffer
import org.angproj.aux.io.*
import org.angproj.aux.pkg.Convention
import org.angproj.aux.pkg.FoldFormat
import kotlin.jvm.JvmInline


@JvmInline
public value class UByteArrayType(public override val value: UByteBuffer): ArrayEnfoldable<UByte,UByteBuffer> {

    override fun foldSize(foldFormat: FoldFormat): Long = ArrayEnfoldable.arrayFoldSize(
        value, atomicSize, foldFormat)

    public fun enfoldToBlock(outData: Storable, offset: Int = 0): Long = ArrayEnfoldable.arrayEnfoldToBlock(
        value, atomicSize, outData, offset) { o, i, v -> o.storeUByte(i, v) }

    public fun enfoldToStream(outStream: BinaryWritable): Long = ArrayEnfoldable.arrayEnfoldToStream(
        value, atomicSize, conventionType, outStream) { o, v -> o.writeUByte(v) }

    public companion object : ArrayUnfoldable<UByte, UByteBuffer, UByteArrayType> {
        override val factory: (count: Int) -> UByteArrayType = { c -> UByteArrayType(UByteBuffer(c)) }

        override val foldFormatSupport: List<FoldFormat> = listOf(FoldFormat.BLOCK, FoldFormat.STREAM)
        override val conventionType: Convention = Convention.ARRAY_UBYTE
        override val atomicSize: Int = TypeSize.uByte

        public fun unfoldFromBlock(
            inData: Retrievable,
            value: UByteBuffer
        ): Long = unfoldFromBlock(inData, 0, value)

        public fun unfoldFromBlock(
            inData: Retrievable,
            offset: Int,
            value: UByteBuffer
        ): Long = ArrayUnfoldable.arrayUnfoldFromBlock(
            inData, offset, value, atomicSize
        ) { d, i -> d.retrieveUByte(i) }

        /*public fun unfoldFromBlock(
            inData: Retrievable,
            count: Int
        ): UByteArrayType = unfoldFromBlock(inData, 0, count)

        public fun unfoldFromBlock(
            inData: Retrievable,
            offset: Int,
            count: Int
        ): UByteArrayType = ArrayUnfoldable.arrayUnfoldFromBlock(
            inData, offset, count, atomicSize, factory) { d, i -> d.retrieveUByte(i) }*/

        public fun unfoldFromStream(
            inStream: BinaryReadable
        ): UByteArrayType = ArrayUnfoldable.arrayUnfoldFromStream(
            inStream, conventionType, factory) { s -> s.readUByte() }
    }
}