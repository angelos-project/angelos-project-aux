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
import org.angproj.aux.io.*
import org.angproj.aux.num.BigInt
import org.angproj.aux.pkg.arb.BigIntType
import org.angproj.aux.pkg.arb.StringType
import org.angproj.aux.pkg.arb.StructType
import org.angproj.aux.pkg.arb.Uuid4Type
import org.angproj.aux.pkg.coll.ArrayType
import org.angproj.aux.pkg.coll.DictType
import org.angproj.aux.pkg.coll.ListType
import org.angproj.aux.pkg.coll.ObjectType
import org.angproj.aux.pkg.mem.*
import org.angproj.aux.pkg.prime.*
import org.angproj.aux.pkg.type.BlockType
import org.angproj.aux.util.EndianAware
import org.angproj.aux.util.Uuid4


public interface Packageable : Enpackageable, Unpackageable, EndianAware, Comparable<Packageable> {

    override fun enfold(outStream: BinaryWritable) { TODO("Not yet implemented") }
    override fun enfold(outData: Storable, offset: Int): Int { TODO("Not yet implemented") }
    override fun unfold(inStream: BinaryReadable) { TODO("Not yet implemented") }
    override fun unfold(inData: Retrievable, offset: Int): Int { TODO("Not yet implemented") }

    public fun foldSize(foldFormat: FoldFormat): Int

    public fun withFoldSize(foldFormat: FoldFormat, action: FoldFormat.() -> Int): Int = foldFormat.action()


    public fun FoldFormat.sizeOf(type: BlockType): Int = type.foldSize(this)

    public fun FoldFormat.sizeOf(value: Byte): Int = ByteType.atomicSize
    public fun FoldFormat.sizeOf(value: Short): Int = ShortType.atomicSize
    public fun FoldFormat.sizeOf(value: Int): Int = IntType.atomicSize
    public fun FoldFormat.sizeOf(value: Long): Int = LongType.atomicSize
    public fun FoldFormat.sizeOf(value: Float): Int = FloatType.atomicSize
    public fun FoldFormat.sizeOf(value: Double): Int = DoubleType.atomicSize
    public fun FoldFormat.sizeOf(value: UByte): Int = UByteType.atomicSize
    public fun FoldFormat.sizeOf(value: UShort): Int = UShortType.atomicSize
    public fun FoldFormat.sizeOf(value: UInt): Int = UIntType.atomicSize
    public fun FoldFormat.sizeOf(value: ULong): Int = ULongType.atomicSize


    public fun FoldFormat.sizeOf(value: ByteBuffer): Int = ByteArrayType(value).foldSize(this)
    public fun FoldFormat.sizeOf(value: ShortBuffer): Int = ShortArrayType(value).foldSize(this)
    public fun FoldFormat.sizeOf(value: IntBuffer): Int = IntArrayType(value).foldSize(this)
    public fun FoldFormat.sizeOf(value: LongBuffer): Int = LongArrayType(value).foldSize(this)
    public fun FoldFormat.sizeOf(value: FloatBuffer): Int = FloatArrayType(value).foldSize(this)
    public fun FoldFormat.sizeOf(value: DoubleBuffer): Int = DoubleArrayType(value).foldSize(this)
    public fun FoldFormat.sizeOf(value: UByteBuffer): Int = UByteArrayType(value).foldSize(this)
    public fun FoldFormat.sizeOf(value: UShortBuffer): Int = UShortArrayType(value).foldSize(this)
    public fun FoldFormat.sizeOf(value: UIntBuffer): Int = UIntArrayType(value).foldSize(this)
    public fun FoldFormat.sizeOf(value: ULongBuffer): Int = ULongArrayType(value).foldSize(this)


    public fun FoldFormat.sizeOf(value: Uuid4): Int = Uuid4Type(value).foldSize(this)
    public fun FoldFormat.sizeOf(value: Text): Int = StringType(value).foldSize(this)
    public fun FoldFormat.sizeOf(value: BigInt): Int = BigIntType(value).foldSize(this)

    public fun <P: Packageable>FoldFormat.sizeOf(type: StructType<P>): Int = type.foldSize(this)
    public fun <P: Packageable>FoldFormat.sizeOf(type: ObjectType<P>): Int = type.foldSize(this)
    public fun <P: Packageable>FoldFormat.sizeOf(type: MutableMap<Text, P>): Int = DictType(type).foldSize(this)
    public fun <P: Packageable>FoldFormat.sizeOf(type: MutableList<P>): Int = ListType(type).foldSize(this)
    public fun <P: Packageable>FoldFormat.sizeOf(type: Array<P>): Int = ArrayType(type).foldSize(this)


    public fun foldFormat(): FoldFormat

    public override operator fun compareTo(other: Packageable): Int { return hashCode() - other.hashCode() }
}

