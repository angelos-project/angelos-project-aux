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
import org.angproj.aux.buf.TextBuffer
import org.angproj.aux.buf.asBinary
import org.angproj.aux.buf.copyInto
import org.angproj.aux.io.*
import org.angproj.aux.mem.BufMgr
import org.angproj.aux.util.*
import kotlin.math.min
import kotlin.test.*


class TestPumpWriter(data: Binary, private val half: Boolean = false) : PumpWriter {
    private val txt = data
    private var pos = 0

    override val inputCount: Long
        get() = pos.toLong()

    override val inputStale: Boolean
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

class PushTextTest  : AbstractPushTest() {

    override var debugMode: Boolean = false

    @Test
    override fun testPushManualClose() {
        pushTextManualClose(DataSize._1K, DataSize._4K)
        pushTextManualClose(DataSize._1K, DataSize._1K)

        pushTextManualCloseRefill(DataSize._1K, DataSize._4K)
        pushTextManualCloseRefill(DataSize._1K, DataSize._1K)
    }

    @Test
    override fun testPushAutoClose() {
        pushTextAutomaticClose(DataSize._1K, DataSize._4K)
        pushTextAutomaticClose(DataSize._1K, DataSize._1K)
    }

    @Test
    override fun testPushBrokenPump() {
        pushTextBroken()
    }

    /**
     * Testing the TextWritable implementation
     *
     * Writing a Java/Kotlin String
     * */
    @Test
    fun testWrite() {
        val size = chineseLipsum.encodeToByteArray().size
        val text = binOf(size)

        val source = buildSource { push(BlobWriter(text)).txt() }
        source.write(chineseLipsum)
        source.flush()
        source.close()

        assertEquals(text, chineseLipsum.toText().asBinary())
    }

    /**
     * Testing the TextWritable implementation
     *
     * Writing a Text
     * */
    @Test
    fun testWrite1() {
        val size = chineseLipsum.encodeToByteArray().size
        val copy = binOf(size)
        val text = chineseLipsum.toText()

        val source = buildSource { push(BlobWriter(copy)).txt() }
        source.write(text)
        source.flush()
        source.close()

        assertEquals(text.asBinary(), copy)
    }

    /**
     * Testing the TextWritable implementation
     *
     * Writing a TextBuffer
     * */
    @Test
    fun testWrite2() {
        val size = chineseLipsum.encodeToByteArray().size
        val copy = binOf(size)
        val text = TextBuffer(size)
        text.write(chineseLipsum)

        val source = buildSource { push(BlobWriter(copy)).txt() }
        source.write(text)
        source.flush()
        source.close()

        assertEquals(text.asBinary(), copy)
    }
}