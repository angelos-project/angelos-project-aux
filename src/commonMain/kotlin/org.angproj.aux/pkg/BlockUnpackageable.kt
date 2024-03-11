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
package org.angproj.aux.pkg

import org.angproj.aux.io.Retrievable
import org.angproj.aux.pkg.arb.Uuid4Type
import org.angproj.aux.pkg.mem.*
import org.angproj.aux.pkg.prime.*

public interface BlockUnpackageable : Packageable {
    public fun unfold(inData: Retrievable): BlockPackageable

    public companion object {
        protected fun loadByte(inData: Retrievable, offset: Int): ByteType = ByteType.unfoldFromBlock(inData, offset)
        protected fun loadShort(inData: Retrievable, offset: Int): ShortType = ShortType.unfoldFromBlock(inData, offset)
        protected fun loadInt(inData: Retrievable, offset: Int): IntType = IntType.unfoldFromBlock(inData, offset)
        protected fun loadLong(inData: Retrievable, offset: Int): LongType = LongType.unfoldFromBlock(inData, offset)
        protected fun loadFloat(inData: Retrievable, offset: Int): FloatType = FloatType.unfoldFromBlock(inData, offset)
        protected fun loadDouble(inData: Retrievable, offset: Int): DoubleType =
            DoubleType.unfoldFromBlock(inData, offset)

        protected fun loadUByte(inData: Retrievable, offset: Int): UByteType = UByteType.unfoldFromBlock(inData, offset)
        protected fun loadUShort(inData: Retrievable, offset: Int): UShortType =
            UShortType.unfoldFromBlock(inData, offset)

        protected fun loadUInt(inData: Retrievable, offset: Int): UIntType = UIntType.unfoldFromBlock(inData, offset)
        protected fun loadULong(inData: Retrievable, offset: Int): ULongType = ULongType.unfoldFromBlock(inData, offset)

        protected fun loadShortArray(inData: Retrievable, offset: Int, count: Int): ShortArrayType =
            ShortArrayType.unfoldFromBlock(inData, offset, count)

        protected fun loadIntArray(inData: Retrievable, offset: Int, count: Int): IntArrayType =
            IntArrayType.unfoldFromBlock(inData, offset, count)

        protected fun loadLongArray(inData: Retrievable, offset: Int, count: Int): LongArrayType =
            LongArrayType.unfoldFromBlock(inData, offset, count)

        protected fun loadFloatArray(inData: Retrievable, offset: Int, count: Int): FloatArrayType =
            FloatArrayType.unfoldFromBlock(inData, offset, count)

        protected fun loadDoubleArray(inData: Retrievable, offset: Int, count: Int): DoubleArrayType =
            DoubleArrayType.unfoldFromBlock(inData, offset, count)

        protected fun loadUuid4(inData: Retrievable, offset: Int): Uuid4Type = Uuid4Type.unfoldFromBlock(inData, offset)
    }
}