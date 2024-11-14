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
import org.angproj.aux.util.CodePoint
import org.angproj.aux.util.Uuid4
import org.angproj.aux.util.chunkLoop
import kotlin.math.min
import kotlin.test.*

/**
 * Standard [PumpWriter] behaving normally, stale when full.
 * This [PumpWriter] requires manual setting of limit of the blob at finish.
 * */
class BlobWriter(private val blob: Binary) : PumpWriter {
    private var pos = 0

    override val inputCount: Long
        get() = pos.toLong()

    override val inputStale: Boolean
        get() = remaining() <= 0

    private fun remaining(): Int = blob.limit - pos

    override fun write(data: Segment<*>): Int {
        val length = min(data.limit, remaining())

        if(length > 0) BufMgr.asWrap(data) { copyInto(blob, pos, 0, length) }
        pos += length

        return length
    }
}


class MultiBlobWriter(private val blobList: List<Binary>) : PumpWriter {
    private var blob = binOf(0)
    private var pos = 0
    private var cnt = 0
    private var offset = 0

    override val inputCount: Long
        get() = cnt + pos.toLong()

    override val inputStale: Boolean
        get() = remaining() <= 0 && offset >= blobList.size

    private fun remaining(): Int = blob.limit - pos

    override fun write(data: Segment<*>): Int {
        if(remaining() == 0) {
            blob = blobList.getOrNull(offset) ?: binOf(0)
            cnt += pos
            pos = 0
            offset++
        }
        val length = min(data.limit, remaining())

        if(length > 0) BufMgr.asWrap(data) { copyInto(blob, pos, 0, length) }
        pos += length

        return length
    }
}


abstract class AbstractPushTest {

    abstract var debugMode: Boolean

    fun debug(block: () -> Unit) { if (debugMode) block() }

    fun pushBinaryManualClose(dataCap: Int, segSize: DataSize, bufSize: DataSize) {
        debug {
            println("======== ======== Start ======== ========")
            println("Transmission size: $dataCap")
            println("Segment size: ${segSize.size}")
            println("Buffer size: ${bufSize.size}")
            println()
        }

        val send = binOf(dataCap).apply { Uuid4.read(this._segment) }
        val receive = binOf(send.capacity)

        val source = buildSource { push(BlobWriter(receive)).seg(segSize).buf(bufSize).bin() }

        var index = 0
        send.wrap {
            index = chunkLoop<Unit>(index, send.limit, TypeSize.long) { source.writeLong(readLong()) }
            index = chunkLoop<Unit>(index, send.limit, TypeSize.byte) { source.writeByte(readByte()) }
        }

        debug {
            println(source.telemetry())
            println()
        }

        source.flush()
        assertEquals(receive.checkSum(), send.checkSum())

        source.close()
        assertFalse { source.isOpen() }
        assertFailsWith<PipeException> { source.writeByte(45) }

        debug {
            println(source.telemetry())
            println("======== ======== Finish ======== ========")
        }
    }

    fun pushTextManualClose(segSize: DataSize, bufSize: DataSize) {
        val send = (chineseLipsum).toText().asBinary()

        debug {
            println("======== ======== Start ======== ========")
            println("Transmission size: ${send.limit}")
            println("Segment size: ${segSize.size}")
            println("Buffer size: ${bufSize.size}")
            println()
        }

        val receive = binOf(send.capacity)

        val source = buildSource { push(BlobWriter(receive)).seg(segSize).buf(bufSize).txt() }

        var index = 0
        send.wrap {
            while (index < receive.limit) {
                val octet = readGlyph()
                source.writeGlyph(octet)
                index += octet.octetSize()
            }
        }

        debug {
            println(source.telemetry())
            println()
        }

        source.flush()
        assertEquals(receive.checkSum(), send.checkSum())

        source.close()
        assertFalse { source.isOpen() }
        assertFailsWith<PipeException> { source.writeGlyph(CodePoint((45))) }

        debug {
            println(source.telemetry())
            println("======== ======== Finish ======== ========")
        }
    }

    fun pushTextManualCloseRefill(segSize: DataSize, bufSize: DataSize) {
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

        val source = buildSource { push(BlobWriter(receive)).seg(segSize).buf(bufSize).txt() }


        send.forEach {
            it.wrap {
                var index = 0
                while (index < it.limit) {
                    val octet = readGlyph()
                    source.writeGlyph(octet)
                    index += octet.octetSize()
                }
            }
        }


        debug {
            println(source.telemetry())
            println()
        }

        source.flush()
        assertEquals(receive.checkSum(), witness.checkSum())

        source.close()
        assertFalse { source.isOpen() }
        assertFailsWith<PipeException> { source.writeGlyph(CodePoint((45))) }

        debug {
            println(source.telemetry())
            println("======== ======== Finish ======== ========")
        }
    }

    @Test
    abstract fun testPushManualClose()

