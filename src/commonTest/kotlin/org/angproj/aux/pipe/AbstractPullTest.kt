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
import org.angproj.aux.buf.wrap
import org.angproj.aux.io.*
import org.angproj.aux.util.Uuid4
import org.angproj.aux.util.chunkLoop
import kotlin.math.min
import kotlin.test.*


/**
 * Standard [PumpReader] behaving normally, stale when eof at reach of limit.
 * */
class BlobReader(private val blob: Binary) : PumpReader {
    private var pos = 0

    override val outputCount: Long
        get() = pos.toLong()

    override val outputStale: Boolean
        get() = remaining() <= 0

    private fun remaining(): Int = blob.limit - pos

    override fun read(data: Segment<*>): Int {
        val length = min(data.limit, remaining())

        if(length > 0) blob.copyInto(data, 0, pos, pos + length)
        pos += length

        return length
    }
}


/**
 * Multiple blobs in a list, can simulate several messages.
 * */
class MultiBlobReader(private val blobList: MutableList<Binary>) : PumpReader {
    private var blob = binOf(0)
    private var pos = 0
    private var cnt = 0

    override val outputCount: Long
        get() = cnt + pos.toLong()

    override val outputStale: Boolean
        get() = remaining() <= 0 && blobList.isEmpty()

    private fun remaining(): Int = blob.limit - pos

    override fun read(data: Segment<*>): Int {
        if(remaining() == 0) {
            blob = blobList.removeFirstOrNull() ?: binOf(0)
            cnt += pos
            pos = 0
        }
        val length = min(data.limit, remaining())

        if(length > 0) blob.copyInto(data, 0, pos, pos + length)
        pos += length

        return length
    }
}


abstract class AbstractPullTest {

    abstract var debugMode: Boolean

    fun debug(block: () -> Unit) { if (debugMode) block() }

    /**
     * Uses a binary sink and a fixed size of random data.
     * */
    fun pullBinaryManualClose(dataCap: Int, segSize: DataSize, bufSize: DataSize) {
        debug {
            println("======== ======== Start ======== ========")
            println("Transmission size: $dataCap")
            println("Segment size: ${segSize.size}")
            println("Buffer size: ${bufSize.size}")
            println()
        }

        val send = binOf(dataCap).apply { Uuid4.read(this._segment) }
        val receive = binOf(send.capacity)

        val sink = buildSink { pull(BlobReader(send)).seg(segSize).buf(bufSize).bin() }

        var index = 0
        receive.wrap {
            index = chunkLoop<Unit>(index, receive.limit, TypeSize.long) { writeLong(sink.readLong()) }
            index = chunkLoop<Unit>(index, receive.limit, TypeSize.byte) { writeByte(sink.readByte()) }
        }
        receive.limitAt(index)

        debug {
            println(sink.telemetry())
            println()
        }

        sink.close()
        assertFalse { sink.isOpen() }

        receive.limitAt(send.limit)
        assertEquals(send.checkSum(), receive.checkSum())

        assertFailsWith<PipeException> { sink.readByte() }

        debug {
            println(sink.telemetry())
            println("======== ======== Finish ======== ========")
        }
    }

    /**
     * Uses a text sink and one page of UTF text, one time
     * */
    fun pullTextManualClose(segSize: DataSize, bufSize: DataSize) {
        val send = (chineseLipsum).toText().asBinary()

        debug {
            println("======== ======== Start ======== ========")
            println("Transmission size: ${send.limit}")
            println("Segment size: ${segSize.size}")
            println("Buffer size: ${bufSize.size}")
            println()
        }

        val receive = binOf(send.capacity)

        val sink = buildSink { pull(BlobReader(send)).seg(segSize).buf(bufSize).txt() }

        var index = 0
        receive.wrap {
            while (index < receive.limit) {
                val octet = sink.readGlyph()
                writeGlyph(octet)
                index += octet.octetSize()
            }
        }
        receive.limitAt(index)

        debug {
            println(sink.telemetry())
            println()
        }

        sink.close()
        assertFalse { sink.isOpen() }

        receive.limitAt(send.limit)
        assertEquals(send.checkSum(), receive.checkSum())

        assertFailsWith<PipeException> { sink.readGlyph() }

        debug {
            println(sink.telemetry())
            println("======== ======== Finish ======== ========")
        }
    }

