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
import org.angproj.aux.io.*
import org.angproj.aux.util.writeGlyphAt
import kotlin.math.min
import kotlin.test.*
import kotlin.time.measureTime


class TestPumpReader(private val bin: Binary, private val half: Boolean = false) : PumpReader {
    private var pos = 0

    override val outputCount: Long
        get() = pos.toLong()

    override val outputStale: Boolean
        get() = remaining() <= 0

    private fun remaining(): Int = bin.limit - pos

    override fun read(data: Segment<*>): Int {
        var length = min(data.limit, remaining())

        // Error simulation
        if (half) if (remaining() < (bin.capacity / 2)) length /= 2

        if (length > 0) bin.copyInto(data, 0, pos, pos + length)
        data.limitAt(length)
        pos += length

        return length
    }

    constructor(text: String, half: Boolean = false) : this(text.toText().asBinary(), half)

    /*val txt = DataBuffer(text.encodeToByteArray())

    override fun read(data: Segment<*>): Int {
        var length  = min(data.limit, this.txt.remaining)

        if(half) if (this.txt.remaining < (this.txt.size / 2)) length /= 2


        var index = chunkLoop<Reify>(0, length, TypeSize.long) {
            data.setLong(it, this.txt.readLong())
        }
        index = chunkLoop<Reify>(index, length, TypeSize.byte) {
            data.setByte(it, this.txt.readByte())
        }
        return index
    }*/
}

class PullTextTest : AbstractPullTest() {

    override var debugMode: Boolean = false

    @Test
    override fun testPullManualClose() {
        pullTextManualClose(DataSize._1K, DataSize._4K)
        pullTextManualClose(DataSize._1K, DataSize._1K)

        pullTextManualCloseRefill(DataSize._1K, DataSize._4K)
        pullTextManualCloseRefill(DataSize._1K, DataSize._1K)
    }

    @Test
    override fun testPullAutoClose() {
        pullTextAutomaticClose(DataSize._1K, DataSize._4K)
        pullTextAutomaticClose(DataSize._1K, DataSize._1K)
    }

    /**
     * The goal is to pull all data from the TextSource.
     * */
    @Test
    fun testStreamPullClose() {

        val text = latinLipsum + greekLipsum + chineseLipsum
        val copy = text.encodeToByteArray()
        val canvas = ByteArray(copy.size)
        var pos = 0


        val readable = Pipe.buildTextPullPipe(TestPumpReader(text))
        val time = measureTime {
            do {
                val cp = readable.readGlyph()
                pos += canvas.writeGlyphAt(pos, cp)
            } while (pos < canvas.size)
        }
        println(time)
        assertContentEquals(copy, canvas)
        readable.close()
        //assertEquals(readable.memUse(), 0)
        assertFalse { readable.isOpen() }
        assertFailsWith<PipeException> { readable.readGlyph() }
    }

    @Test
    fun testStreamPullAutoClose() {

        val text = latinLipsum + greekLipsum + chineseLipsum
        val copy = text.encodeToByteArray()
        val canvas = ByteArray(copy.size)
        var pos = 0

        val readable = Pipe.buildTextPullPipe(TestPumpReader(text))
        do {
            val cp = readable.readGlyph()
            pos += canvas.writeGlyphAt(pos, cp)
        } while (pos < canvas.size)

        assertContentEquals(copy, canvas)
        assertFailsWith<StaleException> { readable.readGlyph() }
        assertFailsWith<StaleException> { readable.readGlyph() }
        assertTrue { readable.isOpen() } // Still open
        assertFailsWith<StaleException> { readable.readGlyph() }
        assertFalse { readable.isOpen() } // Closes on third strike
        assertFailsWith<PipeException> { readable.readGlyph() }
    }
}