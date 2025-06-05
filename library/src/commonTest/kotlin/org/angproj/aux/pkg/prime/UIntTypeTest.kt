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
package org.angproj.aux.pkg.prime

import org.angproj.aux.TestInformationStub.refUInt
import org.angproj.aux.buf.BinaryBuffer
import org.angproj.aux.io.*
import org.angproj.aux.mem.BufMgr
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.Package
import org.angproj.aux.pkg.Packageable
import org.angproj.aux.pkg.arb.StructType
import org.angproj.aux.pkg.coll.ObjectType
import org.angproj.aux.pkg.type.BlockType
import kotlin.test.Test
import kotlin.test.assertEquals

data class UIntTestObjectPackage(
    var uInt: UInt = 0u
): Package { // According to Package convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat) { listOf(sizeOf(uInt),).sum() }
    override fun enfold(outStream: BinaryWritable): Unit = withEnfold(outStream) { saveUInt(uInt) }
    override fun unfold(inStream: BinaryReadable): Unit = withUnfold(inStream) { uInt = loadUInt() }
}

data class UIntTestStructPackageable(
    var uInt: UInt = 0u
): Packageable { // According to struct Packageable convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat){ listOf(sizeOf(uInt),).sum() }
    override fun enfold(outData: Storable, offset: Int): Int = withEnfold(outData) { saveUInt(uInt) }
    override fun unfold(inData: Retrievable, offset: Int): Int = withUnfold(inData, offset) { uInt = loadUInt() }
    override fun foldFormat(): FoldFormat = FoldFormat.BLOCK
}

data class UIntTestObjectPackageable(
    var uInt: UInt = 0u
): Packageable { // According to object Packageable convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat){ listOf(sizeOf(uInt),).sum() }
    override fun enfold(outStream: BinaryWritable): Unit = withEnfold(outStream) { saveUInt(uInt) }
    override fun unfold(inStream: BinaryReadable): Unit = withUnfold(inStream) { uInt = loadUInt() }
    override fun foldFormat(): FoldFormat = FoldFormat.STREAM
}

class UIntTypeTest {

    val testUInt = refUInt

    @Test
    fun enfoldUnfoldToObjectPackage() {
        val to1 = UIntTestObjectPackage(testUInt)
        val buf = BufMgr.binary(DataSize._4K.size)
        val len = ObjectType(to1).enfoldStream(buf)
        buf.flip()
        val to2 = ObjectType.unfoldStream(buf) { UIntTestObjectPackage() }.value

        assertEquals(len, buf.limit)
        assertEquals(to1, to2)
    }

    @Test
    fun enfoldUnfoldToStructPackageable() {
        val to1 = UIntTestStructPackageable(testUInt)
        val bin = BufMgr.bin(DataSize._4K.size)
        val len1 = StructType(to1).enfoldBlock(bin, 0)
        bin.limitAt(len1)
        val to2 = StructType.unfoldBlock(bin) { UIntTestStructPackageable() }.value

        assertEquals(to1, to2)
    }

    @Test
    fun enfoldUnfoldToObjectPackageable() {
        val to1 = UIntTestObjectPackageable(testUInt)
        val buf = BufMgr.binary(DataSize._4K.size)
        val len = ObjectType(to1).enfoldStream(buf)
        buf.flip()
        val to2 = ObjectType.unfoldStream(buf) { UIntTestObjectPackageable() }.value

        assertEquals(len, buf.limit)
        assertEquals(to1, to2)
    }

    @Test
    fun enfoldUnfoldToBlock() {
        val type = UIntType(testUInt)
        val block = BlockType(binOf(type.foldSize(FoldFormat.BLOCK)))
        assertEquals(block.foldSize(FoldFormat.BLOCK), type.foldSize(FoldFormat.BLOCK))
        type.enfoldBlock(block, 0)

        val retrieved = UIntType.unfoldBlock(block, 0)
        assertEquals(type.value, retrieved.value)
    }

    @Test
    fun enfoldUnfoldToStream() {
        val type = UIntType(testUInt)
        val stream = BinaryBuffer()
        type.enfoldStream(stream)
        stream.flip()
        assertEquals(stream.limit, type.foldSize(FoldFormat.STREAM))

        val retrieved = UIntType.unfoldStream(stream)
        assertEquals(type.value, retrieved.value)
    }
}