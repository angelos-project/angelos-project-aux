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
package org.angproj.aux.pipe

import org.angproj.aux.TestInformationStub.chineseLipsum
import org.angproj.aux.TestInformationStub.greekLipsum
import org.angproj.aux.TestInformationStub.latinLipsum
import org.angproj.aux.buf.copyInto
import org.angproj.aux.buf.wrap
import org.angproj.aux.io.*
import org.angproj.aux.mem.BufMgr
import org.angproj.aux.util.*
import kotlin.math.min
import kotlin.test.*
import kotlin.time.measureTime


class BlobWriter(private val blob: Binary) : PumpWriter {
    private var pos = 0

    override val count: Long
        get() = pos.toLong()

    override val stale: Boolean
        get() = remaining() <= 0

    private fun remaining(): Int = blob.limit - pos

    override fun write(data: Segment<*>): Int {
        val length = min(data.limit, remaining())

        if(length > 0) BufMgr.asWrap(data) { copyInto(blob, pos, 0, length) }
        pos += length

        if(data.limit != length) blob.limitAt(pos) // Must set limit on txt if written input shorter than Binary
        return length
    }
}

class TestPumpWriter(data: Binary, private val half: Boolean = false) : PumpWriter {
    private val txt = data
    private var pos = 0

    override val count: Long
        get() = pos.toLong()

    override val stale: Boolean
        get() = remaining() <= 0

    private fun remaining(): Int = txt.limit - pos

    override fun write(data: Segment<*>): Int {
        var length = min(data.limit, remaining())

        // Error simulation
        if(half) if (remaining() < (txt.capacity / 2)) length /= 2

        if(length > 0) BufMgr.asWrap(data) { copyInto(txt, pos, 0, length) }
        pos += length

        return length
    }

    /*val data = DataBuffer(data.toByteArray())

    override fun write(data: Segment<*>): Int {
        var length = min(data.limit, this.data.remaining)

        if(half) if (this.data.remaining < (this.data.size / 2)) length /= 2

        var index = chunkLoop<Reify>(0, length, TypeSize.long) {
            this.data.writeLong(data.getLong(it))
        }

        index = chunkLoop<Reify>(index, length, TypeSize.byte) {
            this.data.writeByte(data.getByte(it))
        }
        return index
    }*/
}

fun ByteArray.toText(): Text = BufMgr.txt(this.size).also { tb ->
    var offset = 0
    do { offset += tb.storeGlyph(offset, this@toText.readGlyphAt(offset)) } while (offset < tb.limit)
}

class PushTextTest {

    /**
     * The goal is to pull all data from the TextSource.
     * */
    @Test
    fun testStreamPush() {

        /*val text = (latinSH + greekSH + chineseSH).encodeToByteArray()
        val canvas = BufMgr.txt(text.size)
        val writeable = Pipe.buildTextPushPipe(TestPumpWriter(canvas.asBinary())) // PushPipe(PumpSink<TextType>(StringWriter(canvas))).getSource()
        var pos = 0

        val time = measureTime {
            do {
                val cp = text.readGlyphAt(pos)
                pos += cp.octetSize()
                writeable.writeGlyph(cp)
            } while(pos < text.size)
        }
        writeable.flush()
        println(time)
        assertContentEquals(text, canvas.toByteArray())*/

        /**
         * Setting up a control text to run through the pipe and to check against
         * Both [text] and [canvas] are of the same type and size, [canvas] is left empty
         * */
        val text = (latinLipsum + greekLipsum + chineseLipsum).encodeToByteArray().toText()
        val canvas = BufMgr.txt(text.capacity)
        /**
         * The [canvas] is run in the PumpWriter to be pumped through the pipe.
         * */
        val writeable = Pipe.buildTextPushPipe(TestPumpWriter(canvas.asBinary()))

        /**
         * The [text] is wrapped as a [Binary], it is read and written glyph by glyph to the pipe
         * */
        val time = measureTime {
            text.asBinary().wrap {
                do {
                    writeable.writeGlyph(readGlyph())
                } while(position < limit)
            }
        }

        /**
         * The [text] and [canvas] are identical only after a flush is made
         * The flush forcefully writes the internal queue to the PumpWriter
         * */
        assertNotEquals(text, canvas)
        writeable.flush()
        println(time)
        assertEquals(text, canvas)
        assertTrue { writeable.isOpen() }
        writeable.close()
        assertFalse { writeable.isOpen() }
    }

