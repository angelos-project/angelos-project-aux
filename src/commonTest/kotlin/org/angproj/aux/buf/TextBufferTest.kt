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
import org.angproj.aux.util.readGlyphAt
import kotlin.test.Test
import kotlin.test.assertEquals


class TextBufferTest: AbstractFlowBufferTest<TextBuffer>() {

     override fun setInput(): TextBuffer {
        val lipsum = TestInformationStub.lipsumMedium.encodeToByteArray()
        val buf = TextBuffer()

        var idx = 0
        while(idx < lipsum.size) {
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
        buf.flip()
        while(idx < lipsum.size) {
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
        while(idx < lipsum.size) {
            val cp = lipsum.readGlyphAt(idx)
            idx += cp.octetSize()
            buf.writeGlyph(cp)
        }

        buf.flip()
        idx = 0
        while(idx < lipsum.size) {
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
}