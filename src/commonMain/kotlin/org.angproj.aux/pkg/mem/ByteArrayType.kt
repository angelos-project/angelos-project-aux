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

import org.angproj.aux.buf.ByteBuffer
import org.angproj.aux.io.*
import org.angproj.aux.pkg.Convention
import org.angproj.aux.pkg.FoldFormat
import kotlin.jvm.JvmInline


@JvmInline
public value class ByteArrayType(public override val value: ByteBuffer): ArrayEnfoldable<Byte, ByteBuffer> {

    override val foldFormat: FoldFormat
        get() = TODO("Not yet implemented")

    override fun foldSize(foldFormat: FoldFormat): Long = ArrayEnfoldable.arrayFoldSize(
        value, atomicSize, foldFormat)

    public fun enfoldToBlock(outData: Storable, offset: Int = 0): Long = ArrayEnfoldable.arrayEnfoldToBlock(
        value, atomicSize, outData, offset) { o, i, v -> o.storeByte(i, v) }

    public fun enfoldToStream(outStream: Writable): Long = ArrayEnfoldable.arrayEnfoldToStream(
        value, atomicSize, conventionType, outStream) { o, v -> o.writeByte(v) }

    public companion object : ArrayUnfoldable<Byte, ByteBuffer, ByteArrayType> {
        override val factory: (count: Int) -> ByteArrayType = { c -> ByteArrayType(ByteBuffer(c)) }

        override val foldFormatSupport: List<FoldFormat> = listOf(FoldFormat.BLOCK, FoldFormat.STREAM)
        override val conventionType: Convention = Convention.ARRAY_BYTE
        override val atomicSize: Int = TypeSize.byte

        public fun unfoldFromBlock(
            inData: Retrievable,
            count: Int
        ): ByteArrayType = unfoldFromBlock(inData, 0, count)

        public fun unfoldFromBlock(
            inData: Retrievable,
            offset: Int,
            count: Int
        ): ByteArrayType = ArrayUnfoldable.arrayUnfoldFromBlock(
            inData, offset, count, atomicSize, factory) { d, i -> d.retrieveByte(i) }

        public fun unfoldFromStream(
            inStream: Readable
        ): ByteArrayType = ArrayUnfoldable.arrayUnfoldFromStream(
            inStream, conventionType, factory) { s -> s.readByte() }
    }
}