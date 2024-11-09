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

import org.angproj.aux.buf.BinaryBuffer
import org.angproj.aux.io.*
import org.angproj.aux.mem.BufMgr
import org.angproj.aux.pkg.*
import org.angproj.aux.util.Uuid4
import org.angproj.aux.util.uuid4
import kotlin.test.Test
import kotlin.test.assertEquals


data class RootDictTestObjectPackage(
    var rootDict: MutableMap<Text, NodeDictTestObjectPackage> = mutableMapOf()
): Package { // According to Package convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat) { listOf(sizeOf(rootDict),).sum() }
    override fun enfold(outStream: BinaryWritable): Unit = withEnfold(outStream) { saveDict(rootDict) }
    override fun unfold(inStream: BinaryReadable): Unit = withUnfold(inStream) { rootDict = loadDict { NodeDictTestObjectPackage() } }
}

data class NodeDictTestObjectPackage(
    var nodeDict: MutableMap<Text, NodeDictTestObjectPackage> = mutableMapOf(),
): Package { // According to Package convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat) { listOf(sizeOf(nodeDict),).sum() }
    override fun enfold(outStream: BinaryWritable): Unit = withEnfold(outStream) { saveDict(nodeDict) }
    override fun unfold(inStream: BinaryReadable): Unit = withUnfold(inStream) { nodeDict = loadDict { NodeDictTestObjectPackage() } }
}


data class RootDictTestObjectPackage2(
    var rootDict: MutableMap<Text, NodeDictTestStructPackage> = mutableMapOf()
): Package { // According to Package convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat) { listOf(sizeOf(rootDict),).sum() }
    override fun enfold(outStream: BinaryWritable): Unit = withEnfold(outStream) { saveDict(rootDict) }
    override fun unfold(inStream: BinaryReadable): Unit = withUnfold(inStream) { rootDict = loadDict { NodeDictTestStructPackage() } }
}

data class NodeDictTestStructPackage(
    val uuid: Uuid4 = uuid4(),
): Packageable { // According to Package convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat){ listOf(sizeOf(uuid),).sum() }
    override fun enfold(outData: Storable, offset: Int): Int = withEnfold(outData) { saveUuid4(uuid) }
    override fun unfold(inData: Retrievable, offset: Int): Int = withUnfold(inData, offset) { loadUuid4(uuid) }
    override fun foldFormat(): FoldFormat = FoldFormat.BLOCK
}


class DictTypeTest {

    val debug: Boolean = false

    private val keys = listOf(
        "Foo".toText(),
        "Bar".toText(),
        "Baz".toText(),
        //"Qux".toText()
    )

    private fun dictLevel(level: Int, path: String,  maxLevel: Int = 4): NodeDictTestObjectPackage {
        val node = NodeDictTestObjectPackage()
        if(level < maxLevel) repeat(maxLevel) {
            val p2 = "$path.${it+1}"
            if(debug) println(p2)
            node.nodeDict.put(keys[it], dictLevel(level + 1, p2, maxLevel))
        }
        return node
    }

    private fun setInputDictTestObject(): RootDictTestObjectPackage {
        val root = RootDictTestObjectPackage()
        val path = "1"
        if(debug) println(path)
        repeat(keys.size) {
            val p2 = "$path.${it+1}"
            if (debug) println(p2)
            root.rootDict.put(keys[it], dictLevel(0, p2, keys.size))
        }
        return root
    }

    private fun setInputDictTestStruct(): RootDictTestObjectPackage2 {
        val root = RootDictTestObjectPackage2()
        repeat(keys.size) {
            val node = NodeDictTestStructPackage()
            if (debug) println(node.uuid)
            root.rootDict.put(keys[it], node)
        }
        return root
    }

    @Test
    fun enfoldUnfoldToObjectPackage() {
        val to1 = setInputDictTestObject()
        val buf = BufMgr.binary(DataSize._8K.size)
        val len = ObjectType(to1).enfoldStream(buf)
        buf.flip()
        val to2 = ObjectType.unfoldStream(buf) { RootDictTestObjectPackage() }.value

        assertEquals(to1, to2)
        assertEquals(len, buf.limit)
    }

    @Test
    fun enfoldUnfoldToStructPackageable() {
        val to1 = setInputDictTestStruct()
        val buf = BufMgr.binary(DataSize._8K.size)
        val len = ObjectType(to1).enfoldStream(buf)
        buf.flip()
        val to2 = ObjectType.unfoldStream(buf) { RootDictTestObjectPackage2() }.value

        if(debug) {
            println("Said length: $len")
            println("Buffer use: " + buf.limit)
        }

        assertEquals(to1, to2)
        assertEquals(len, buf.limit)
    }

    private fun setInputTestObject(): NodeDictTestObjectPackage = NodeDictTestObjectPackage()

    private fun setInputTestStruct(): NodeDictTestStructPackage = NodeDictTestStructPackage()

    @Test
    fun enfoldUnfoldObject() {
        val pl1 = mutableMapOf(
            keys[0] to setInputTestObject(),
            keys[1] to setInputTestObject(),
            keys[2] to setInputTestObject(),
        )
        val buf = BinaryBuffer()
        val dt = DictType(pl1)
        dt.enfoldStream(buf)
        buf.flip()

        assertEquals(dt.foldSize(FoldFormat.STREAM), buf.limit)

        val pl2 = DictType.unfoldStream(buf) { NodeDictTestObjectPackage() }.value

        assertEquals(pl1, pl2)
    }

    @Test
    fun enfoldUnfoldStruct() {
        val pl1 = mutableMapOf(
            keys[0] to setInputTestStruct(),
            keys[1] to setInputTestStruct(),
            keys[2] to setInputTestStruct(),
        )
        val buf = BinaryBuffer()
        val dt = DictType(pl1)
        dt.enfoldStream(buf)
        buf.flip()

        assertEquals(dt.foldSize(FoldFormat.BLOCK), buf.limit)

        val pl2 = DictType.unfoldStream(buf) { NodeDictTestStructPackage() }.value

        assertEquals(pl1, pl2)
    }
}