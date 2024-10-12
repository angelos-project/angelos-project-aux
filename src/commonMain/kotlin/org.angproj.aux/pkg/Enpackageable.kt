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
import org.angproj.aux.io.Writable
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
    public fun enfold(outStream: Writable)
    public fun enfold(outData: Storable, offset: Int)

    public fun withEnfold(outStream: Writable, action: Writable.() -> Unit) { outStream.action() }
    public fun withEnfold(outData: Storable, action: Storable.() -> Unit) { outData.action() }

    public fun Writable.saveBlock(type: BlockType): Long = type.enfoldToStream(this)

    public fun Writable.saveByte(value: Byte): Long = ByteType(value).enfoldToStream(this)
    public fun Writable.saveShort(value: Short): Long = ShortType(value).enfoldToStream(this)
    public fun Writable.saveInt(value: Int): Long = IntType(value).enfoldToStream(this)
    public fun Writable.saveLong(value: Long): Long = LongType(value).enfoldToStream(this)
    public fun Writable.saveFloat(value: Float): Long = FloatType(value).enfoldToStream(this)
    public fun Writable.saveDouble(value: Double): Long = DoubleType(value).enfoldToStream(this)
    public fun Writable.saveUByte(value: UByte): Long = UByteType(value).enfoldToStream(this)
    public fun Writable.saveUShort(value: UShort): Long = UShortType(value).enfoldToStream(this)
    public fun Writable.saveUInt(value: UInt): Long = UIntType(value).enfoldToStream(this)
    public fun Writable.saveULong(value: ULong): Long = ULongType(value).enfoldToStream(this)


    public fun Writable.saveByteArray(value: ByteBuffer): Long = ByteArrayType(value).enfoldToStream(this)
    public fun Writable.saveShortArray(value: ShortBuffer): Long = ShortArrayType(value).enfoldToStream(this)
    public fun Writable.saveIntArray(value: IntBuffer): Long = IntArrayType(value).enfoldToStream(this)
    public fun Writable.saveLongArray(value: LongBuffer): Long = LongArrayType(value).enfoldToStream(this)
    public fun Writable.saveFloatArray(value: FloatBuffer): Long = FloatArrayType(value).enfoldToStream(this)
    public fun Writable.saveDoubleArray(value: DoubleBuffer): Long = DoubleArrayType(value).enfoldToStream(this)
    public fun Writable.saveUByteArray(value: UByteBuffer): Long = UByteArrayType(value).enfoldToStream(this)
    public fun Writable.saveUShortArray(value: UShortBuffer): Long = UShortArrayType(value).enfoldToStream(this)
    public fun Writable.saveUIntArray(value: UIntBuffer): Long = UIntArrayType(value).enfoldToStream(this)
    public fun Writable.saveULongArray(value: ULongBuffer): Long = ULongArrayType(value).enfoldToStream(this)


    public fun Writable.saveUuid4(value: Uuid4): Long = Uuid4Type(value).enfoldToStream(this)
    public fun Writable.saveString(value: Text): Long = StringType(value).enfoldToStream(this)
    public fun Writable.saveBigInt(value: BigInt): Long = BigIntType(value).enfoldToStream(this)

    public fun <P: Packageable>Writable.saveStruct(type: StructType<P>): Long =
        type.enfoldToStream(this)
    public fun <P: Packageable>Writable.saveObject(type: ObjectType<P>): Long =
        type.enfoldToStream(this)
    public fun <P: Packageable>Writable.saveDict(type: DictType<P>): Long =
        type.enfoldToStream(this)
    public fun <P: Packageable>Writable.saveList(type: ListType<P>): Long =
        type.enfoldToStream(this)


    public fun Storable.saveByte(outData: Storable, offset: Int, value: Byte): Long = ByteType(
        value).enfoldToBlock(outData, offset)
    public fun Storable.saveShort(outData: Storable, offset: Int, value: Short): Long = ShortType(
        value).enfoldToBlock(outData, offset)
    public fun Storable.saveInt(outData: Storable, offset: Int, value: Int): Long = IntType(
        value).enfoldToBlock(outData, offset)
    public fun Storable.saveLong(outData: Storable, offset: Int, value: Long): Long = LongType(
        value).enfoldToBlock(outData, offset)
    public fun Storable.saveFloat(outData: Storable, offset: Int, value: Float): Long = FloatType(
        value).enfoldToBlock(outData, offset)
    public fun Storable.saveDouble(outData: Storable, offset: Int, value: Double): Long = DoubleType(
        value).enfoldToBlock(outData, offset)
    public fun Storable.saveUByte(outData: Storable, offset: Int, value: UByte): Long = UByteType(
        value).enfoldToBlock(outData, offset)
    public fun Storable.saveUShort(outData: Storable, offset: Int, value: UShort): Long = UShortType(
        value).enfoldToBlock(outData, offset)
    public fun Storable.saveUInt(outData: Storable, offset: Int, value: UInt): Long = UIntType(
        value).enfoldToBlock(outData, offset)
    public fun Storable.saveULong(outData: Storable, offset: Int, value: ULong): Long = ULongType(
        value).enfoldToBlock(outData, offset)


    public fun Storable.saveByteArray(outData: Storable, offset: Int, value: ByteBuffer): Long = ByteArrayType(
        value).enfoldToBlock(outData, offset)
    public fun Storable.saveShortArray(outData: Storable, offset: Int, value: ShortBuffer): Long = ShortArrayType(
        value).enfoldToBlock(outData, offset)
    public fun Storable.saveIntArray(outData: Storable, offset: Int, value: IntBuffer): Long = IntArrayType(
        value).enfoldToBlock(outData, offset)
    public fun Storable.saveLongArray(outData: Storable, offset: Int, value: LongBuffer): Long = LongArrayType(
        value).enfoldToBlock(outData, offset)
    public fun Storable.saveFloatArray(outData: Storable, offset: Int, value: FloatBuffer): Long = FloatArrayType(
        value).enfoldToBlock(outData, offset)
    public fun Storable.saveDoubleArray(outData: Storable, offset: Int, value: DoubleBuffer): Long = DoubleArrayType(
        value).enfoldToBlock(outData, offset)
    public fun Storable.saveUByteArray(outData: Storable, offset: Int, value: UByteBuffer): Long =
        UByteArrayType(value).enfoldToBlock(outData, offset)
    public fun Storable.saveUShortArray(outData: Storable, offset: Int, value: UShortBuffer): Long =
        UShortArrayType(value).enfoldToBlock(outData, offset)
    public fun Storable.saveUIntArray(outData: Storable, offset: Int, value: UIntBuffer): Long =
        UIntArrayType(value).enfoldToBlock(outData, offset)
    public fun Storable.saveULongArray(outData: Storable, offset: Int, value: ULongBuffer): Long =
        ULongArrayType(value).enfoldToBlock(outData, offset)


    public fun Storable.saveUuid4(outData: Storable, offset: Int, value: Uuid4): Long =
        Uuid4Type(value).enfoldToBlock(outData, offset)


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