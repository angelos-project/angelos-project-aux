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

import org.angproj.aux.TestInformationStub
import org.angproj.aux.buf.BinaryBuffer
import org.angproj.aux.io.*
import org.angproj.aux.mem.BufMgr
import org.angproj.aux.pkg.FoldFormat
import org.angproj.aux.pkg.Package
import org.angproj.aux.pkg.Packageable
import org.angproj.aux.pkg.coll.ObjectType
import org.angproj.aux.util.NullObject
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith


data class StringTestObjectPackage(
    var text: Text = NullObject.text
): Package { // According to Package convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat) { listOf(sizeOf(text),).sum() }
    override fun enfold(outStream: BinaryWritable): Unit = withEnfold(outStream) { saveString(text) }
    override fun unfold(inStream: BinaryReadable): Unit = withUnfold(inStream) { text = loadString() }
}

data class StringTestObjectPackageable(
    var text: Text = NullObject.text
): Packageable { // According to object Packageable convention
    override fun foldSize(foldFormat: FoldFormat): Int = withFoldSize(foldFormat) { listOf(sizeOf(text),).sum() }
    override fun enfold(outStream: BinaryWritable): Unit = withEnfold(outStream) { saveString(text) }
    override fun unfold(inStream: BinaryReadable): Unit = withUnfold(inStream) { text = loadString() }
    override fun foldFormat(): FoldFormat = FoldFormat.STREAM
}


class StringTypeTest {

    private val testText = "Hello, world!".toText()

    @Test
    fun enfoldUnfoldToObjectPackage() {
        val to1 = StringTestObjectPackage(testText)
        val buf = BufMgr.binary(DataSize._4K.size)
        val len = ObjectType(to1).enfoldStream(buf)
        buf.flip()
        val to2 = ObjectType.unfoldStream(buf) { StringTestObjectPackage() }.value

        assertEquals(len, buf.limit)
        assertEquals(to1, to2)
    }

    @Test
    fun enfoldUnfoldToObjectPackageable() {
        val to1 = StringTestObjectPackageable(testText)
        val buf = BufMgr.binary(DataSize._4K.size)
        val len = ObjectType(to1).enfoldStream(buf)
        buf.flip()
        val to2 = ObjectType.unfoldStream(buf) { StringTestObjectPackageable() }.value

        assertEquals(len, buf.limit)
        assertEquals(to1, to2)
    }

    @Test
    fun enfoldUnfoldToStream() {
        val texts = listOf(
            TestInformationStub.lipsumShort.toText(),
            TestInformationStub.lipsumMedium.toText(),
            TestInformationStub.lipsumLong.toText()
        )
        val stream = BinaryBuffer()

        texts.forEach {
            stream.clear()
            val type = StringType(it)
            type.enfoldStream(stream)
            stream.flip()
            assertEquals(type.foldSize(FoldFormat.STREAM), stream.limit)

            val retrieved = StringType.unfoldStream(stream)
            assertEquals(type, retrieved)

            assertFailsWith<IllegalArgumentException> { StringType(NullObject.text).enfoldStream(stream) }
        }
    }
}