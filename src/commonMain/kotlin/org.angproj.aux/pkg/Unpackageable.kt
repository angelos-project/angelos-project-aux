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
import org.angproj.aux.io.BinaryReadable
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
    public fun unfold(inStream: BinaryReadable)
    public fun unfold(inData: Retrievable, offset: Int = 0): Long

    public fun withUnfold(inStream: BinaryReadable, action: BinaryReadable.() -> Unit) : Unit = inStream.action()
    //public fun withUnfold(inData: Retrievable, action: Retrievable.() -> Unit) : Unit = inData.action()
    public fun withUnfold(inData: Retrievable, offset: Int = 0, action: RetrieveIter.() -> Unit): Long {
        val retrieve = RetrieveIter(inData, offset.toLong())
        retrieve.action()
        return retrieve.index
    }

    public fun BinaryReadable.loadBlock(): BlockType = BlockType.unfoldFromStream(this)

    public fun BinaryReadable.loadByte(): Byte = ByteType.unfoldFromStream(this).value
    public fun BinaryReadable.loadShort(): Short = ShortType.unfoldFromStream(this).value
    public fun BinaryReadable.loadInt(): Int = IntType.unfoldFromStream(this).value
    public fun BinaryReadable.loadLong(): Long = LongType.unfoldFromStream(this).value
    public fun BinaryReadable.loadFloat(): Float = FloatType.unfoldFromStream(this).value
    public fun BinaryReadable.loadDouble(): Double = DoubleType.unfoldFromStream(this).value
    public fun BinaryReadable.loadUByte(): UByte = UByteType.unfoldFromStream(this).value
    public fun BinaryReadable.loadUShort(): UShort = UShortType.unfoldFromStream(this).value
    public fun BinaryReadable.loadUInt(): UInt = UIntType.unfoldFromStream(this).value
    public fun BinaryReadable.loadULong(): ULong = ULongType.unfoldFromStream(this).value


    public fun BinaryReadable.loadByteArray(): ByteBuffer = ByteArrayType.unfoldFromStream(this).value
    public fun BinaryReadable.loadShortArray(): ShortBuffer = ShortArrayType.unfoldFromStream(this).value
    public fun BinaryReadable.loadIntArray(): IntBuffer = IntArrayType.unfoldFromStream(this).value
    public fun BinaryReadable.loadLongArray(): LongBuffer = LongArrayType.unfoldFromStream(this).value
    public fun BinaryReadable.loadFloatArray(): FloatBuffer = FloatArrayType.unfoldFromStream(this).value
    public fun BinaryReadable.loadDoubleArray(): DoubleBuffer = DoubleArrayType.unfoldFromStream(this).value
    public fun BinaryReadable.loadUByteArray(): UByteBuffer = UByteArrayType.unfoldFromStream(this).value
    public fun BinaryReadable.loadUShortArray(): UShortBuffer = UShortArrayType.unfoldFromStream(this).value
    public fun BinaryReadable.loadUIntArray(): UIntBuffer = UIntArrayType.unfoldFromStream(this).value
    public fun BinaryReadable.loadULongArray(): ULongBuffer = ULongArrayType.unfoldFromStream(this).value


    public fun BinaryReadable.loadUuid4(): Uuid4 = Uuid4Type.unfoldFromStream(this).value
    public fun BinaryReadable.loadString(): Text = StringType.unfoldFromStream(this).value
    public fun BinaryReadable.loadBigInt(): BigInt = BigIntType.unfoldFromStream(this).value

    public fun BinaryReadable.loadStruct(): StructType<*> = StructType.unfoldStream(this)
    public fun BinaryReadable.loadObject(): ObjectType<*> = ObjectType.unfoldStream(this)
    public fun BinaryReadable.loadDict(): DictType<*> = DictType.unfoldStream(this)
    public fun BinaryReadable.loadList(): ListType<*> = ListType.unfoldStream(this)


    /*public fun Retrievable.loadByte(
        offset: Int): Byte = ByteType.unfoldFromBlock(this, offset).value
    public fun Retrievable.loadShort(
        offset: Int): Short = ShortType.unfoldFromBlock(this, offset).value
    public fun Retrievable.loadInt(
        offset: Int): Int = IntType.unfoldFromBlock(this, offset).value
    public fun Retrievable.loadLong(
        offset: Int): Long = LongType.unfoldFromBlock(this, offset).value
    public fun Retrievable.loadFloat(
        offset: Int): Float = FloatType.unfoldFromBlock(this, offset).value
    public fun Retrievable.loadDouble(
        offset: Int): Double = DoubleType.unfoldFromBlock(this, offset).value
    public fun Retrievable.loadUByte(
        offset: Int): UByte = UByteType.unfoldFromBlock(this, offset).value
    public fun Retrievable.loadUShort(
        offset: Int): UShort = UShortType.unfoldFromBlock(this, offset).value
    public fun Retrievable.loadUInt(
        offset: Int): UInt = UIntType.unfoldFromBlock(this, offset).value
    public fun Retrievable.loadULong(
        offset: Int): ULong = ULongType.unfoldFromBlock(this, offset).value


    public fun Retrievable.loadByteArray(
        offset: Int, count: Int
    ): ByteBuffer = ByteArrayType.unfoldFromBlock(this, offset, count).value
    public fun Retrievable.loadShortArray(
        offset: Int, count: Int
    ): ShortBuffer = ShortArrayType.unfoldFromBlock(this, offset, count).value
    public fun Retrievable.loadIntArray(
        offset: Int, count: Int
    ): IntBuffer = IntArrayType.unfoldFromBlock(this, offset, count).value
    public fun Retrievable.loadLongArray(
        offset: Int, count: Int
    ): LongBuffer = LongArrayType.unfoldFromBlock(this, offset, count).value
    public fun Retrievable.loadFloatArray(
        offset: Int, count: Int
    ): FloatBuffer = FloatArrayType.unfoldFromBlock(this, offset, count).value
    public fun Retrievable.loadDoubleArray(
        offset: Int, count: Int
    ): DoubleBuffer = DoubleArrayType.unfoldFromBlock(this, offset, count).value
    public fun Retrievable.loadUByteArray(
        offset: Int, count: Int
    ): UByteBuffer = UByteArrayType.unfoldFromBlock(this, offset, count).value
    public fun Retrievable.loadUShortArray(
        offset: Int, count: Int
    ): UShortBuffer = UShortArrayType.unfoldFromBlock(this, offset, count).value
    public fun Retrievable.loadUIntArray(
        offset: Int, count: Int
    ): UIntBuffer = UIntArrayType.unfoldFromBlock(this, offset, count).value
    public fun Retrievable.loadULongArray(
        offset: Int, count: Int
    ): ULongBuffer = ULongArrayType.unfoldFromBlock(this, offset, count).value


    public fun Retrievable.loadUuid4(offset: Int): Uuid4 = Uuid4Type.unfoldFromBlock(this, offset).value*/


    public class RetrieveIter(public val retrieve: Retrievable, public var index: Long = 0)


    public fun RetrieveIter.loadByte(): Byte = ByteType.unfoldFromBlock(
        retrieve, index.toInt()).value.also { index += ByteType.atomicSize }
    public fun RetrieveIter.loadShort(): Short = ShortType.unfoldFromBlock(
        retrieve, index.toInt()).value.also { index += ShortType.atomicSize }
    public fun RetrieveIter.loadInt(): Int = IntType.unfoldFromBlock(
        retrieve, index.toInt()).value.also { index += IntType.atomicSize }
    public fun RetrieveIter.loadLong(): Long = LongType.unfoldFromBlock(
        retrieve, index.toInt()).value.also { index += LongType.atomicSize }
    public fun RetrieveIter.loadFloat(): Float = FloatType.unfoldFromBlock(
        retrieve, index.toInt()).value.also { index += FloatType.atomicSize }
    public fun RetrieveIter.loadDouble(): Double = DoubleType.unfoldFromBlock(
        retrieve, index.toInt()).value.also { index += DoubleType.atomicSize }
    public fun RetrieveIter.loadUByte(): UByte = UByteType.unfoldFromBlock(
        retrieve, index.toInt()).value.also { index += UByteType.atomicSize }
    public fun RetrieveIter.loadUShort(): UShort = UShortType.unfoldFromBlock(
        retrieve, index.toInt()).value.also { index += UShortType.atomicSize }
    public fun RetrieveIter.loadUInt(): UInt = UIntType.unfoldFromBlock(
        retrieve, index.toInt()).value.also { index += UIntType.atomicSize }
    public fun RetrieveIter.loadULong(): ULong = ULongType.unfoldFromBlock(
        retrieve, index.toInt()).value.also { index += ULongType.atomicSize }


    public fun RetrieveIter.loadByteArray(
        value: ByteBuffer
    ): Long = ByteArrayType.unfoldFromBlock(retrieve, index.toInt(), value).also { index += it }
    public fun RetrieveIter.loadShortArray(
        value: ShortBuffer
    ): Long = ShortArrayType.unfoldFromBlock(retrieve, index.toInt(), value).also { index += it }
    public fun RetrieveIter.loadIntArray(
        value: IntBuffer
    ): Long = IntArrayType.unfoldFromBlock(retrieve, index.toInt(), value).also { index += it }
    public fun RetrieveIter.loadLongArray(
        value: LongBuffer
    ): Long = LongArrayType.unfoldFromBlock(retrieve, index.toInt(), value).also { index += it }
    public fun RetrieveIter.loadFloatArray(
        value: FloatBuffer
    ): Long = FloatArrayType.unfoldFromBlock(retrieve, index.toInt(), value).also { index += it }
    public fun RetrieveIter.loadDoubleArray(
        value: DoubleBuffer
    ): Long = DoubleArrayType.unfoldFromBlock(retrieve, index.toInt(), value).also { index += it }
    public fun RetrieveIter.loadUByteArray(
        value: UByteBuffer
    ): Long = UByteArrayType.unfoldFromBlock(retrieve, index.toInt(), value).also { index += it }
    public fun RetrieveIter.loadUShortArray(
        value: UShortBuffer
    ): Long = UShortArrayType.unfoldFromBlock(retrieve, index.toInt(), value).also { index += it }
    public fun RetrieveIter.loadUIntArray(
        value: UIntBuffer
    ): Long = UIntArrayType.unfoldFromBlock(retrieve, index.toInt(), value).also { index += it }
    public fun RetrieveIter.loadULongArray(
        value: ULongBuffer
    ): Long = ULongArrayType.unfoldFromBlock(retrieve, index.toInt(), value).also { index += it }


    public fun RetrieveIter.loadUuid4(value: Uuid4): Long = Uuid4Type.unfoldFromBlock(
        retrieve, index.toInt(), value).also { index += it }

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