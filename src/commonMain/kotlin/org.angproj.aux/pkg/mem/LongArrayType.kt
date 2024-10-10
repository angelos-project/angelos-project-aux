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

import org.angproj.aux.buf.LongBuffer
import org.angproj.aux.io.*
import org.angproj.aux.pkg.Convention
import org.angproj.aux.pkg.FoldFormat
import kotlin.jvm.JvmInline


@JvmInline
public value class LongArrayType(public override val value: LongBuffer): ArrayEnfoldable<Long, LongBuffer> {

    override val foldFormat: FoldFormat
        get() = TODO("Not yet implemented")

    override fun foldSize(foldFormat: FoldFormat): Long = ArrayEnfoldable.arrayFoldSize(
        value, atomicSize, foldFormat)

    public fun enfoldToBlock(outData: Storable, offset: Int = 0): Long = ArrayEnfoldable.arrayEnfoldToBlock(
        value, atomicSize, outData, offset) { o, i, v -> o.storeLong(i, v) }

    public fun enfoldToStream(outStream: Writable): Long = ArrayEnfoldable.arrayEnfoldToStream(
        value, atomicSize, conventionType, outStream) { o, v -> o.writeLong(v) }

    public companion object : ArrayUnfoldable<Long, LongBuffer, LongArrayType> {
        override val factory: (count: Int) -> LongArrayType = { c -> LongArrayType(LongBuffer(c)) }

        override val foldFormatSupport: List<FoldFormat> = listOf(FoldFormat.BLOCK, FoldFormat.STREAM)
        override val conventionType: Convention = Convention.ARRAY_LONG
        override val atomicSize: Int = TypeSize.long

        public fun unfoldFromBlock(
            inData: Retrievable,
            count: Int
        ): LongArrayType = unfoldFromBlock(inData, 0, count)

        public fun unfoldFromBlock(
            inData: Retrievable,
            offset: Int,
            count: Int
        ): LongArrayType = ArrayUnfoldable.arrayUnfoldFromBlock(
            inData, offset, count, atomicSize, factory) { d, i -> d.retrieveLong(i) }

        public fun unfoldFromStream(
            inStream: Readable
        ): LongArrayType = ArrayUnfoldable.arrayUnfoldFromStream(
            inStream, conventionType, factory) { s -> s.readLong() }
    }
}