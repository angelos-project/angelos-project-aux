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
            buf.writeGlyph(cp)
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
            assertEquals(buf.readGlyph().value, cp.value)
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
            buf.writeGlyph(cp)
        }

        buf.flip()
        idx = 0
        while (idx < lipsum.size) {
            val cp = lipsum.readGlyphAt(idx)
            idx += cp.octetSize()
            assertEquals(buf.readGlyph().value, cp.value)
        }
    }

    @Test
    fun readWriteGlyph() {
        io(TestInformationStub.latinLipsum)
        io(TestInformationStub.greekLipsum)
        io(TestInformationStub.chineseLipsum)
    }

    @Test
    fun select() {
        val buf = """本格表世向駐供暮基造食四検内協案。山文提議負表崎何九被博特止点関通写覧馬。会出週朝野加交伊再謝神年拡員部禁辺。
府構供投十隊済参国拐政意紛集癒夜治和。陸規地景何守谷困乱青購謝輸。同極価売現近題日稿売報革衛月塁両改。禁消情飯治刊読救南毎番五掲田夫意鈴。
手新市要所由州時青拳数子。党詳半前象写鐘木亡情強万構図天報。🤪""".toTextBuffer()

        val txt = buf.toText()
        assertEquals(buf._segment, txt._segment)
    }

    @Test
    fun toText() {
        val buf = setInput()
        buf.flip()
        val txt = buf.toText()

        assertEquals(buf._segment, txt._segment)
    }
}