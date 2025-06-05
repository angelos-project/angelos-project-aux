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
package org.angproj.aux.pkg.mem

import org.angproj.aux.buf.*
import org.angproj.aux.io.*
import org.angproj.aux.mem.BufMgr
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.Package
import org.angproj.aux.pkg.Packageable
import org.angproj.aux.pkg.arb.StructType
import org.angproj.aux.pkg.coll.ObjectType
import org.angproj.aux.pkg.type.BlockType
import org.angproj.aux.util.NullObject
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals


data class IntTestObjectPackage(
    val fixBuffer: IntBuffer = IntBuffer(DataSize._128B), // Fixed size buffer
    var dynBuffer: IntBuffer = NullObject.intBuffer // Dynamic sized buffer
): Package { // According to Package convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat) { listOf(
        sizeOf(fixBuffer),
        sizeOf(dynBuffer)
    ).sum() }
    override fun enfold(outStream: BinaryWritable): Unit = withEnfold(outStream) {
        saveIntArray(fixBuffer)
        saveIntArray(dynBuffer)
    }
    override fun unfold(inStream: BinaryReadable): Unit = withUnfold(inStream) {
        loadIntArray(fixBuffer)
        dynBuffer = loadIntArray()
    }
}

data class IntTestStructPackageable(
    val fixBuffer: IntBuffer = IntBuffer(DataSize._128B), // Fixed size buffer
): Packageable { // According to struct Packageable convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat){ listOf(
        sizeOf(fixBuffer),
    ).sum() }
    override fun enfold(outData: Storable, offset: Int): Int = withEnfold(outData) {
        saveIntArray(fixBuffer)
    }
    override fun unfold(inData: Retrievable, offset: Int): Int = withUnfold(inData, offset) {
        loadIntArray(fixBuffer)
    }
    override fun foldFormat(): FoldFormat = FoldFormat.BLOCK
}

data class IntTestObjectPackageable(
    val fixBuffer: IntBuffer = IntBuffer(DataSize._128B), // Fixed size buffer
    var dynBuffer: IntBuffer = NullObject.intBuffer // Dynamic sized buffer
): Packageable { // According to object Packageable convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat){ listOf(
        sizeOf(fixBuffer),
        sizeOf(dynBuffer)
    ).sum() }
    override fun enfold(outStream: BinaryWritable): Unit = withEnfold(outStream) {
        saveIntArray(fixBuffer)
        saveIntArray(dynBuffer)
    }
    override fun unfold(inStream: BinaryReadable): Unit = withUnfold(inStream) {
        loadIntArray(fixBuffer)
        dynBuffer = loadIntArray()
    }
    override fun foldFormat(): FoldFormat = FoldFormat.STREAM
}


class IntArrayTypeTest {

    @Test
    fun enfoldUnfoldToObjectPackage() {
        val to1 = IntTestObjectPackage()
        to1.fixBuffer.random()
        to1.dynBuffer = IntBuffer(DataSize._128B).apply { random() }
        val buf = BufMgr.binary(DataSize._4K.size)
        val len = ObjectType(to1).enfoldStream(buf)
        buf.flip()
        val to2 = ObjectType.unfoldStream(buf) { IntTestObjectPackage() }.value

        assertEquals(len, buf.limit)
        assertEquals(to1, to2)
    }

    @Test
    fun enfoldUnfoldToStructPackageable() {
        val to1 = IntTestStructPackageable()
        to1.fixBuffer.random()
        val bin = BufMgr.bin(DataSize._4K.size)
        val len1 = StructType(to1).enfoldBlock(bin, 0)
        bin.limitAt(len1)
        val to2 = StructType.unfoldBlock(bin) { IntTestStructPackageable() }.value

        assertEquals(to1, to2)
    }

    @Test
    fun enfoldUnfoldToObjectPackageable() {
        val to1 = IntTestObjectPackageable()
        to1.fixBuffer.random()
        to1.dynBuffer = IntBuffer(DataSize._128B).apply { random() }
        val buf = BufMgr.binary(DataSize._4K.size)
        val len = ObjectType(to1).enfoldStream(buf)
        buf.flip()
        val to2 = ObjectType.unfoldStream(buf) { IntTestObjectPackageable() }.value

        assertEquals(len, buf.limit)
        assertEquals(to1, to2)
    }


    val rand = IntBuffer(DataSize._128B).apply { random() }

    @Test
    fun enfoldArrayToBlock() {
        val type = IntArrayType(rand)
        val block = BlockType(binOf(type.foldSize(FoldFormat.BLOCK)))
        assertEquals(block.foldSize(FoldFormat.BLOCK), type.foldSize(FoldFormat.BLOCK))
        type.enfoldBlock(block, 0)

        val retrieved = IntArrayType(IntBuffer(type.value.limit))
        IntArrayType.unfoldFromBlock(block,retrieved.value)
        assertContentEquals(type.value, retrieved.value)
    }

    @Test
    fun enfoldArrayToStream() {
        val type = IntArrayType(rand)
        val stream = BinaryBuffer()
        type.enfoldStream(stream)
        stream.flip()
        assertEquals(stream.limit, type.foldSize(FoldFormat.STREAM))

        val retrieved = IntArrayType.unfoldStream(stream)
        assertContentEquals(type.value, retrieved.value)
    }
}