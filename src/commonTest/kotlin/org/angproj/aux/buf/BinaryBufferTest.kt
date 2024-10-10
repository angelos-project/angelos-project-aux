package org.angproj.aux.buf

import org.angproj.aux.TestInformationStub
import kotlin.test.Test
import kotlin.test.assertEquals


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
}