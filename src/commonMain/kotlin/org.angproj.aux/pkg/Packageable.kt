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
import org.angproj.aux.pkg.coll.DictType
import org.angproj.aux.pkg.coll.ListType
import org.angproj.aux.pkg.coll.ObjectType
import org.angproj.aux.pkg.mem.*
import org.angproj.aux.pkg.prime.*
import org.angproj.aux.pkg.type.BlockType
import org.angproj.aux.util.EndianAware
import org.angproj.aux.util.Uuid4


public interface Packageable : Enpackageable, Unpackageable, EndianAware {

    override fun enfold(outStream: Writable) { TODO("Not yet implemented") }
    override fun enfold(outData: Storable, offset: Int) { TODO("Not yet implemented") }
    override fun unfold(inStream: Readable) { TODO("Not yet implemented") }
    override fun unfold(inData: Retrievable) { TODO("Not yet implemented") }

    public fun foldSize(foldFormat: FoldFormat): Long

    public fun withFoldSize(foldFormat: FoldFormat, action: FoldFormat.() -> Long): Long = foldFormat.action()


    public fun FoldFormat.sizeOf(type: BlockType): Long = type.foldSize(this)

    public fun FoldFormat.sizeOf(value: Byte): Long = ByteType.atomicSize.toLong()
    public fun FoldFormat.sizeOf(value: Short): Long = ShortType.atomicSize.toLong()
    public fun FoldFormat.sizeOf(value: Int): Long = IntType.atomicSize.toLong()
    public fun FoldFormat.sizeOf(value: Long): Long = LongType.atomicSize.toLong()
    public fun FoldFormat.sizeOf(value: Float): Long = FloatType.atomicSize.toLong()
    public fun FoldFormat.sizeOf(value: Double): Long = DoubleType.atomicSize.toLong()
    public fun FoldFormat.sizeOf(value: UByte): Long = UByteType.atomicSize.toLong()
    public fun FoldFormat.sizeOf(value: UShort): Long = UShortType.atomicSize.toLong()
    public fun FoldFormat.sizeOf(value: UInt): Long = UIntType.atomicSize.toLong()
    public fun FoldFormat.sizeOf(value: ULong): Long = ULongType.atomicSize.toLong()


    public fun FoldFormat.sizeOf(value: ByteBuffer): Long = ByteArrayType(value).foldSize(this)
    public fun FoldFormat.sizeOf(value: ShortBuffer): Long = ShortArrayType(value).foldSize(this)
    public fun FoldFormat.sizeOf(value: IntBuffer): Long = IntArrayType(value).foldSize(this)
    public fun FoldFormat.sizeOf(value: LongBuffer): Long = LongArrayType(value).foldSize(this)
    public fun FoldFormat.sizeOf(value: FloatBuffer): Long = FloatArrayType(value).foldSize(this)
    public fun FoldFormat.sizeOf(value: DoubleBuffer): Long = DoubleArrayType(value).foldSize(this)
    public fun FoldFormat.sizeOf(value: UByteBuffer): Long = UByteArrayType(value).foldSize(this)
    public fun FoldFormat.sizeOf(value: UShortBuffer): Long = UShortArrayType(value).foldSize(this)
    public fun FoldFormat.sizeOf(value: UIntBuffer): Long = UIntArrayType(value).foldSize(this)
    public fun FoldFormat.sizeOf(value: ULongBuffer): Long = ULongArrayType(value).foldSize(this)


    public fun FoldFormat.sizeOf(value: Uuid4): Long = Uuid4Type(value).foldSize(this)
    public fun FoldFormat.sizeOf(value: Text): Long = StringType(value).foldSize(this)
    public fun FoldFormat.sizeOf(value: BigInt): Long = BigIntType(value).foldSize(this)

    public fun <P: Packageable>FoldFormat.sizeOf(type: StructType<P>): Long = type.foldSize(this)
    public fun <P: Packageable>FoldFormat.sizeOf(type: ObjectType<P>): Long = type.foldSize(this)
    public fun <P: Packageable>FoldFormat.sizeOf(type: DictType<P>): Long = type.foldSize(this)
    public fun <P: Packageable>FoldFormat.sizeOf(type: ListType<P>): Long = type.foldSize(this)

    public val foldFormat: FoldFormat
}