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

import org.angproj.aux.TestInformationStub.refInt
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

data class IntTestObjectPackage(
    var int: Int = 0
): Package { // According to Package convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat) { listOf(sizeOf(int),).sum() }
    override fun enfold(outStream: BinaryWritable): Unit = withEnfold(outStream) { saveInt(int) }
    override fun unfold(inStream: BinaryReadable): Unit = withUnfold(inStream) { int = loadInt() }
}

data class IntTestStructPackageable(
    var int: Int = 0
): Packageable { // According to struct Packageable convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat){ listOf(sizeOf(int),).sum() }
    override fun enfold(outData: Storable, offset: Int): Int = withEnfold(outData) { saveInt(int) }
    override fun unfold(inData: Retrievable, offset: Int): Int = withUnfold(inData, offset) { int = loadInt() }
    override fun foldFormat(): FoldFormat = FoldFormat.BLOCK
}

data class IntTestObjectPackageable(
    var int: Int = 0
): Packageable { // According to object Packageable convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat){ listOf(sizeOf(int),).sum() }
    override fun enfold(outStream: BinaryWritable): Unit = withEnfold(outStream) { saveInt(int) }
    override fun unfold(inStream: BinaryReadable): Unit = withUnfold(inStream) { int = loadInt() }
    override fun foldFormat(): FoldFormat = FoldFormat.STREAM
}

class IntTypeTest {

    val testInt = refInt

    @Test
    fun enfoldUnfoldToObjectPackage() {
        val to1 = IntTestObjectPackage(testInt)
        val buf = BufMgr.binary(DataSize._4K.size)
        val len = ObjectType(to1).enfoldStream(buf)
        buf.flip()
        val to2 = ObjectType.unfoldStream(buf) { IntTestObjectPackage() }.value

        assertEquals(len, buf.limit)
        assertEquals(to1, to2)
    }

    @Test
    fun enfoldUnfoldToStructPackageable() {
        val to1 = IntTestStructPackageable(testInt)
        val bin = BufMgr.bin(DataSize._4K.size)
        val len1 = StructType(to1).enfoldBlock(bin, 0)
        bin.limitAt(len1)
        val to2 = StructType.unfoldBlock(bin) { IntTestStructPackageable() }.value

        assertEquals(to1, to2)
    }

    @Test
    fun enfoldUnfoldToObjectPackageable() {
        val to1 = IntTestObjectPackageable(testInt)
        val buf = BufMgr.binary(DataSize._4K.size)
        val len = ObjectType(to1).enfoldStream(buf)
        buf.flip()
        val to2 = ObjectType.unfoldStream(buf) { IntTestObjectPackageable() }.value

        assertEquals(len, buf.limit)
        assertEquals(to1, to2)
    }

    @Test
    fun enfoldUnfoldToBlock() {
        val type = IntType(testInt)
        val block = BlockType(binOf(type.foldSize(FoldFormat.BLOCK)))
        assertEquals(block.foldSize(FoldFormat.BLOCK), type.foldSize(FoldFormat.BLOCK))
        type.enfoldBlock(block, 0)

        val retrieved = IntType.unfoldBlock(block, 0)
        assertEquals(type.value, retrieved.value)
    }

    @Test
    fun enfoldUnfoldToStream() {
        val type = IntType(testInt)
        val stream = BinaryBuffer()
        type.enfoldStream(stream)
        stream.flip()
        assertEquals(stream.limit, type.foldSize(FoldFormat.STREAM))

        val retrieved = IntType.unfoldStream(stream)
        assertEquals(type.value, retrieved.value)
    }
}