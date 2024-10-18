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

import org.angproj.aux.buf.*
import org.angproj.aux.io.Storable
import org.angproj.aux.io.Text
import org.angproj.aux.io.BinaryWritable
import org.angproj.aux.num.BigInt
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
import org.angproj.aux.util.Uuid4

public interface Enpackageable {
    public fun enfold(outStream: BinaryWritable)
    public fun enfold(outData: Storable, offset: Int): Long

    public fun withEnfold(outStream: BinaryWritable, action: BinaryWritable.() -> Unit) { outStream.action() }
    //public fun withEnfold(outData: Storable, action: Storable.() -> Unit) { outData.action() }
    public fun withEnfold(outData: Storable, offset: Int = 0, action: StorageIter.() -> Unit): Long {
        val storage = StorageIter(outData, offset.toLong())
        storage.action()
        return storage.index
    }

    public fun BinaryWritable.saveBlock(type: BlockType): Long = type.enfoldToStream(this)

    public fun BinaryWritable.saveByte(value: Byte): Long = ByteType(value).enfoldToStream(this)
    public fun BinaryWritable.saveShort(value: Short): Long = ShortType(value).enfoldToStream(this)
    public fun BinaryWritable.saveInt(value: Int): Long = IntType(value).enfoldToStream(this)
    public fun BinaryWritable.saveLong(value: Long): Long = LongType(value).enfoldToStream(this)
    public fun BinaryWritable.saveFloat(value: Float): Long = FloatType(value).enfoldToStream(this)
    public fun BinaryWritable.saveDouble(value: Double): Long = DoubleType(value).enfoldToStream(this)
    public fun BinaryWritable.saveUByte(value: UByte): Long = UByteType(value).enfoldToStream(this)
    public fun BinaryWritable.saveUShort(value: UShort): Long = UShortType(value).enfoldToStream(this)
    public fun BinaryWritable.saveUInt(value: UInt): Long = UIntType(value).enfoldToStream(this)
    public fun BinaryWritable.saveULong(value: ULong): Long = ULongType(value).enfoldToStream(this)


    public fun BinaryWritable.saveByteArray(value: ByteBuffer): Long = ByteArrayType(value).enfoldToStream(this)
    public fun BinaryWritable.saveShortArray(value: ShortBuffer): Long = ShortArrayType(value).enfoldToStream(this)
    public fun BinaryWritable.saveIntArray(value: IntBuffer): Long = IntArrayType(value).enfoldToStream(this)
    public fun BinaryWritable.saveLongArray(value: LongBuffer): Long = LongArrayType(value).enfoldToStream(this)
    public fun BinaryWritable.saveFloatArray(value: FloatBuffer): Long = FloatArrayType(value).enfoldToStream(this)
    public fun BinaryWritable.saveDoubleArray(value: DoubleBuffer): Long = DoubleArrayType(value).enfoldToStream(this)
    public fun BinaryWritable.saveUByteArray(value: UByteBuffer): Long = UByteArrayType(value).enfoldToStream(this)
    public fun BinaryWritable.saveUShortArray(value: UShortBuffer): Long = UShortArrayType(value).enfoldToStream(this)
    public fun BinaryWritable.saveUIntArray(value: UIntBuffer): Long = UIntArrayType(value).enfoldToStream(this)
    public fun BinaryWritable.saveULongArray(value: ULongBuffer): Long = ULongArrayType(value).enfoldToStream(this)


    public fun BinaryWritable.saveUuid4(value: Uuid4): Long = Uuid4Type(value).enfoldToStream(this)
    public fun BinaryWritable.saveString(value: Text): Long = StringType(value).enfoldToStream(this)
    public fun BinaryWritable.saveBigInt(value: BigInt): Long = BigIntType(value).enfoldToStream(this)

    public fun <P: Packageable>BinaryWritable.saveStruct(type: StructType<P>): Long =
        type.enfoldToStream(this)
    public fun <P: Packageable>BinaryWritable.saveObject(type: ObjectType<P>): Long =
        type.enfoldToStream(this)
    public fun <P: Packageable>BinaryWritable.saveDict(type: DictType<P>): Long =
        type.enfoldToStream(this)
    public fun <P: Packageable>BinaryWritable.saveList(type: ListType<P>): Long =
        type.enfoldToStream(this)


    public class StorageIter(public val storage: Storable, public var index: Long = 0)

    public fun StorageIter.saveByte(value: Byte): Long = ByteType(
        value).enfoldToBlock(storage, index.toInt()).also { index += it }
    public fun StorageIter.saveShort(value: Short): Long = ShortType(
        value).enfoldToBlock(storage, index.toInt()).also { index += it }
    public fun StorageIter.saveInt(value: Int): Long = IntType(
        value).enfoldToBlock(storage, index.toInt()).also { index += it }
    public fun StorageIter.saveLong(value: Long): Long = LongType(
        value).enfoldToBlock(storage, index.toInt()).also { index += it }
    public fun StorageIter.saveFloat(value: Float): Long = FloatType(
        value).enfoldToBlock(storage, index.toInt()).also { index += it }
    public fun StorageIter.saveDouble(value: Double): Long = DoubleType(
        value).enfoldToBlock(storage, index.toInt()).also { index += it }
    public fun StorageIter.saveUByte(value: UByte): Long = UByteType(
        value).enfoldToBlock(storage, index.toInt()).also { index += it }
    public fun StorageIter.saveUShort(value: UShort): Long = UShortType(
        value).enfoldToBlock(storage, index.toInt()).also { index += it }
    public fun StorageIter.saveUInt(value: UInt): Long = UIntType(
        value).enfoldToBlock(storage, index.toInt()).also { index += it }
    public fun StorageIter.saveULong(value: ULong): Long = ULongType(
        value).enfoldToBlock(storage, index.toInt()).also { index += it }