    @Test
    fun testStreamPushAutoClose() {

        /*val text = (latinSH + greekSH + chineseSH).encodeToByteArray()
        val canvas = BufMgr.txt(text.size)
        val writeable = Pipe.buildTextPushPipe(TestPumpWriter(canvas.asBinary(), true)) //PushPipe(Default, PumpSink<TextType>(StringWriter(canvas, true))).getSource()
        var pos = 0

        assertFailsWith<UnsupportedOperationException> {
            do {
                val cp = text.readGlyphAt(pos)
                pos += cp.octetSize()
                writeable.writeGlyph(cp)
            } while(pos < text.size)
        }

        writeable.flush()
        assertFailsWith<UnsupportedOperationException> { writeable.writeGlyph(CodePoint(123)) }
        writeable.close()*/

        val text = (latinLipsum + greekLipsum + chineseLipsum).encodeToByteArray().toText()
        val canvas = BufMgr.txt(text.capacity)
        /**
         * The pipe has [half] set to true, this will cause the PumpWriter to misbehave in the middle of the act
         * */
        val writeable = Pipe.buildTextPushPipe(TestPumpWriter(canvas.asBinary(), true))

        /**
         * Because of the misbehavior the pipe should close the queue and send a Null segment
         * signaling internally EOF of the inner queue of the pipe
         * */
        assertFailsWith<StaleException> {
            text.asBinary().wrap {
                do {
                    writeable.writeGlyph(readGlyph())
                } while(position < limit)
            }
        }

        /**
         * After a misbehavior and a flush the [text] and [canvas] should not be identical,
         * neither should any subsequent writes be accepted to the pipe
         * */
        /*writeable.flush()
        assertNotEquals(text, canvas)
        assertFailsWith<UnsupportedOperationException> { writeable.writeGlyph(CodePoint(123)) }
        assertTrue { writeable.isOpen() } // Is this correct?
        writeable.close()*/
        assertFailsWith<StaleException> { writeable.flush() }
        assertNotEquals(text, canvas)
        //assertFailsWith<StaleException> { writeable.writeGlyph(CodePoint(123)) }
        //assertTrue { writeable.isOpen() } // Is this correct?
        writeable.close()
    }

    @Test
    fun testStreamPushManualClose() {

        val text = (latinLipsum + greekLipsum + chineseLipsum).encodeToByteArray().toText()
        val canvas = BufMgr.txt(text.capacity)
        val writeable = Pipe.buildTextPushPipe(TestPumpWriter(canvas.asBinary())) // PushPipe(PumpSink<TextType>(StringWriter(canvas))).getSource()
        //var pos = 0

        text.asBinary().wrap {
            do {
                writeable.writeGlyph(readGlyph())
            } while(position < limit / 2)
        }

        /*do {
            val cp = text.readGlyphAt(pos)
            pos += cp.octetSize()
            writeable.writeGlyph(cp)
        } while(pos < text.size / 2)*/


        /**
         * A pipe should not accept any subsequent writes after close or after a following flush either,
         * a closed pipe is a closed pip
         * */
        /*writeable.flush()
        assertNotEquals(text, canvas)
        writeable.close()
        assertFailsWith<UnsupportedOperationException> { writeable.writeGlyph(CodePoint(123)) }
        writeable.flush()
        assertFailsWith<UnsupportedOperationException> { writeable.writeGlyph(CodePoint(123)) }
        assertFalse { writeable.isOpen() }*/

        writeable.flush()
        assertNotEquals(text, canvas)
        writeable.close()
        assertFailsWith<PipeException> { writeable.writeGlyph(CodePoint(123)) }
        assertFailsWith<PipeException> { writeable.flush() }
        assertFailsWith<PipeException> { writeable.writeGlyph(CodePoint(123)) }
    }
}