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
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertFailsWith
import kotlin.time.measureTime


class BlobReader(private val blob: Binary) : PumpReader {
    private var pos = 0

    private fun remaining(): Int = blob.limit - pos

    override fun read(data: Segment<*>): Int {
        val length = min(data.limit, remaining())

        if(length > 0) blob.copyInto(data, 0, pos, pos + length)
        data.limitAt(length)
        pos += length

        return length
    }
}

class TestPumpReader(private val bin: Binary, private val half: Boolean = false) : PumpReader {
    private var pos = 0

    private fun remaining(): Int = bin.limit - pos

    override fun read(data: Segment<*>): Int {
        var length = min(data.limit, remaining())

        // Error simulation
        if(half) if (remaining() < (bin.capacity / 2)) length /= 2

        if(length > 0) bin.copyInto(data, 0, pos, pos + length)
        data.limitAt(length)
        pos += length

        return length
    }

    constructor(text: String, half: Boolean = false): this(text.toText().asBinary(), half)

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

class PullTextTest {

    /**
     * The goal is to pull all data from the TextSource.
     * */
    @Test
    fun testStreamPull() {

        val text = latinLipsum + greekLipsum + chineseLipsum
        val copy = text.encodeToByteArray()
        val canvas = ByteArray(copy.size)
        var pos = 0


        val readable = Pipe.buildTextPullPipe(TestPumpReader(text))
        val time = measureTime {
            do {
                val cp = readable.readGlyph()
                pos += canvas.writeGlyphAt(pos, cp)
            } while(pos < canvas.size)
        }
        println(time)
        assertContentEquals(copy, canvas)
        assertFailsWith<UnsupportedOperationException> { readable.readGlyph() }
    }

    @Test
    fun testStreamPullClose() {

        val text = latinLipsum + greekLipsum + chineseLipsum
        val copy = text.encodeToByteArray()
        val canvas = ByteArray(copy.size)
        var pos = 0

        val readable = Pipe.buildTextPullPipe(TestPumpReader(text))
        do {
            val cp = readable.readGlyph()
            pos += canvas.writeGlyphAt(pos, cp)
        } while (pos < canvas.size / 2)

        readable.close()
        assertFailsWith<UnsupportedOperationException> { readable.readGlyph() }
    }
}