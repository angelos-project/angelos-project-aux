package org.angproj.aux.buf

import org.angproj.aux.TestInformationStub
import org.angproj.aux.io.TypeSize
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith


class BinaryBufferTest: AbstractFlowBufferTest<BinaryBuffer>() {

    override fun setInput(): BinaryBuffer {
        val buf = BinaryBuffer()

        buf.writeByte(TestInformationStub.refByte)
        buf.writeUByte(TestInformationStub.refUByte)
        buf.writeShort(TestInformationStub.refShort)
        buf.writeUShort(TestInformationStub.refUShort)
        buf.writeInt(TestInformationStub.refInt)
        buf.writeUInt(TestInformationStub.refUInt)
        buf.writeLong(TestInformationStub.refLong)
        buf.writeULong(TestInformationStub.refULong)
        buf.writeFloat(TestInformationStub.refFloat)
        buf.writeDouble(TestInformationStub.refDouble)

        return buf
    }

    override val posValue: Int = 42

    @Test
    fun readWriteByte() {
        val buf = BinaryBuffer()
        buf.writeByte(TestInformationStub.refByte)
        buf.flip()
        assertEquals(buf.readByte(), TestInformationStub.refByte)
    }

    @Test
    fun readWriteUByte() {
        val buf = BinaryBuffer()
        buf.writeUByte(TestInformationStub.refUByte)
        buf.flip()
        assertEquals(buf.readUByte(), TestInformationStub.refUByte)
    }

    @Test
    fun readWriteShort() {
        val buf = BinaryBuffer()
        buf.writeShort(TestInformationStub.refShort)
        buf.flip()
        assertEquals(buf.readShort(), TestInformationStub.refShort)
    }

    @Test
    fun readWriteUShort() {
        val buf = BinaryBuffer()
        buf.writeUShort(TestInformationStub.refUShort)
        buf.flip()
        assertEquals(buf.readUShort(), TestInformationStub.refUShort)
    }

    @Test
    fun readWriteInt() {
        val buf = BinaryBuffer()
        buf.writeInt(TestInformationStub.refInt)
        buf.flip()
        assertEquals(buf.readInt(), TestInformationStub.refInt)
    }

    @Test
    fun readWriteUInt() {
        val buf = BinaryBuffer()
        buf.writeUInt(TestInformationStub.refUInt)
        buf.flip()
        assertEquals(buf.readUInt(), TestInformationStub.refUInt)
    }

    @Test
    fun readWriteLong() {
        val buf = BinaryBuffer()
        buf.writeLong(TestInformationStub.refLong)
        buf.flip()
        assertEquals(buf.readLong(), TestInformationStub.refLong)
    }

    @Test
    fun readWriteULong() {
        val buf = BinaryBuffer()
        buf.writeULong(TestInformationStub.refULong)
        buf.flip()
        assertEquals(buf.readULong(), TestInformationStub.refULong)
    }

    @Test
    fun readWriteFloat() {
        val buf = BinaryBuffer()
        buf.writeFloat(TestInformationStub.refFloat)
        buf.flip()
        assertEquals(buf.readFloat(), TestInformationStub.refFloat)
    }

    @Test
    fun readWriteDouble() {
        val buf = BinaryBuffer()
        buf.writeDouble(TestInformationStub.refDouble)
        buf.flip()
        assertEquals(buf.readDouble(), TestInformationStub.refDouble)
    }

    @Test
    fun toBinary() {
        val buf = setInput()
        buf.flip()
        val bin = buf.toBinary()

        assertEquals(buf._segment, bin._segment)
    }

    @Test
    fun byteRWOutbound() {
        val m = setInput()

        m.readByte()
        assertFailsWith<IllegalArgumentException> {
            m.positionAt(-1)
            m.readByte()
        }

        m.positionAt(m.limit - TypeSize.byte)
        m.readByte() // Won't crash
        assertFailsWith<IllegalArgumentException> {
            m.positionAt(m.limit)
            m.readByte() // Must throw
        }

        m.positionAt(0)
        m.writeByte(1)
        assertFailsWith<IllegalArgumentException> {
            m.positionAt(-1)
            m.writeByte(1)
        }

        m.positionAt(m.limit - TypeSize.byte)
        m.writeByte(0) // Won't crash
        assertFailsWith<IllegalArgumentException> {
            m.positionAt(m.limit)
            m.writeByte(1) // Must throw
        }
    }

    @Test
    fun shortRWOutbound() {
        val m = setInput()

        m.readShort()
        assertFailsWith<IllegalArgumentException> {
            m.positionAt(-1)
            m.readShort()
        }

        m.positionAt(m.limit - TypeSize.short)
        m.readShort() // Won't crash
        assertFailsWith<IllegalArgumentException> {
            m.positionAt(m.limit-1)
            m.readShort() // Must throw
        }

        m.positionAt(0)
        m.writeShort(1)
        assertFailsWith<IllegalArgumentException> {
            m.positionAt(-1)
            m.writeShort(1)
        }

        m.positionAt(m.limit - TypeSize.short)
        m.writeShort(0) // Won't crash
        assertFailsWith<IllegalArgumentException> {
            m.positionAt(m.limit-1)
            m.writeShort(1) // Must throw
        }
    }

    @Test
    fun intRWOutbound() {
        val m = setInput()

        m.readInt()
        assertFailsWith<IllegalArgumentException> {
            m.positionAt(-1)
            m.readInt()
        }

        m.positionAt(m.limit - TypeSize.int)
        m.readInt() // Won't crash
        assertFailsWith<IllegalArgumentException> {
            m.positionAt(m.limit-3)
            m.readInt() // Must throw
        }

        m.positionAt(0)
        m.writeInt(1)
        assertFailsWith<IllegalArgumentException> {
            m.positionAt(-1)
            m.writeInt(1)
        }

        m.positionAt(m.limit - TypeSize.int)
        m.writeInt(0) // Won't crash
        assertFailsWith<IllegalArgumentException> {
            m.positionAt(m.limit-3)
            m.writeInt(1) // Must throw
        }
    }

    @Test
    fun longRWOutbound() {
        val m = setInput()

        m.readLong()
        assertFailsWith<IllegalArgumentException> {
            m.positionAt(-1)
            m.readLong()
        }

        m.positionAt(m.limit - TypeSize.long)
        m.readLong() // Won't crash
        assertFailsWith<IllegalArgumentException> {
            m.positionAt(m.limit-7)
            m.readLong() // Must throw
        }

        m.positionAt(0)
        m.writeLong(1)
        assertFailsWith<IllegalArgumentException> {
            m.positionAt(-1)
            m.writeLong(-1)
        }

        m.positionAt(m.limit - TypeSize.long)
        m.writeLong(0) // Won't crash
        assertFailsWith<IllegalArgumentException> {
            m.positionAt(m.limit-7)
            m.writeLong(1) // Must throw
        }
    }
}