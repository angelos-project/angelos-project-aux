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

import org.angproj.aux.buf.UShortBuffer
import org.angproj.aux.io.*
import org.angproj.aux.pkg.Convention
import org.angproj.aux.pkg.FoldFormat
import kotlin.jvm.JvmInline


@JvmInline
public value class UShortArrayType(public override val value: UShortBuffer): ArrayEnfoldable<UShort, UShortBuffer> {

    override fun foldSize(foldFormat: FoldFormat): Long = ArrayEnfoldable.arrayFoldSize(
        value, atomicSize, foldFormat)

    public fun enfoldToBlock(outData: Storable, offset: Int = 0): Long = ArrayEnfoldable.arrayEnfoldToBlock(
        value, atomicSize, outData, offset) { o, i, v -> o.storeUShort(i, v) }

    public fun enfoldToStream(outStream: BinaryWritable): Long = ArrayEnfoldable.arrayEnfoldToStream(
        value, atomicSize, conventionType, outStream) { o, v -> o.writeUShort(v) }

    public companion object : ArrayUnfoldable<UShort, UShortBuffer, UShortArrayType> {
        override val factory: (count: Int) -> UShortArrayType = { c -> UShortArrayType(UShortBuffer(c)) }

        override val foldFormatSupport: List<FoldFormat> = listOf(FoldFormat.BLOCK, FoldFormat.STREAM)
        override val conventionType: Convention = Convention.ARRAY_USHORT
        override val atomicSize: Int = TypeSize.uShort

        public fun unfoldFromBlock(
            inData: Retrievable,
            value: UShortBuffer
        ): Long = unfoldFromBlock(inData, 0, value)

        public fun unfoldFromBlock(
            inData: Retrievable,
            offset: Int,
            value: UShortBuffer
        ): Long = ArrayUnfoldable.arrayUnfoldFromBlock(
            inData, offset, value, atomicSize
        ) { d, i -> d.retrieveUShort(i) }

        /*public fun unfoldFromBlock(
            inData: Retrievable,
            count: Int
        ): UShortArrayType = unfoldFromBlock(inData, 0, count)

        public fun unfoldFromBlock(
            inData: Retrievable,
            offset: Int,
            count: Int
        ): UShortArrayType = ArrayUnfoldable.arrayUnfoldFromBlock(
            inData, offset, count, atomicSize, factory) { d, i -> d.retrieveUShort(i) }*/

        public fun unfoldFromStream(
            inStream: BinaryReadable
        ): UShortArrayType = ArrayUnfoldable.arrayUnfoldFromStream(
            inStream, conventionType, factory) { s -> s.readUShort() }
    }
}