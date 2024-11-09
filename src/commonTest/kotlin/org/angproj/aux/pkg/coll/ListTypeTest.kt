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
import org.angproj.aux.util.NullObject
import org.angproj.aux.util.Uuid4
import org.angproj.aux.util.uuid4
import kotlin.test.Test
import kotlin.test.assertEquals


data class RootListTestObjectPackage(
    var rootList: MutableList<NodeListTestObjectPackage> = mutableListOf()
): Package { // According to Package convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat) { listOf(sizeOf(rootList),).sum() }
    override fun enfold(outStream: BinaryWritable): Unit = withEnfold(outStream) { saveList(rootList) }
    override fun unfold(inStream: BinaryReadable): Unit = withUnfold(inStream) { rootList = loadList { NodeListTestObjectPackage() } }
}

data class NodeListTestObjectPackage(
    var path: Text = NullObject.text,
    var nodeList: MutableList<NodeListTestObjectPackage> = mutableListOf(),
): Package { // According to Package convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat) { listOf(
        sizeOf(path),
        sizeOf(nodeList),
        ).sum() }
    override fun enfold(outStream: BinaryWritable): Unit = withEnfold(outStream) {
        saveString(path)
        saveList(nodeList)
    }
    override fun unfold(inStream: BinaryReadable): Unit = withUnfold(inStream) {
        path = loadString()
        nodeList = loadList { NodeListTestObjectPackage() }
    }
}


data class RootListTestObjectPackage2(
    var rootList: MutableList<NodeListTestStructPackage> = mutableListOf()
): Package { // According to Package convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat) { listOf(sizeOf(rootList),).sum() }
    override fun enfold(outStream: BinaryWritable): Unit = withEnfold(outStream) { saveList(rootList) }
    override fun unfold(inStream: BinaryReadable): Unit = withUnfold(inStream) { rootList = loadList { NodeListTestStructPackage() } }
}

data class NodeListTestStructPackage(
    val uuid: Uuid4 = uuid4(),
): Packageable { // According to Package convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat){ listOf(sizeOf(uuid),).sum() }
    override fun enfold(outData: Storable, offset: Int): Int = withEnfold(outData) { saveUuid4(uuid) }
    override fun unfold(inData: Retrievable, offset: Int): Int = withUnfold(inData, offset) { loadUuid4(uuid) }
    override fun foldFormat(): FoldFormat = FoldFormat.BLOCK
}


class ListTypeTest {

    val debug: Boolean = false

    private fun listLevel(level: Int, path: String,  maxLevel: Int = 4): NodeListTestObjectPackage {
        val node = NodeListTestObjectPackage()
        node.path = path.toText()
        if(level < maxLevel) repeat(maxLevel) {
            val p2 = "$path.${it+1}"
            if(debug) println(p2)
            node.nodeList.add(listLevel(level + 1, p2, maxLevel))
        }
        return node
    }

    private fun setInputListTestObject(): RootListTestObjectPackage {
        val root = RootListTestObjectPackage()
        val path = "1"
        if(debug) println(path)
        repeat(3) {
            val p2 = "$path.${it+1}"
            if (debug) println(p2)
            root.rootList.add(listLevel(0, p2, 3))
        }
        return root
    }

    private fun setInputListTestStruct(): RootListTestObjectPackage2 {
        val root = RootListTestObjectPackage2()
        repeat(128) {
            val node = NodeListTestStructPackage()
            if (debug) println(node.uuid)
            root.rootList.add(node)
        }
        return root
    }

    @Test
    fun enfoldUnfoldToObjectPackage() {
        val to1 = setInputListTestObject()
        val buf = BufMgr.binary(DataSize._8K.size)
        val len = ObjectType(to1).enfoldStream(buf)
        buf.flip()
        val to2 = ObjectType.unfoldStream(buf) { RootListTestObjectPackage() }.value

        assertEquals(to1, to2)
        assertEquals(len, buf.limit)
    }

    @Test
    fun enfoldUnfoldToStructPackageable() {
        val to1 = setInputListTestStruct()
        val buf = BufMgr.binary(DataSize._8K.size)
        val len = ObjectType(to1).enfoldStream(buf)
        buf.flip()
        val to2 = ObjectType.unfoldStream(buf) { RootListTestObjectPackage2() }.value

        if(debug) {
            println("Said length: $len")
            println("Buffer use: " + buf.limit)
        }

        assertEquals(to1, to2)
        assertEquals(len, buf.limit)
    }

    @Test
    fun enfoldUnfoldObject() {
        val pl1 = MutableList(10) { NodeListTestObjectPackage().apply { path = uuid4().toText() } }
        val buf = BinaryBuffer()
        val lt = ListType(pl1)
        lt.enfoldStream(buf)
        buf.flip()

        assertEquals(lt.foldSize(FoldFormat.STREAM), buf.limit)

        val pl2 = ListType.unfoldStream(buf) { NodeListTestObjectPackage() }.value

        assertEquals(pl1, pl2)
    }

    @Test
    fun enfoldUnfoldStruct() {
        val pl1 = MutableList(10) { NodeListTestStructPackage() }
        val buf = BinaryBuffer()
        val lt = ListType(pl1)
        lt.enfoldStream(buf)
        buf.flip()

        assertEquals(lt.foldSize(FoldFormat.BLOCK), buf.limit)

        val pl2 = ListType.unfoldStream(buf) {  NodeListTestStructPackage() }.value

        assertEquals(pl1, pl2)
    }
}