    public fun StorageIter.saveByteArray(value: ByteBuffer): Long = ByteArrayType(
        value).enfoldToBlock(storage, index.toInt()).also { index += it }
    public fun StorageIter.saveShortArray(value: ShortBuffer): Long = ShortArrayType(
        value).enfoldToBlock(storage, index.toInt()).also { index += it }
    public fun StorageIter.saveIntArray(value: IntBuffer): Long = IntArrayType(
        value).enfoldToBlock(storage, index.toInt()).also { index += it }
    public fun StorageIter.saveLongArray(value: LongBuffer): Long = LongArrayType(
        value).enfoldToBlock(storage, index.toInt()).also { index += it }
    public fun StorageIter.saveFloatArray(value: FloatBuffer): Long = FloatArrayType(
        value).enfoldToBlock(storage, index.toInt()).also { index += it }
    public fun StorageIter.saveDoubleArray(value: DoubleBuffer): Long = DoubleArrayType(
        value).enfoldToBlock(storage, index.toInt()).also { index += it }
    public fun StorageIter.saveUByteArray(value: UByteBuffer): Long =
        UByteArrayType(value).enfoldToBlock(storage, index.toInt()).also { index += it }
    public fun StorageIter.saveUShortArray(value: UShortBuffer): Long =
        UShortArrayType(value).enfoldToBlock(storage, index.toInt()).also { index += it }
    public fun StorageIter.saveUIntArray(value: UIntBuffer): Long =
        UIntArrayType(value).enfoldToBlock(storage, index.toInt()).also { index += it }
    public fun StorageIter.saveULongArray(value: ULongBuffer): Long =
        ULongArrayType(value).enfoldToBlock(storage, index.toInt()).also { index += it }

    public fun StorageIter.saveUuid4(value: Uuid4): Long = Uuid4Type(
        value).enfoldToBlock(storage, index.toInt()).also { index += it }



    /*public companion object {
        protected fun saveByte(outData: Storable, offset: Int, type: ByteType): Long =
            type.enfoldToBlock(outData, offset)

        protected fun saveShort(outData: Storable, offset: Int, type: ShortType): Long =
            type.enfoldToBlock(outData, offset)

        protected fun saveInt(outData: Storable, offset: Int, type: IntType): Long = type.enfoldToBlock(outData, offset)
        protected fun saveLong(outData: Storable, offset: Int, type: LongType): Long =
            type.enfoldToBlock(outData, offset)

        protected fun saveFloat(outData: Storable, offset: Int, type: FloatType): Long =
            type.enfoldToBlock(outData, offset)

        protected fun saveDouble(outData: Storable, offset: Int, type: DoubleType): Long =
            type.enfoldToBlock(outData, offset)

        protected fun saveUByte(outData: Storable, offset: Int, type: UByteType): Long =
            type.enfoldToBlock(outData, offset)

        protected fun saveUShort(outData: Storable, offset: Int, type: UShortType): Long =
            type.enfoldToBlock(outData, offset)

        protected fun saveUInt(outData: Storable, offset: Int, type: UIntType): Long =
            type.enfoldToBlock(outData, offset)

        protected fun saveULong(outData: Storable, offset: Int, type: ULongType): Long =
            type.enfoldToBlock(outData, offset)

        protected fun saveByteArray(outData: Storable, offset: Int, type: ByteArrayType): Long =
            type.enfoldToBlock(outData, offset)

        protected fun saveShortArray(outData: Storable, offset: Int, type: ShortArrayType): Long =
            type.enfoldToBlock(outData, offset)

        protected fun saveIntArray(outData: Storable, offset: Int, type: IntArrayType): Long =
            type.enfoldToBlock(outData, offset)

        protected fun saveLongArray(outData: Storable, offset: Int, type: LongArrayType): Long =
            type.enfoldToBlock(outData, offset)

        protected fun saveFloatArray(outData: Storable, offset: Int, type: FloatArrayType): Long =
            type.enfoldToBlock(outData, offset)

        protected fun saveDoubleArray(outData: Storable, offset: Int, type: DoubleArrayType): Long =
            type.enfoldToBlock(outData, offset)

        protected fun saveUByteArray(outData: Storable, offset: Int, type: UByteArrayType): Long =
            type.enfoldToBlock(outData, offset)

        protected fun saveUShortArray(outData: Storable, offset: Int, type: UShortArrayType): Long =
            type.enfoldToBlock(outData, offset)

        protected fun saveUIntArray(outData: Storable, offset: Int, type: UIntArrayType): Long =
            type.enfoldToBlock(outData, offset)

        protected fun saveULongArray(outData: Storable, offset: Int, type: ULongArrayType): Long =
            type.enfoldToBlock(outData, offset)

        protected fun saveUuid4(outData: Storable, offset: Int, type: Uuid4Type): Long =
            type.enfoldToBlock(outData, offset)
    }*/
}