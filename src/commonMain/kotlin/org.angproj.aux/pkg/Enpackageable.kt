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
import org.angproj.aux.io.BinaryWritable
import org.angproj.aux.io.Storable
import org.angproj.aux.io.Text
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
    public fun enfold(outData: Storable, offset: Int): Int

    public fun withEnfold(outStream: BinaryWritable, action: BinaryWritable.() -> Unit) { outStream.action() }
    //public fun withEnfold(outData: Storable, action: Storable.() -> Unit) { outData.action() }
    public fun withEnfold(outData: Storable, offset: Int = 0, action: StorageIter.() -> Unit): Int {
        val storage = StorageIter(outData, offset)
        storage.action()
        return storage.index
    }

    public fun BinaryWritable.saveBlock(type: BlockType): Int = type.enfoldToStream(this)

    public fun BinaryWritable.saveByte(value: Byte): Int = ByteType(value).enfoldToStream(this)
    public fun BinaryWritable.saveShort(value: Short): Int = ShortType(value).enfoldToStream(this)
    public fun BinaryWritable.saveInt(value: Int): Int = IntType(value).enfoldToStream(this)
    public fun BinaryWritable.saveLong(value: Long): Int = LongType(value).enfoldToStream(this)
    public fun BinaryWritable.saveFloat(value: Float): Int = FloatType(value).enfoldToStream(this)
    public fun BinaryWritable.saveDouble(value: Double): Int = DoubleType(value).enfoldToStream(this)
    public fun BinaryWritable.saveUByte(value: UByte): Int = UByteType(value).enfoldToStream(this)
    public fun BinaryWritable.saveUShort(value: UShort): Int = UShortType(value).enfoldToStream(this)
    public fun BinaryWritable.saveUInt(value: UInt): Int = UIntType(value).enfoldToStream(this)
    public fun BinaryWritable.saveULong(value: ULong): Int = ULongType(value).enfoldToStream(this)


    public fun BinaryWritable.saveByteArray(value: ByteBuffer): Int = ByteArrayType(value).enfoldToStream(this)
    public fun BinaryWritable.saveShortArray(value: ShortBuffer): Int = ShortArrayType(value).enfoldToStream(this)
    public fun BinaryWritable.saveIntArray(value: IntBuffer): Int = IntArrayType(value).enfoldToStream(this)
    public fun BinaryWritable.saveLongArray(value: LongBuffer): Int = LongArrayType(value).enfoldToStream(this)
    public fun BinaryWritable.saveFloatArray(value: FloatBuffer): Int = FloatArrayType(value).enfoldToStream(this)
    public fun BinaryWritable.saveDoubleArray(value: DoubleBuffer): Int = DoubleArrayType(value).enfoldToStream(this)
    public fun BinaryWritable.saveUByteArray(value: UByteBuffer): Int = UByteArrayType(value).enfoldToStream(this)
    public fun BinaryWritable.saveUShortArray(value: UShortBuffer): Int = UShortArrayType(value).enfoldToStream(this)
    public fun BinaryWritable.saveUIntArray(value: UIntBuffer): Int = UIntArrayType(value).enfoldToStream(this)
    public fun BinaryWritable.saveULongArray(value: ULongBuffer): Int = ULongArrayType(value).enfoldToStream(this)


    public fun BinaryWritable.saveUuid4(value: Uuid4): Int = Uuid4Type(value).enfoldToStream(this)
    public fun BinaryWritable.saveString(value: Text): Int = StringType(value).enfoldToStream(this)
    public fun BinaryWritable.saveBigInt(value: BigInt): Int = BigIntType(value).enfoldToStream(this)

    public fun <P: Packageable>BinaryWritable.saveStruct(type: StructType<P>): Int =
        type.enfoldToStream(this)
    public fun <P: Packageable>BinaryWritable.saveObject(type: ObjectType<P>): Int =
        type.enfoldToStream(this)
    public fun <P: Packageable>BinaryWritable.saveDict(type: DictType<P>): Int =
        type.enfoldToStream(this)
    public fun <P: Packageable>BinaryWritable.saveList(type: ListType<P>): Int =
        type.enfoldToStream(this)


    public class StorageIter(public val storage: Storable, public var index: Int = 0)

    public fun StorageIter.saveByte(value: Byte): Int = ByteType(
        value).enfoldToBlock(storage, index).also { index += it }
    public fun StorageIter.saveShort(value: Short): Int = ShortType(
        value).enfoldToBlock(storage, index).also { index += it }
    public fun StorageIter.saveInt(value: Int): Int = IntType(
        value).enfoldToBlock(storage, index).also { index += it }
    public fun StorageIter.saveLong(value: Long): Int = LongType(
        value).enfoldToBlock(storage, index).also { index += it }
    public fun StorageIter.saveFloat(value: Float): Int = FloatType(
        value).enfoldToBlock(storage, index).also { index += it }
    public fun StorageIter.saveDouble(value: Double): Int = DoubleType(
        value).enfoldToBlock(storage, index).also { index += it }
    public fun StorageIter.saveUByte(value: UByte): Int = UByteType(
        value).enfoldToBlock(storage, index).also { index += it }
    public fun StorageIter.saveUShort(value: UShort): Int = UShortType(
        value).enfoldToBlock(storage, index).also { index += it }
    public fun StorageIter.saveUInt(value: UInt): Int = UIntType(
        value).enfoldToBlock(storage, index).also { index += it }
    public fun StorageIter.saveULong(value: ULong): Int = ULongType(
        value).enfoldToBlock(storage, index).also { index += it }


    public fun StorageIter.saveByteArray(value: ByteBuffer): Int = ByteArrayType(
        value).enfoldToBlock(storage, index).also { index += it }
    public fun StorageIter.saveShortArray(value: ShortBuffer): Int = ShortArrayType(
        value).enfoldToBlock(storage, index).also { index += it }
    public fun StorageIter.saveIntArray(value: IntBuffer): Int = IntArrayType(
        value).enfoldToBlock(storage, index).also { index += it }
    public fun StorageIter.saveLongArray(value: LongBuffer): Int = LongArrayType(
        value).enfoldToBlock(storage, index).also { index += it }
    public fun StorageIter.saveFloatArray(value: FloatBuffer): Int = FloatArrayType(
        value).enfoldToBlock(storage, index).also { index += it }
    public fun StorageIter.saveDoubleArray(value: DoubleBuffer): Int = DoubleArrayType(
        value).enfoldToBlock(storage, index).also { index += it }
    public fun StorageIter.saveUByteArray(value: UByteBuffer): Int =
        UByteArrayType(value).enfoldToBlock(storage, index).also { index += it }
    public fun StorageIter.saveUShortArray(value: UShortBuffer): Int =
        UShortArrayType(value).enfoldToBlock(storage, index).also { index += it }
    public fun StorageIter.saveUIntArray(value: UIntBuffer): Int =
        UIntArrayType(value).enfoldToBlock(storage, index).also { index += it }
    public fun StorageIter.saveULongArray(value: ULongBuffer): Int =
        ULongArrayType(value).enfoldToBlock(storage, index).also { index += it }

    public fun StorageIter.saveUuid4(value: Uuid4): Int = Uuid4Type(
        value).enfoldToBlock(storage, index).also { index += it }



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