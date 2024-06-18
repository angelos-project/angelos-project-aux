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

import org.angproj.aux.io.DataSize
import org.angproj.aux.io.PumpReader
import org.angproj.aux.io.Segment
import org.angproj.aux.io.TypeSize
import org.angproj.aux.mem.Default
import org.angproj.aux.utf.writeGlyphAt
import org.angproj.aux.util.Reify
import org.angproj.aux.util.chunkLoop
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.time.measureTime

class BinaryReader(private val half: Boolean = false) : PumpReader {

    override fun read(data: Segment): Int {

        //if(half) if (this.data.remaining < (this.data.size / 2)) data.limit /= 2

        var index = chunkLoop<Reify>(0, data.limit, TypeSize.long) {
            data.setLong(it, Random.nextLong())
        }
        index = chunkLoop<Reify>(index, data.limit, TypeSize.byte) {
            data.setByte(it, Random.nextInt().toByte())
        }
        return index
    }
}

class PullBinaryTest {

    /**
     * The goal is to pull all data from the TextSource.
     * */
    @Test
    fun testStreamPull() {
        val readable = PullPipe<BinaryType>(
            Default,
            PumpSource(BinaryReader()),
            DataSize._1K,
            DataSize._1K
        ).getSink()
        var count = 0
        readable.readByte()
        val time = measureTime {
            do {
                val cp = readable.readLong()
                count += 8
            } while(count < 3000)
        }
        println(time)
        //assertContentEquals(copy, canvas)
        //assertFailsWith<UnsupportedOperationException> { readable.readGlyph() }
        TODO("Make correctly.")

    }

    @Test
    fun testStreamPullClose() {

        val text = latinLL + greekLL + chineseLL
        val copy = text.encodeToByteArray()
        val canvas = ByteArray(copy.size)
        var pos = 0

        val readable = Pipe.buildTextPullPipe(StringReader(text)) // PullPipe(Default, PumpSource<TextType>(StringReader(text))).getSink()
        do {
            val cp = readable.readGlyph()
            pos += canvas.writeGlyphAt(pos, cp)
        } while(pos < canvas.size / 2)

        readable.close()
        assertFailsWith<UnsupportedOperationException> { readable.readGlyph() }
        TODO("Make correctly.")

    }
}