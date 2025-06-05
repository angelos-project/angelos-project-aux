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
package org.angproj.aux.pkg.arb

import org.angproj.aux.buf.BinaryBuffer
import org.angproj.aux.io.*
import org.angproj.aux.mem.BufMgr
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.Package
import org.angproj.aux.pkg.Packageable
import org.angproj.aux.pkg.coll.ObjectType
import org.angproj.aux.pkg.type.BlockType
import org.angproj.aux.util.NullObject
import org.angproj.aux.util.Uuid4
import org.angproj.aux.util.uuid4
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith


data class UuidTestObjectPackage(
    var uuid: Uuid4 = NullObject.uuid4 // uuid4()
): Package { // According to Package convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat) { listOf(sizeOf(uuid),).sum() }
    override fun enfold(outStream: BinaryWritable): Unit = withEnfold(outStream) { saveUuid4(uuid) }
    override fun unfold(inStream: BinaryReadable): Unit = withUnfold(inStream) { uuid = loadUuid4() }
}

data class UuidTestStructPackageable(
    val uuid: Uuid4 = uuid4()
): Packageable { // According to struct Packageable convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat){ listOf(sizeOf(uuid),).sum() }
    override fun enfold(outData: Storable, offset: Int): Int = withEnfold(outData) { saveUuid4(uuid) }
    override fun unfold(inData: Retrievable, offset: Int): Int = withUnfold(inData, offset) { loadUuid4(uuid) }
    override fun foldFormat(): FoldFormat = FoldFormat.BLOCK
}

data class UuidTestObjectPackageable(
    var uuid: Uuid4 = NullObject.uuid4 // uuid4()
): Packageable { // According to object Packageable convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat){ listOf(sizeOf(uuid),).sum() }
    override fun enfold(outStream: BinaryWritable): Unit = withEnfold(outStream) { saveUuid4(uuid) }
    override fun unfold(inStream: BinaryReadable): Unit = withUnfold(inStream) { uuid = loadUuid4() }
    override fun foldFormat(): FoldFormat = FoldFormat.STREAM
}


class Uuid4TypeTest {

    private val testUuid4 = uuid4()

    @Test
    fun enfoldUnfoldToObjectPackage() {
        val to1 = UuidTestObjectPackage(testUuid4)
        val buf = BufMgr.binary(DataSize._4K.size)
        val len = ObjectType(to1).enfoldStream(buf)
        buf.flip()
        val to2 = ObjectType.unfoldStream(buf) { UuidTestObjectPackage() }.value

        assertEquals(len, buf.limit)
        assertEquals(to1, to2)

        buf.clear()
        assertFailsWith<IllegalArgumentException> { ObjectType(UuidTestObjectPackage()).enfoldStream(buf) }
    }

    @Test
    fun enfoldUnfoldToStructPackageable() {
        val to1 = UuidTestStructPackageable()
        val bin = BufMgr.bin(DataSize._4K.size)
        val len1 = StructType(to1).enfoldBlock(bin, 0)
        bin.limitAt(len1)
        val to2 = StructType.unfoldBlock(bin) { UuidTestStructPackageable() }.value

        assertEquals(to1, to2)
        assertFailsWith<IllegalArgumentException> {
            StructType(UuidTestStructPackageable(NullObject.uuid4)).enfoldBlock(bin, 0) }
    }

    @Test
    fun enfoldUnfoldToObjectPackageable() {
        val to1 = UuidTestObjectPackageable(testUuid4)
        val buf = BufMgr.binary(DataSize._4K.size)
        val len = ObjectType(to1).enfoldStream(buf)
        buf.flip()
        val to2 = ObjectType.unfoldStream(buf) { UuidTestObjectPackageable() }.value

        assertEquals(len, buf.limit)
        assertEquals(to1, to2)

        buf.clear()
        assertFailsWith<IllegalArgumentException> { ObjectType(UuidTestObjectPackage()).enfoldStream(buf) }
    }

    @Test
    fun enfoldUnfoldToBlock() {
        val type = Uuid4Type(testUuid4)
        val block = BlockType(binOf(type.foldSize(FoldFormat.BLOCK)))

        assertEquals(block.foldSize(FoldFormat.BLOCK), type.foldSize(FoldFormat.BLOCK))
        type.enfoldBlock(block, 0)

        val retrieved = Uuid4Type.unfoldBlock(block, 0)
        assertEquals(type, retrieved)

        val retrieved2 = uuid4()
        Uuid4Type.unfoldFromBlock(block, retrieved2)
        assertEquals(type.value, retrieved2)

        assertFailsWith<IllegalArgumentException> {
            Uuid4Type.unfoldFromBlock(block, NullObject.uuid4) }
    }

    @Test
    fun enfoldUnfoldToStream() {
        val type = Uuid4Type(testUuid4)
        val stream = BinaryBuffer()
        type.enfoldStream(stream)
        stream.flip()

        assertEquals(type.foldSize(FoldFormat.STREAM), stream.limit)

        val retrieved = Uuid4Type.unfoldStream(stream)
        assertEquals(type, retrieved)
    }
}