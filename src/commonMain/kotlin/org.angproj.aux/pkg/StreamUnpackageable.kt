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
        protected fun loadBlock(inStream: Readable): BlockType = BlockType.unfold(inStream)

        protected fun loadByte(inStream: Readable): ByteType = ByteType.unfold(inStream)
        protected fun loadShort(inStream: Readable): ShortType = ShortType.unfold(inStream)
        protected fun loadInt(inStream: Readable): IntType = IntType.unfold(inStream)
        protected fun loadLong(inStream: Readable): LongType = LongType.unfold(inStream)
        protected fun loadFloat(inStream: Readable): FloatType = FloatType.unfold(inStream)
        protected fun loadDouble(inStream: Readable): DoubleType = DoubleType.unfold(inStream)
        protected fun loadUShort(inStream: Readable): UShortType = UShortType.unfold(inStream)
        protected fun loadUInt(inStream: Readable): UIntType = UIntType.unfold(inStream)
        protected fun loadULong(inStream: Readable): ULongType = ULongType.unfold(inStream)

        protected fun loadShortArray(inStream: Readable): ShortArrayType = ShortArrayType.unfold(inStream)
        protected fun loadIntArray(inStream: Readable): IntArrayType = IntArrayType.unfold(inStream)
        protected fun loadLongArray(inStream: Readable): LongArrayType = LongArrayType.unfold(inStream)
        protected fun loadFloatArray(inStream: Readable): FloatArrayType = FloatArrayType.unfold(inStream)
        protected fun loadDoubleArray(inStream: Readable): DoubleArrayType = DoubleArrayType.unfold(inStream)

        protected fun loadUuid4(inStream: Readable): Uuid4Type = Uuid4Type.unfold(inStream)
        protected fun loadString(inStream: Readable): StringType = StringType.unfold(inStream)
        protected fun loadBigInt(inStream: Readable): BigIntType = BigIntType.unfold(inStream)

        protected fun <P: BlockPackageable> loadStruct(inStream: Readable): StructType<*> = StructType.unfold(inStream)
        protected fun <P: StreamPackageable> loadObject(inStream: Readable): ObjectType<*> = ObjectType.unfold(inStream)
        protected fun <P: Packageable> loadDict(inStream: Readable): DictType<*> = DictType.unfold(inStream)
        protected fun <P: Packageable> loadList(inStream: Readable): ListType<*> = ListType.unfold(inStream)
    }
}