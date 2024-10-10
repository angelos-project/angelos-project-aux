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

import org.angproj.aux.buf.UIntBuffer
import org.angproj.aux.io.*
import org.angproj.aux.pkg.Convention
import org.angproj.aux.pkg.FoldFormat
import kotlin.jvm.JvmInline


@JvmInline
public value class UIntArrayType(public override val value: UIntBuffer): ArrayEnfoldable<UInt, UIntBuffer> {

    override val foldFormat: FoldFormat
        get() = TODO("Not yet implemented")

    override fun foldSize(foldFormat: FoldFormat): Long = ArrayEnfoldable.arrayFoldSize(
        value, atomicSize, foldFormat)

    public fun enfoldToBlock(outData: Storable, offset: Int = 0): Long = ArrayEnfoldable.arrayEnfoldToBlock(
        value, atomicSize, outData, offset) { o, i, v -> o.storeUInt(i, v) }

    public fun enfoldToStream(outStream: Writable): Long = ArrayEnfoldable.arrayEnfoldToStream(
        value, atomicSize, conventionType, outStream) { o, v -> o.writeUInt(v) }

    public companion object : ArrayUnfoldable<UInt, UIntBuffer, UIntArrayType> {
        override val factory: (count: Int) -> UIntArrayType = { c -> UIntArrayType(UIntBuffer(c)) }

        override val foldFormatSupport: List<FoldFormat> = listOf(FoldFormat.BLOCK, FoldFormat.STREAM)
        override val conventionType: Convention = Convention.ARRAY_UINT
        override val atomicSize: Int = TypeSize.uInt

        public fun unfoldFromBlock(
            inData: Retrievable,
            count: Int
        ): UIntArrayType = unfoldFromBlock(inData, 0, count)

        public fun unfoldFromBlock(
            inData: Retrievable,
            offset: Int,
            count: Int
        ): UIntArrayType = ArrayUnfoldable.arrayUnfoldFromBlock(
            inData, offset, count, atomicSize, factory) { d, i -> d.retrieveUInt(i) }

        public fun unfoldFromStream(
            inStream: Readable
        ): UIntArrayType = ArrayUnfoldable.arrayUnfoldFromStream(
            inStream, conventionType, factory) { s -> s.readUInt() }
    }
}