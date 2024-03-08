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

import org.angproj.aux.io.Readable
import org.angproj.aux.pkg.arb.BigIntType
import org.angproj.aux.pkg.arb.StringType
import org.angproj.aux.pkg.arb.StructType
import org.angproj.aux.pkg.arb.Uuid4Type
import org.angproj.aux.pkg.coll.DictType
import org.angproj.aux.pkg.coll.ListType
import org.angproj.aux.pkg.coll.ObjectType
import org.angproj.aux.pkg.mem.*
import org.angproj.aux.pkg.prime.*
import org.angproj.aux.pkg.type.BlockType

public interface StreamUnpackageable : Packageable {
    public fun unfold(inStream: Readable)

    public companion object {
        protected fun loadBlock(inStream: Readable): BlockType = BlockType.unfoldFromStream(inStream)

        protected fun loadByte(inStream: Readable): ByteType = ByteType.unfoldFromStream(inStream)
        protected fun loadShort(inStream: Readable): ShortType = ShortType.unfoldFromStream(inStream)
        protected fun loadInt(inStream: Readable): IntType = IntType.unfoldFromStream(inStream)
        protected fun loadLong(inStream: Readable): LongType = LongType.unfoldFromStream(inStream)
        protected fun loadFloat(inStream: Readable): FloatType = FloatType.unfoldFromStream(inStream)
        protected fun loadDouble(inStream: Readable): DoubleType = DoubleType.unfoldFromStream(inStream)
        protected fun loadUByte(inStream: Readable): UByteType = UByteType.unfoldFromStream(inStream)
        protected fun loadUShort(inStream: Readable): UShortType = UShortType.unfoldFromStream(inStream)
        protected fun loadUInt(inStream: Readable): UIntType = UIntType.unfoldFromStream(inStream)
        protected fun loadULong(inStream: Readable): ULongType = ULongType.unfoldFromStream(inStream)

        protected fun loadShortArray(inStream: Readable): ShortArrayType = ShortArrayType.unfoldFromStream(inStream)
        protected fun loadIntArray(inStream: Readable): IntArrayType = IntArrayType.unfoldFromStream(inStream)
        protected fun loadLongArray(inStream: Readable): LongArrayType = LongArrayType.unfoldFromStream(inStream)
        protected fun loadFloatArray(inStream: Readable): FloatArrayType = FloatArrayType.unfoldFromStream(inStream)
        protected fun loadDoubleArray(inStream: Readable): DoubleArrayType = DoubleArrayType.unfoldFromStream(inStream)

        protected fun loadUuid4(inStream: Readable): Uuid4Type = Uuid4Type.unfoldFromStream(inStream)
        protected fun loadString(inStream: Readable): StringType = StringType.unfoldFromStream(inStream)
        protected fun loadBigInt(inStream: Readable): BigIntType = BigIntType.unfoldFromStream(inStream)

        protected fun <P: BlockPackageable> loadStruct(inStream: Readable): StructType<*> = StructType.unfoldStream(inStream)
        protected fun <P: StreamPackageable> loadObject(inStream: Readable): ObjectType<*> = ObjectType.unfoldStream(inStream)
        protected fun <P: Packageable> loadDict(inStream: Readable): DictType<*> = DictType.unfoldStream(inStream)
        protected fun <P: Packageable> loadList(inStream: Readable): ListType<*> = ListType.unfoldStream(inStream)
    }
}