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
package org.angproj.aux.pkg.coll

import org.angproj.aux.io.*
import org.angproj.aux.mem.BufMgr
import org.angproj.aux.pkg.*
import org.angproj.aux.pkg.arb.StructType
import org.angproj.aux.util.Uuid4
import org.angproj.aux.util.uuid4
import kotlin.test.Test
import kotlin.test.assertEquals


data class RootArrayTestObjectPackage(
    var rootArray: Array<NodeArrayTestStructPackage> = emptyArray()
): Package { // According to Package convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat) { listOf(sizeOf(rootArray),).sum() }
    override fun enfold(outStream: BinaryWritable): Unit = withEnfold(outStream) { saveArray(rootArray) }
    override fun unfold(inStream: BinaryReadable): Unit = withUnfold(inStream) {
        rootArray = loadArray { c -> Array(c) { NodeArrayTestStructPackage() } } }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RootArrayTestObjectPackage) return false
        if (!rootArray.contentEquals(other.rootArray)) return false
        return true
    }

    override fun hashCode(): Int = rootArray.contentHashCode()
}

data class RootArrayTestStructPackage(
    val rootArray: Array<NodeArrayTestStructPackage> = Array(16) { NodeArrayTestStructPackage() }
): Packageable { // According to Package convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat) { listOf(sizeOf(rootArray),).sum() }
    override fun enfold(outData: Storable, offset: Int): Int = withEnfold(outData, offset) { saveArray(rootArray) }
    override fun unfold(inData: Retrievable, offset: Int): Int = withUnfold(inData, offset) { loadArray(rootArray) }
    override fun foldFormat(): FoldFormat = FoldFormat.BLOCK

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RootArrayTestStructPackage) return false
        if (!rootArray.contentEquals(other.rootArray)) return false
        return true
    }

    override fun hashCode(): Int = rootArray.contentHashCode()
}

data class NodeArrayTestStructPackage(
    val uuid: Uuid4 = uuid4(),
): Packageable { // According to Package convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat){ listOf(sizeOf(uuid),).sum() }
    override fun enfold(outData: Storable, offset: Int): Int = withEnfold(outData, offset) { saveUuid4(uuid) }
    override fun unfold(inData: Retrievable, offset: Int): Int = withUnfold(inData, offset) { loadUuid4(uuid) }
    override fun foldFormat(): FoldFormat = FoldFormat.BLOCK
}


class ArrayTypeTest {

    val debug: Boolean = false

    @Test
    fun enfoldUnfoldToObjectPackageable() {
        val to1 = RootArrayTestObjectPackage()
        val buf = BufMgr.binary(DataSize._8K.size)
        val len = ObjectType(to1).enfoldStream(buf)
        buf.flip()
        val to2 = ObjectType.unfoldStream(buf) { RootArrayTestObjectPackage() }.value

        if(debug) {
            println("Said length: $len")
            println("Buffer use: " + buf.limit)
        }

        assertEquals(to1, to2)
        assertEquals(len, buf.limit)
    }

    @Test
    fun enfoldUnfoldToStructPackageable() {
        val to1 = RootArrayTestStructPackage()
        val buf = binOf(to1.foldSize(FoldFormat.BLOCK))
        println(buf.limit)
        val len = StructType(to1).enfoldBlock(buf, 0)

        assertEquals(len, buf.limit)
        assertEquals(to1.foldSize(FoldFormat.BLOCK), buf.limit)

        val to2 = StructType.unfoldBlock(buf) { RootArrayTestStructPackage() }.value

        assertEquals(to1, to2)
    }
}