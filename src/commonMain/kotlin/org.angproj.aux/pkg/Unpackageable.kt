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
import org.angproj.aux.io.Readable
import org.angproj.aux.io.Retrievable
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

public interface Unpackageable {
    public fun unfold(inStream: Readable)
    public fun unfold(inData: Retrievable)

    public fun withUnfold(inStream: Readable, action: Readable.() -> Unit) : Unit = inStream.action()
    public fun withUnfold(inData: Retrievable, action: Retrievable.() -> Unit) : Unit = inData.action()

    public fun Readable.loadBlock(): BlockType = BlockType.unfoldFromStream(this)

    public fun Readable.loadByte(): Byte = ByteType.unfoldFromStream(this).value
    public fun Readable.loadShort(): Short = ShortType.unfoldFromStream(this).value
    public fun Readable.loadInt(): Int = IntType.unfoldFromStream(this).value
    public fun Readable.loadLong(): Long = LongType.unfoldFromStream(this).value
    public fun Readable.loadFloat(): Float = FloatType.unfoldFromStream(this).value
    public fun Readable.loadDouble(): Double = DoubleType.unfoldFromStream(this).value
    public fun Readable.loadUByte(): UByte = UByteType.unfoldFromStream(this).value
    public fun Readable.loadUShort(): UShort = UShortType.unfoldFromStream(this).value
    public fun Readable.loadUInt(): UInt = UIntType.unfoldFromStream(this).value
    public fun Readable.loadULong(): ULong = ULongType.unfoldFromStream(this).value


    public fun Readable.loadByteArray(): ByteBuffer = ByteArrayType.unfoldFromStream(this).value
    public fun Readable.loadShortArray(): ShortBuffer = ShortArrayType.unfoldFromStream(this).value
    public fun Readable.loadIntArray(): IntBuffer = IntArrayType.unfoldFromStream(this).value
    public fun Readable.loadLongArray(): LongBuffer = LongArrayType.unfoldFromStream(this).value
    public fun Readable.loadFloatArray(): FloatBuffer = FloatArrayType.unfoldFromStream(this).value
    public fun Readable.loadDoubleArray(): DoubleBuffer = DoubleArrayType.unfoldFromStream(this).value
    public fun Readable.loadUByteArray(): UByteBuffer = UByteArrayType.unfoldFromStream(this).value
    public fun Readable.loadUShortArray(): UShortBuffer = UShortArrayType.unfoldFromStream(this).value
    public fun Readable.loadUIntArray(): UIntBuffer = UIntArrayType.unfoldFromStream(this).value
    public fun Readable.loadULongArray(): ULongBuffer = ULongArrayType.unfoldFromStream(this).value


    public fun Readable.loadUuid4(): Uuid4 = Uuid4Type.unfoldFromStream(this).value
    public fun Readable.loadString(): Text = StringType.unfoldFromStream(this).value
    public fun Readable.loadBigInt(): BigInt = BigIntType.unfoldFromStream(this).value

    public fun Readable.loadStruct(): StructType<*> = StructType.unfoldStream(this)
    public fun Readable.loadObject(): ObjectType<*> = ObjectType.unfoldStream(this)
    public fun Readable.loadDict(): DictType<*> = DictType.unfoldStream(this)
    public fun Readable.loadList(): ListType<*> = ListType.unfoldStream(this)


