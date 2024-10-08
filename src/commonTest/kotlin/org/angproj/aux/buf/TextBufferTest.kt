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

import org.angproj.aux.TestInformationStub.chineseLipsum
import org.angproj.aux.TestInformationStub.greekLipsum
import org.angproj.aux.TestInformationStub.latinLipsum
import org.angproj.aux.TestInformationStub.lipsumMedium
import org.angproj.aux.io.DataSize
import org.angproj.aux.io.Segment
import org.angproj.aux.util.readGlyphAt
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertIs

class TextBufferTest: AbstractBufferTest() {

    private fun setInput(text: String, size: DataSize = DataSize._4K): TextBuffer {
        val lipsum = text.encodeToByteArray()
        val buf = TextBuffer(size)

        var idx = 0
        while(idx < lipsum.size) {
            val cp = lipsum.readGlyphAt(idx)
            idx += cp.octetSize()
            buf.writeGlyph(cp)
        }

        return buf
    }

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
        ioTo(latinLipsum)
        ioTo(greekLipsum)
        ioTo(chineseLipsum)
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
        io(latinLipsum)
        io(greekLipsum)
        io(chineseLipsum)
    }

    @Test
    override fun getSegment() {
        assertIs<Segment>(setInput(lipsumMedium).segment)
    }

    @Test
    override fun getCapacity() {
        assertEquals(setInput(lipsumMedium).capacity, DataSize._4K.size)
    }

    @Test
    override fun getPosition() {
        assertEquals(setInput(lipsumMedium).position, 206)
    }

    @Test
    override fun positionAt() {
        val buf = setInput(lipsumMedium)

        buf.positionAt(200)
        assertEquals(buf.position, 200)

        assertFailsWith<IllegalArgumentException> { buf.positionAt(buf.mark-1) }
        assertFailsWith<IllegalArgumentException> { buf.positionAt(buf.limit+1) }
    }

    @Test
    override fun getLimit() {
        assertEquals(setInput(lipsumMedium).limit, DataSize._4K.size)
    }

    @Test
    override fun limitAt() {
        val buf = setInput(lipsumMedium)
        assertEquals(buf.position, 206)

        buf.limitAt(200)
        assertEquals(buf.limit, 200)
        assertEquals(buf.position, 200)

        assertFailsWith<IllegalArgumentException> { buf.limitAt(buf.mark-1) }
        assertFailsWith<IllegalArgumentException> { buf.limitAt(buf.capacity+1) }
    }

    @Test
    override fun getMark() {
        assertEquals(setInput(lipsumMedium).mark, 0)
    }

    @Test
    override fun markAt() {
        val buf = setInput(lipsumMedium)

        assertEquals(buf.mark, 0)
        assertEquals(buf.position, 206)

        buf.markAt()
        assertEquals(buf.mark, buf.position)
    }

    @Test
    override fun reset() {
        val buf = setInput(lipsumMedium)

        assertEquals(buf.mark, 0)
        assertEquals(buf.position, 206)

        buf.reset()
        assertEquals(buf.mark, 0)
        assertEquals(buf.position, 0)
    }

    @Test
    override fun clear() {
        val buf = setInput(lipsumMedium)
        assertEquals(buf.limit, buf.capacity)

        buf.markAt()
        buf.limitAt(206)

        assertEquals(buf.position, 206)
        assertEquals(buf.mark, 206)
        assertEquals(buf.limit, 206)

        buf.clear()
        assertEquals(buf.limit, buf.capacity)
        assertEquals(buf.mark, 0)
        assertEquals(buf.position, 0)
    }

    @Test
    override fun flip() {
        val buf = setInput(lipsumMedium)
        assertEquals(buf.limit, buf.capacity)
        assertEquals(buf.position, 206)

        buf.markAt()
        assertEquals(buf.mark, 206)

        buf.flip()
        assertEquals(buf.limit, 206)
        assertEquals(buf.mark, 0)
        assertEquals(buf.position, 0)
    }

    @Test
    override fun rewind() {
        val buf = setInput(lipsumMedium)

        assertEquals(buf.mark, 0)
        assertEquals(buf.position, 206)

        buf.markAt()
        assertEquals(buf.mark, 206)

        buf.rewind()
        assertEquals(buf.mark, 0)
        assertEquals(buf.position, 0)
    }

    @Test
    override fun getRemaining() {
        assertEquals(setInput(lipsumMedium).remaining, DataSize._4K.size - 206)
    }

    @Test
    override fun isView() {
        assertEquals(setInput(lipsumMedium).isView(), false)
    }

    @Test
    override fun isMem() {
        assertEquals(setInput(lipsumMedium).isMem(), false)
    }

    @Test
    override fun close() {
        setInput(lipsumMedium).close()
    }
}