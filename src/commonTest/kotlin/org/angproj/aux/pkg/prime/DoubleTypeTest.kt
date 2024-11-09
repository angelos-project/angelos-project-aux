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

import org.angproj.aux.TestInformationStub.refDouble
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


data class DoubleTestObjectPackage(
    var double: Double = 0.0
): Package { // According to Package convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat) { listOf(sizeOf(double),).sum() }
    override fun enfold(outStream: BinaryWritable): Unit = withEnfold(outStream) { saveDouble(double) }
    override fun unfold(inStream: BinaryReadable): Unit = withUnfold(inStream) { double = loadDouble() }
}

data class DoubleTestStructPackageable(
    var double: Double = 0.0
): Packageable { // According to struct Packageable convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat){ listOf(sizeOf(double),).sum() }
    override fun enfold(outData: Storable, offset: Int): Int = withEnfold(outData) { saveDouble(double) }
    override fun unfold(inData: Retrievable, offset: Int): Int = withUnfold(inData, offset) { double = loadDouble() }
    override fun foldFormat(): FoldFormat = FoldFormat.BLOCK
}

data class DoubleTestObjectPackageable(
    var double: Double = 0.0
): Packageable { // According to object Packageable convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat){ listOf(sizeOf(double),).sum() }
    override fun enfold(outStream: BinaryWritable): Unit = withEnfold(outStream) { saveDouble(double) }
    override fun unfold(inStream: BinaryReadable): Unit = withUnfold(inStream) { double = loadDouble() }
    override fun foldFormat(): FoldFormat = FoldFormat.STREAM
}

class DoubleTypeTest {

    val testDouble = refDouble

    @Test
    fun enfoldUnfoldToObjectPackage() {
        val to1 = DoubleTestObjectPackage(testDouble)
        val buf = BufMgr.binary(DataSize._4K.size)
        val len = ObjectType(to1).enfoldStream(buf)
        buf.flip()
        val to2 = ObjectType.unfoldStream(buf) { DoubleTestObjectPackage() }.value

        assertEquals(len, buf.limit)
        assertEquals(to1, to2)
    }

    @Test
    fun enfoldUnfoldToStructPackageable() {
        val to1 = DoubleTestStructPackageable(testDouble)
        val bin = BufMgr.bin(DataSize._4K.size)
        val len1 = StructType(to1).enfoldBlock(bin, 0)
        bin.limitAt(len1)
        val to2 = StructType.unfoldBlock(bin) { DoubleTestStructPackageable() }.value

        assertEquals(to1, to2)
    }

    @Test
    fun enfoldUnfoldToObjectPackageable() {
        val to1 = DoubleTestObjectPackageable(testDouble)
        val buf = BufMgr.binary(DataSize._4K.size)
        val len = ObjectType(to1).enfoldStream(buf)
        buf.flip()
        val to2 = ObjectType.unfoldStream(buf) { DoubleTestObjectPackageable() }.value

        assertEquals(len, buf.limit)
        assertEquals(to1, to2)
    }

    val first: Double = -1.9695734209401788E-261

    @Test
    fun enfoldUnfoldToBlock() {
        val type = DoubleType(first)
        val block = BlockType(binOf(type.foldSize(FoldFormat.BLOCK)))
        assertEquals(block.foldSize(FoldFormat.BLOCK), type.foldSize(FoldFormat.BLOCK))
        type.enfoldBlock(block, 0)

        val retrieved = DoubleType.unfoldBlock(block, 0)
        assertEquals(type.value, retrieved.value)
    }

    @Test
    fun enfoldUnfoldToStream() {
        val type = DoubleType(first)
        val stream = BinaryBuffer()
        type.enfoldStream(stream)
        stream.flip()
        assertEquals(stream.limit, type.foldSize(FoldFormat.STREAM))

        val retrieved = DoubleType.unfoldStream(stream)
        assertEquals(type.value, retrieved.value)
    }
}