    fun pushBinaryAutomaticClose(dataCap: Int, segSize: DataSize, bufSize: DataSize) {
        debug {
            println("======== ======== Start ======== ========")
            println("Transmission size: $dataCap")
            println("Segment size: ${segSize.size}")
            println("Buffer size: ${bufSize.size}")
            println()
        }

        val send = binOf(dataCap).apply { Uuid4.read(this._segment) }
        val receive = binOf(send.capacity)

        val source = buildSource { push(BlobWriter(receive)).seg(segSize).buf(bufSize).bin() }

        var index = 0
        send.wrap {
            index = chunkLoop<Unit>(index, send.limit, TypeSize.long) { source.writeLong(readLong()) }
            index = chunkLoop<Unit>(index, send.limit, TypeSize.byte) { source.writeByte(readByte()) }
        }

        debug {
            println(source.telemetry())
            println()
        }


        source.flush()
        assertEquals(receive.checkSum(), send.checkSum())

        assertFailsWith<StaleException> {
            source.writeByte(45)
            source.flush()
        }
        assertFailsWith<StaleException> {
            source.writeByte(45)
            source.flush()
        }
        assertTrue { source.isOpen() } // Still open
        assertTrue { source.telemetry().pumpOpen } // Still open
        assertFailsWith<StaleException> {
            source.writeByte(45)
            source.flush()
        }
        //assertFalse { source.isOpen() }
        assertFalse { source.telemetry().pumpOpen } // Closes on third strike
        assertFailsWith<PipeException> {
            source.writeByte(45)
            source.flush()
        }

        debug {
            println(source.telemetry())
            println("======== ======== Finish ======== ========")
        }
    }

    fun pushTextAutomaticClose(segSize: DataSize, bufSize: DataSize) {
        val send = (chineseLipsum).toText().asBinary()

        debug {
            println("======== ======== Start ======== ========")
            println("Transmission size: ${send.limit}")
            println("Segment size: ${segSize.size}")
            println("Buffer size: ${bufSize.size}")
            println()
        }

        val receive = binOf(send.capacity)

        val source = buildSource { push(BlobWriter(receive)).seg(segSize).buf(bufSize).txt() }

        var index = 0
        send.wrap {
            while (index < receive.limit) {
                val octet = readGlyph()
                source.writeGlyph(octet)
                index += octet.octetSize()
            }
        }

        debug {
            println(source.telemetry())
            println()
        }

        source.flush()
        assertEquals(receive.checkSum(), send.checkSum())

        assertFailsWith<StaleException> {
            source.writeGlyph(CodePoint(45))
            source.flush()
        }
        assertFailsWith<StaleException> {
            source.writeGlyph(CodePoint(45))
            source.flush()
        }
        assertTrue { source.isOpen() } // Still open
        assertTrue { source.telemetry().pumpOpen } // Still open
        assertFailsWith<StaleException> {
            source.writeGlyph(CodePoint(45))
            source.flush()
        }
        //assertFalse { source.isOpen() }
        assertFalse { source.telemetry().pumpOpen } // Closes on third strike
        assertFailsWith<PipeException> {
            source.writeGlyph(CodePoint(45))
            source.flush()
        }

        debug {
            println(source.telemetry())
            println("======== ======== Finish ======== ========")
        }
    }

    @Test
    abstract fun testPushAutoClose()

    fun pushBinaryBroken() {
        debug {
            println("======== ======== Start ======== ========")
            println("Transmission size: 4096")
            println("Segment size: 1024")
            println("Buffer size: 2048")
            println()
        }

        val send = binOf(DataSize._4K.size).apply { Uuid4.read(this._segment) }
        val receive = listOf(
            binOf(DataSize._2K.size + DataSize._512B.size),
            binOf(DataSize._1K.size + DataSize._512B.size)
        )

        val source = buildSource { push(MultiBlobWriter(receive)).seg(DataSize._1K).buf(DataSize._2K).bin() }

        var index = 0
        send.wrap {
            index = chunkLoop<Unit>(index, send.limit, TypeSize.long) { source.writeLong(readLong()) }
            index = chunkLoop<Unit>(index, send.limit, TypeSize.byte) { source.writeByte(readByte()) }
        }

        debug {
            println(source.telemetry())
            println()
        }

        source.flush()
        assertEquals(receive.toBinary().checkSum(), send.checkSum())

        debug {
            println(source.telemetry())
            println("======== ======== Finish ======== ========")
        }
    }

    fun pushTextBroken() {
        debug {
            println("======== ======== Start ======== ========")
            println("Transmission size: 4096")
            println("Segment size: 1024")
            println("Buffer size: 2048")
            println()
        }

        val send = chineseLipsum.toText().asBinary()
        val receive = listOf(
            binOf(DataSize._2K.size + DataSize._512B.size),
            binOf(send.capacity - (DataSize._2K.size + DataSize._512B.size))
        )

        val source = buildSource { push(MultiBlobWriter(receive)).seg(DataSize._1K).buf(DataSize._2K).txt() }

        var index = 0
        send.wrap {
            while (index < limit) {
                val octet = readGlyph()
                source.writeGlyph(octet)
                index += octet.octetSize()
            }
        }

        debug {
            println(source.telemetry())
            println()
        }

        source.flush()
        assertEquals(receive.toBinary().checkSum(), send.checkSum())

        debug {
            println(source.telemetry())
            println("======== ======== Finish ======== ========")
        }
    }

    @Test
    abstract fun testPushBrokenPump()
}