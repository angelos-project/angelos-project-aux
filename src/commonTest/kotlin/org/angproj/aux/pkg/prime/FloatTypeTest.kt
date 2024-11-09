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

import org.angproj.aux.TestInformationStub.refFloat
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

data class FloatTestObjectPackage(
    var float: Float = 0f
): Package { // According to Package convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat) { listOf(sizeOf(float),).sum() }
    override fun enfold(outStream: BinaryWritable): Unit = withEnfold(outStream) { saveFloat(float) }
    override fun unfold(inStream: BinaryReadable): Unit = withUnfold(inStream) { float = loadFloat() }
}

data class FloatTestStructPackageable(
    var float: Float = 0f
): Packageable { // According to struct Packageable convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat){ listOf(sizeOf(float),).sum() }
    override fun enfold(outData: Storable, offset: Int): Int = withEnfold(outData) { saveFloat(float) }
    override fun unfold(inData: Retrievable, offset: Int): Int = withUnfold(inData, offset) { float = loadFloat() }
    override fun foldFormat(): FoldFormat = FoldFormat.BLOCK
}

data class FloatTestObjectPackageable(
    var float: Float = 0f
): Packageable { // According to object Packageable convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat){ listOf(sizeOf(float),).sum() }
    override fun enfold(outStream: BinaryWritable): Unit = withEnfold(outStream) { saveFloat(float) }
    override fun unfold(inStream: BinaryReadable): Unit = withUnfold(inStream) { float = loadFloat() }
    override fun foldFormat(): FoldFormat = FoldFormat.STREAM
}

class FloatTypeTest {

    val testFloat = refFloat

    @Test
    fun enfoldUnfoldToObjectPackage() {
        val to1 = FloatTestObjectPackage(testFloat)
        val buf = BufMgr.binary(DataSize._4K.size)
        val len = ObjectType(to1).enfoldStream(buf)
        buf.flip()
        val to2 = ObjectType.unfoldStream(buf) { FloatTestObjectPackage() }.value

        assertEquals(len, buf.limit)
        assertEquals(to1, to2)
    }

    @Test
    fun enfoldUnfoldToStructPackageable() {
        val to1 = FloatTestStructPackageable(testFloat)
        val bin = BufMgr.bin(DataSize._4K.size)
        val len1 = StructType(to1).enfoldBlock(bin, 0)
        bin.limitAt(len1)
        val to2 = StructType.unfoldBlock(bin) { FloatTestStructPackageable() }.value

        assertEquals(to1, to2)
    }

    @Test
    fun enfoldUnfoldToObjectPackageable() {
        val to1 = FloatTestObjectPackageable(testFloat)
        val buf = BufMgr.binary(DataSize._4K.size)
        val len = ObjectType(to1).enfoldStream(buf)
        buf.flip()
        val to2 = ObjectType.unfoldStream(buf) { FloatTestObjectPackageable() }.value

        assertEquals(len, buf.limit)
        assertEquals(to1, to2)
    }

    @Test
    fun enfoldUnfoldToBlock() {
        val type = FloatType(testFloat)
        val block = BlockType(binOf(type.foldSize(FoldFormat.BLOCK)))
        assertEquals(block.foldSize(FoldFormat.BLOCK), type.foldSize(FoldFormat.BLOCK))
        type.enfoldBlock(block, 0)

        val retrieved = FloatType.unfoldBlock(block, 0)
        assertEquals(type.value, retrieved.value)
    }

    @Test
    fun enfoldUnfoldToStream() {
        val type = FloatType(testFloat)
        val stream = BinaryBuffer()
        type.enfoldStream(stream)
        stream.flip()
        assertEquals(stream.limit, type.foldSize(FoldFormat.STREAM))

        val retrieved = FloatType.unfoldStream(stream)
        assertEquals(type.value, retrieved.value)
    }
}