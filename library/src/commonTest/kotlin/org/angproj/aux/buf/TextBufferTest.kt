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
package org.angproj.aux.buf

import org.angproj.aux.TestInformationStub
import org.angproj.aux.io.DataSize
import org.angproj.aux.io.asBinary
import org.angproj.aux.io.toText
import org.angproj.aux.toTextBuffer
import org.angproj.aux.util.readGlyphAt
import kotlin.test.Test
import kotlin.test.assertEquals


class TextBufferTest : AbstractFlowBufferTest<TextBuffer>() {

    override fun setInput(): TextBuffer {
        val lipsum = TestInformationStub.lipsumMedium.encodeToByteArray()
        val buf = TextBuffer()

        var idx = 0
        while (idx < lipsum.size) {
            val cp = lipsum.readGlyphAt(idx)
            idx += cp.octetSize()
            buf.write(cp)
        }

        return buf
    }

    override val posValue: Int = 206

    private fun ioTo(text: String) {
        val lipsum = text.encodeToByteArray()
        val buf = text.toTextBuffer(DataSize._8K)

        var idx = 0
        while (idx < lipsum.size) {
            val cp = lipsum.readGlyphAt(idx)
            idx += cp.octetSize()
            assertEquals(buf.read().value, cp.value)
        }
    }

    @Test
    fun toTextBuffer() {
        ioTo(TestInformationStub.latinLipsum)
        ioTo(TestInformationStub.greekLipsum)
        ioTo(TestInformationStub.chineseLipsum)
    }

    private fun io(text: String) {
        val lipsum = text.encodeToByteArray()
        val buf = TextBuffer(lipsum.size)

        var idx = 0
        while (idx < lipsum.size) {
            val cp = lipsum.readGlyphAt(idx)
            idx += cp.octetSize()
            buf.write(cp)
        }

        buf.flip()
        idx = 0
        while (idx < lipsum.size) {
            val cp = lipsum.readGlyphAt(idx)
            idx += cp.octetSize()
            assertEquals(buf.read().value, cp.value)
        }
    }

    @Test
    fun readWriteGlyph() {
        io(TestInformationStub.latinLipsum)
        io(TestInformationStub.greekLipsum)
        io(TestInformationStub.chineseLipsum)
    }

    @Test
    fun testRead() {
        val short = TestInformationStub.lipsumShort
        val text0 = TextBuffer(short.length)
        text0.write(short)
        text0.flip()
        val text1 = TextBuffer(short.length)
        text0.read(text1)

        assertEquals(text0.asBinary(), text1.asBinary())
    }


    @Test
    fun testWrite() {
        val short = TestInformationStub.lipsumShort
        val text = TextBuffer(short.length)
        text.write(short)

        assertEquals(short.toText().asBinary(), text.asBinary())
    }

    @Test
    fun testWrite1() {
        val short = TestInformationStub.lipsumShort
        val text0 = TextBuffer(short.length)
        text0.write(short)
        val text1 = TextBuffer(short.length)
        text1.write(text0)

        assertEquals(text0.asBinary(), text1.asBinary())
    }

    /*@Test
    fun writeLine() {
        val short = TestInformationStub.lipsumShort.toText()
        val text = TextBuffer(short.limit + 1)
        text.writeLine(short)

        val wrap = text.asBinary().asWrapped()
        wrap.positionAt(text.limit-1)
        assertEquals(wrap.readGlyph(), Ascii.CTRL_LF.cp.toCodePoint())

        text.limitAt(text.capacity - 1)
        assertEquals(short.asBinary(), text.asBinary())
    }*/
}