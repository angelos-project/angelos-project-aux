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

import org.angproj.aux.io.Writable
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

public interface StreamEnpackageable : Packageable {
    public fun enfold(outStream: Writable)

    public companion object {
        protected fun saveBlock(outStream: Writable, type: BlockType): Long = type.enfoldToStream(outStream)

        protected fun saveByte(outStream: Writable, type: ByteType): Long = type.enfoldToStream(outStream)
        protected fun saveShort(outStream: Writable, type: ShortType): Long = type.enfoldToStream(outStream)
        protected fun saveInt(outStream: Writable, type: IntType): Long = type.enfoldToStream(outStream)
        protected fun saveLong(outStream: Writable, type: LongType): Long = type.enfoldToStream(outStream)
        protected fun saveFloat(outStream: Writable, type: FloatType): Long = type.enfoldToStream(outStream)
        protected fun saveDouble(outStream: Writable, type: DoubleType): Long = type.enfoldToStream(outStream)
        protected fun saveUByte(outStream: Writable, type: UByteType): Long = type.enfoldToStream(outStream)
        protected fun saveUShort(outStream: Writable, type: UShortType): Long = type.enfoldToStream(outStream)
        protected fun saveUInt(outStream: Writable, type: UIntType): Long = type.enfoldToStream(outStream)
        protected fun saveULong(outStream: Writable, type: ULongType): Long = type.enfoldToStream(outStream)

        protected fun saveShortArray(outStream: Writable, type: ShortArrayType): Long = type.enfoldToStream(outStream)
        protected fun saveIntArray(outStream: Writable, type: IntArrayType): Long = type.enfoldToStream(outStream)
        protected fun saveLongArray(outStream: Writable, type: LongArrayType): Long = type.enfoldToStream(outStream)
        protected fun saveFloatArray(outStream: Writable, type: FloatArrayType): Long = type.enfoldToStream(outStream)
        protected fun saveDoubleArray(outStream: Writable, type: DoubleArrayType): Long = type.enfoldToStream(outStream)

        protected fun saveUuid4(outStream: Writable, type: Uuid4Type): Long = type.enfoldToStream(outStream)
        protected fun saveString(outStream: Writable, type: StringType): Long = type.enfoldToStream(outStream)
        protected fun saveBigInt(outStream: Writable, type: BigIntType): Long = type.enfoldToStream(outStream)

        protected fun <P : BlockPackageable> saveStruct(outStream: Writable, type: StructType<P>): Long =
            type.enfoldToStream(outStream)

        protected fun <P : StreamPackageable> saveObject(outStream: Writable, type: ObjectType<P>): Long =
            type.enfoldToStream(outStream)

        protected fun <P : Packageable> saveDict(outStream: Writable, type: DictType<P>): Long =
            type.enfoldToStream(outStream)

        protected fun <P : Packageable> saveList(outStream: Writable, type: ListType<P>): Long =
            type.enfoldToStream(outStream)
    }
}