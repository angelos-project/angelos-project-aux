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
import org.angproj.aux.TestInformationStub.refLong
import org.angproj.aux.buf.BinaryBuffer
import org.angproj.aux.io.*
import org.angproj.aux.mem.BufMgr
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.Package
import org.angproj.aux.pkg.Packageable
import org.angproj.aux.pkg.arb.StructType
import org.angproj.aux.pkg.coll.ObjectType
import org.angproj.aux.pkg.prime.LongType
import org.angproj.aux.pkg.type.BlockType
import kotlin.test.Test
import kotlin.test.assertEquals

data class LongTestObjectPackage(
    var long: Long = 0
): Package { // According to Package convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat) { listOf(sizeOf(long),).sum() }
    override fun enfold(outStream: BinaryWritable): Unit = withEnfold(outStream) { saveLong(long) }
    override fun unfold(inStream: BinaryReadable): Unit = withUnfold(inStream) { long = loadLong() }
}

data class LongTestStructPackageable(
    var long: Long = 0
): Packageable { // According to struct Packageable convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat){ listOf(sizeOf(long),).sum() }
    override fun enfold(outData: Storable, offset: Int): Int = withEnfold(outData) { saveLong(long) }
    override fun unfold(inData: Retrievable, offset: Int): Int = withUnfold(inData, offset) { long = loadLong() }
    override fun foldFormat(): FoldFormat = FoldFormat.BLOCK
}

data class LongTestObjectPackageable(
    var long: Long = 0
): Packageable { // According to object Packageable convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat){ listOf(sizeOf(long),).sum() }
    override fun enfold(outStream: BinaryWritable): Unit = withEnfold(outStream) { saveLong(long) }
    override fun unfold(inStream: BinaryReadable): Unit = withUnfold(inStream) { long = loadLong() }
    override fun foldFormat(): FoldFormat = FoldFormat.STREAM
}

class LongTypeTest {

    val testLong = refLong

    @Test
    fun enfoldUnfoldToObjectPackage() {
        val to1 = LongTestObjectPackage(testLong)
        val buf = BufMgr.binary(DataSize._4K.size)
        val len = ObjectType(to1).enfoldStream(buf)
        buf.flip()
        val to2 = ObjectType.unfoldStream(buf) { LongTestObjectPackage() }.value

        assertEquals(len, buf.limit)
        assertEquals(to1, to2)
    }

    @Test
    fun enfoldUnfoldToStructPackageable() {
        val to1 = LongTestStructPackageable(testLong)
        val bin = BufMgr.bin(DataSize._4K.size)
        val len1 = StructType(to1).enfoldBlock(bin, 0)
        bin.limitAt(len1)
        val to2 = StructType.unfoldBlock(bin) { LongTestStructPackageable() }.value

        assertEquals(to1, to2)
    }

    @Test
    fun enfoldUnfoldToObjectPackageable() {
        val to1 = LongTestObjectPackageable(testLong)
        val buf = BufMgr.binary(DataSize._4K.size)
        val len = ObjectType(to1).enfoldStream(buf)
        buf.flip()
        val to2 = ObjectType.unfoldStream(buf) { LongTestObjectPackageable() }.value

        assertEquals(len, buf.limit)
        assertEquals(to1, to2)
    }

    @Test
    fun enfoldUnfoldToBlock() {
        val type = LongType(testLong)
        val block = BlockType(binOf(type.foldSize(FoldFormat.BLOCK)))
        assertEquals(block.foldSize(FoldFormat.BLOCK), type.foldSize(FoldFormat.BLOCK))
        type.enfoldBlock(block, 0)

        val retrieved = LongType.unfoldBlock(block, 0)
        assertEquals(type.value, retrieved.value)
    }

    @Test
    fun enfoldUnfoldToStream() {
        val type = LongType(testLong)
        val stream = BinaryBuffer()
        type.enfoldStream(stream)
        stream.flip()
        assertEquals(stream.limit, type.foldSize(FoldFormat.STREAM))

        val retrieved = LongType.unfoldStream(stream)
        assertEquals(type.value, retrieved.value)
    }
}