    public fun Retrievable.loadByte(
        inData: Retrievable, offset: Int): Byte = ByteType.unfoldFromBlock(inData, offset).value
    public fun Retrievable.loadShort(
        inData: Retrievable, offset: Int): Short = ShortType.unfoldFromBlock(inData, offset).value
    public fun Retrievable.loadInt(
        inData: Retrievable, offset: Int): Int = IntType.unfoldFromBlock(inData, offset).value
    public fun Retrievable.loadLong(
        inData: Retrievable, offset: Int): Long = LongType.unfoldFromBlock(inData, offset).value
    public fun Retrievable.loadFloat(
        inData: Retrievable, offset: Int): Float = FloatType.unfoldFromBlock(inData, offset).value
    public fun Retrievable.loadDouble(
        inData: Retrievable, offset: Int): Double = DoubleType.unfoldFromBlock(inData, offset).value
    public fun Retrievable.loadUByte(
        inData: Retrievable, offset: Int): UByte = UByteType.unfoldFromBlock(inData, offset).value
    public fun Retrievable.loadUShort(
        inData: Retrievable, offset: Int): UShort = UShortType.unfoldFromBlock(inData, offset).value
    public fun Retrievable.loadUInt(
        inData: Retrievable, offset: Int): UInt = UIntType.unfoldFromBlock(inData, offset).value
    public fun Retrievable.loadULong(
        inData: Retrievable, offset: Int): ULong = ULongType.unfoldFromBlock(inData, offset).value


    public fun Retrievable.loadByteArray(
        inData: Retrievable, offset: Int, count: Int
    ): ByteBuffer = ByteArrayType.unfoldFromBlock(inData, offset, count).value
    public fun Retrievable.loadShortArray(
        inData: Retrievable, offset: Int, count: Int
    ): ShortBuffer = ShortArrayType.unfoldFromBlock(inData, offset, count).value
    public fun Retrievable.loadIntArray(inData: Retrievable, offset: Int, count: Int): IntArrayType =
        IntArrayType.unfoldFromBlock(inData, offset, count)
    public fun Retrievable.loadLongArray(
        inData: Retrievable, offset: Int, count: Int
    ): LongBuffer = LongArrayType.unfoldFromBlock(inData, offset, count).value
    public fun Retrievable.loadFloatArray(
        inData: Retrievable, offset: Int, count: Int
    ): FloatBuffer = FloatArrayType.unfoldFromBlock(inData, offset, count).value
    public fun Retrievable.loadDoubleArray(
        inData: Retrievable, offset: Int, count: Int
    ): DoubleBuffer = DoubleArrayType.unfoldFromBlock(inData, offset, count).value
    public fun Retrievable.loadUByteArray(
        inData: Retrievable, offset: Int, count: Int
    ): UByteBuffer = UByteArrayType.unfoldFromBlock(inData, offset, count).value
    public fun Retrievable.loadUShortArray(
        inData: Retrievable, offset: Int, count: Int
    ): UShortBuffer = UShortArrayType.unfoldFromBlock(inData, offset, count).value
    public fun Retrievable.loadUIntArray(
        inData: Retrievable, offset: Int, count: Int
    ): UIntBuffer = UIntArrayType.unfoldFromBlock(inData, offset, count).value
    public fun Retrievable.loadULongArray(
        inData: Retrievable, offset: Int, count: Int
    ): ULongBuffer = ULongArrayType.unfoldFromBlock(inData, offset, count).value


    public fun Retrievable.loadUuid4(inData: Retrievable, offset: Int): Uuid4Type = Uuid4Type.unfoldFromBlock(inData, offset)

    /*public companion object {
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

        protected fun loadByteArray(inData: Retrievable, offset: Int, count: Int): ByteArrayType =
            ByteArrayType.unfoldFromBlock(inData, offset, count)

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

        protected fun loadUByteArray(inData: Retrievable, offset: Int, count: Int): UByteArrayType =
            UByteArrayType.unfoldFromBlock(inData, offset, count)

        protected fun loadUShortArray(inData: Retrievable, offset: Int, count: Int): UShortArrayType =
            UShortArrayType.unfoldFromBlock(inData, offset, count)

        protected fun loadUIntArray(inData: Retrievable, offset: Int, count: Int): UIntArrayType =
            UIntArrayType.unfoldFromBlock(inData, offset, count)

        protected fun loadULongArray(inData: Retrievable, offset: Int, count: Int): ULongArrayType =
            ULongArrayType.unfoldFromBlock(inData, offset, count)

        protected fun loadUuid4(inData: Retrievable, offset: Int): Uuid4Type = Uuid4Type.unfoldFromBlock(inData, offset)
    }*/
}