    /**
     * Uses a text sink and several pages of UTF text, several times
     * */
    fun pullTextManualCloseRefill(segSize: DataSize, bufSize: DataSize) {
        val send = mutableListOf(
            latinLipsum.toText().asBinary(),
            greekLipsum.toText().asBinary(),
            chineseLipsum.toText().asBinary()
        )

        debug {
            println("======== ======== Start ======== ========")
            println("Transmission 1 size: ${send[0].limit}")
            println("Transmission 2 size: ${send[1].limit}")
            println("Transmission 2 size: ${send[2].limit}")
            println("Segment size: ${segSize.size}")
            println("Buffer size: ${bufSize.size}")
            println()
        }

        val witness = (latinLipsum + greekLipsum + chineseLipsum).toText().asBinary()
        val receive = binOf(send.sumOf { it.limit })

        val sink = buildSink { pull(MultiBlobReader(send)).seg(segSize).buf(bufSize).txt() }

        var index = 0
        receive.wrap {
            while (index < witness.limit) {
                val octet = sink.readGlyph()
                writeGlyph(octet)
                index += octet.octetSize()
            }
        }

        debug {
            println(sink.telemetry())
            println()
        }

        sink.close()
        assertFalse { sink.isOpen() }

        receive.limitAt(witness.limit)
        assertEquals(witness.checkSum(), receive.checkSum())

        assertFailsWith<PipeException> { sink.readGlyph() }

        debug {
            println(sink.telemetry())
            println("======== ======== Finish ======== ========")
        }
    }

    @Test
    abstract fun testPullManualClose()

    fun pullBinaryAutomaticClose(dataCap: Int, segSize: DataSize, bufSize: DataSize) {
        debug {
            println("======== ======== Start ======== ========")
            println("Transmission size: $dataCap")
            println("Segment size: ${segSize.size}")
            println("Buffer size: ${bufSize.size}")
            println()
        }

        val send = binOf(dataCap).apply { Uuid4.read(this._segment) }
        val receive = binOf(send.capacity)

        val sink = buildSink { pull(BlobReader(send)).seg(segSize).buf(bufSize).bin() }

        var index = 0
        receive.wrap {
            index = chunkLoop<Unit>(index, receive.limit, TypeSize.long) { writeLong(sink.readLong()) }
            index = chunkLoop<Unit>(index, receive.limit, TypeSize.byte) { writeByte(sink.readByte()) }
        }

        debug {
            println(sink.telemetry())
            println()
        }


        receive.limitAt(send.limit)
        assertEquals(send.checkSum(), receive.checkSum())

        assertFailsWith<StaleException> { sink.readByte() }
        assertFailsWith<StaleException> { sink.readByte() }
        assertTrue { sink.isOpen() } // Still open
        assertTrue { sink.telemetry().pumpOpen } // Still open
        assertFailsWith<StaleException> { sink.readByte() }
        assertFalse { sink.isOpen() }
        assertFalse { sink.telemetry().pumpOpen } // Closes on third strike
        assertFailsWith<PipeException> { sink.readByte() }


        debug {
            println(sink.telemetry())
            println("======== ======== Finish ======== ========")
        }
    }

    fun pullTextAutomaticClose(segSize: DataSize, bufSize: DataSize) {
        val send = (chineseLipsum).toText().asBinary()

        debug {
            println("======== ======== Start ======== ========")
            println("Transmission size: ${send.limit}")
            println("Segment size: ${segSize.size}")
            println("Buffer size: ${bufSize.size}")
            println()
        }

        val receive = binOf(send.capacity)

        val sink = buildSink { pull(BlobReader(send)).seg(segSize).buf(bufSize).txt() }

        var index = 0
        receive.wrap {
            while (index < receive.limit) {
                val octet = sink.readGlyph()
                writeGlyph(octet)
                index += octet.octetSize()
            }
        }

        debug {
            println(sink.telemetry())
            println()
        }


        receive.limitAt(send.limit)
        assertEquals(send.checkSum(), receive.checkSum())

        assertFailsWith<StaleException> { sink.readGlyph() }
        assertFailsWith<StaleException> { sink.readGlyph() }
        assertTrue { sink.isOpen() } // Still open
        assertTrue { sink.telemetry().pumpOpen } // Still open
        assertFailsWith<StaleException> { sink.readGlyph() }
        assertFalse { sink.isOpen() }
        assertFalse { sink.telemetry().pumpOpen } // Closes on third strike
        assertFailsWith<PipeException> { sink.readGlyph() }

        debug {
            println(sink.telemetry())
            println("======== ======== Finish ======== ========")
        }
    }

    @Test
    abstract fun testPullAutoClose()
}