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


data class DoubleTestObjectPackage(
    val fixBuffer: DoubleBuffer = DoubleBuffer(DataSize._128B), // Fixed size buffer
    var dynBuffer: DoubleBuffer = NullObject.doubleBuffer // Dynamic sized buffer
): Package { // According to Package convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat) { listOf(
        sizeOf(fixBuffer),
        sizeOf(dynBuffer)
    ).sum() }
    override fun enfold(outStream: BinaryWritable): Unit = withEnfold(outStream) {
        saveDoubleArray(fixBuffer)
        saveDoubleArray(dynBuffer)
    }
    override fun unfold(inStream: BinaryReadable): Unit = withUnfold(inStream) {
        loadDoubleArray(fixBuffer)
        dynBuffer = loadDoubleArray()
    }
}

data class DoubleTestStructPackageable(
    val fixBuffer: DoubleBuffer = DoubleBuffer(DataSize._128B), // Fixed size buffer
): Packageable { // According to struct Packageable convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat){ listOf(
        sizeOf(fixBuffer),
    ).sum() }
    override fun enfold(outData: Storable, offset: Int): Int = withEnfold(outData) {
        saveDoubleArray(fixBuffer)
    }
    override fun unfold(inData: Retrievable, offset: Int): Int = withUnfold(inData, offset) {
        loadDoubleArray(fixBuffer)
    }
    override fun foldFormat(): FoldFormat = FoldFormat.BLOCK
}

data class DoubleTestObjectPackageable(
    val fixBuffer: DoubleBuffer = DoubleBuffer(DataSize._128B), // Fixed size buffer
    var dynBuffer: DoubleBuffer = NullObject.doubleBuffer // Dynamic sized buffer
): Packageable { // According to object Packageable convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat){ listOf(
        sizeOf(fixBuffer),
        sizeOf(dynBuffer)
    ).sum() }
    override fun enfold(outStream: BinaryWritable): Unit = withEnfold(outStream) {
        saveDoubleArray(fixBuffer)
        saveDoubleArray(dynBuffer)
    }
    override fun unfold(inStream: BinaryReadable): Unit = withUnfold(inStream) {
        loadDoubleArray(fixBuffer)
        dynBuffer = loadDoubleArray()
    }
    override fun foldFormat(): FoldFormat = FoldFormat.STREAM
}

class DoubleArrayTypeTest {

    @Test
    fun enfoldUnfoldToObjectPackage() {
        val to1 = DoubleTestObjectPackage()
        to1.fixBuffer.random()
        to1.dynBuffer = DoubleBuffer(DataSize._128B).apply { random() }
        val buf = BufMgr.binary(DataSize._4K.size)
        val len = ObjectType(to1).enfoldStream(buf)
        buf.flip()
        val to2 = ObjectType.unfoldStream(buf) { DoubleTestObjectPackage() }.value

        assertEquals(len, buf.limit)
        assertEquals(to1, to2)
    }

    @Test
    fun enfoldUnfoldToStructPackageable() {
        val to1 = DoubleTestStructPackageable()
        to1.fixBuffer.random()
        val bin = BufMgr.bin(DataSize._4K.size)
        val len1 = StructType(to1).enfoldBlock(bin, 0)
        bin.limitAt(len1)
        val to2 = StructType.unfoldBlock(bin) { DoubleTestStructPackageable() }.value

        assertEquals(to1, to2)
    }

    @Test
    fun enfoldUnfoldToObjectPackageable() {
        val to1 = DoubleTestObjectPackageable()
        to1.fixBuffer.random()
        to1.dynBuffer = DoubleBuffer(DataSize._128B).apply { random() }
        val buf = BufMgr.binary(DataSize._4K.size)
        val len = ObjectType(to1).enfoldStream(buf)
        buf.flip()
        val to2 = ObjectType.unfoldStream(buf) { DoubleTestObjectPackageable() }.value

        assertEquals(len, buf.limit)
        assertEquals(to1, to2)
    }


    val rand = DoubleBuffer(DataSize._128B).apply { random() }

    @Test
    fun enfoldArrayToBlock() {
        val type = DoubleArrayType(rand)
        val block = BlockType(binOf(type.foldSize(FoldFormat.BLOCK)))
        assertEquals(block.foldSize(FoldFormat.BLOCK), type.foldSize(FoldFormat.BLOCK))
        type.enfoldBlock(block, 0)

        val retrieved = DoubleArrayType(DoubleBuffer(type.value.limit))
        DoubleArrayType.unfoldFromBlock(block, retrieved.value)
        assertContentEquals(type.value, retrieved.value)
    }

    @Test
    fun enfoldArrayToStream() {
        val type = DoubleArrayType(rand)
        val stream = BinaryBuffer()
        type.enfoldStream(stream)
        stream.flip()
        assertEquals(stream.limit, type.foldSize(FoldFormat.STREAM))

        val retrieved = DoubleArrayType.unfoldStream(stream)
        assertContentEquals(type.value, retrieved.value)
    }
}