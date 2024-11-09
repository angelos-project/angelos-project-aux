package org.angproj.aux.util

import org.angproj.aux.TestInformationStub.refByteArray
import org.angproj.aux.TestInformationStub.refDouble
import org.angproj.aux.TestInformationStub.refFloat
import org.angproj.aux.TestInformationStub.refInt
import org.angproj.aux.TestInformationStub.refLong
import org.angproj.aux.TestInformationStub.refRevDouble
import org.angproj.aux.TestInformationStub.refRevFloat
import org.angproj.aux.TestInformationStub.refRevInt
import org.angproj.aux.TestInformationStub.refRevLong
import org.angproj.aux.TestInformationStub.refRevShort
import org.angproj.aux.TestInformationStub.refRevUInt
import org.angproj.aux.TestInformationStub.refRevULong
import org.angproj.aux.TestInformationStub.refRevUShort
import org.angproj.aux.TestInformationStub.refShort
import org.angproj.aux.TestInformationStub.refUInt
import org.angproj.aux.TestInformationStub.refULong
import org.angproj.aux.TestInformationStub.refUShort
import kotlin.test.Test
import kotlin.test.assertEquals

class BufferAwareTest : BufferAware {

    @Test
    fun readShortAtTest() { // Big Endian stream from Little Endian values
        assertEquals(refByteArray.readShortAt(0), refRevShort)
    }

    @Test
    fun readUShortAtTest() {
        assertEquals(refByteArray.readUShortAt(16), refRevUShort)
    }

    @Test
    fun readIntAtTest() {
        assertEquals(refByteArray.readIntAt(0), refRevInt)
    }

    @Test
    fun readUIntAtTest() {
        assertEquals(refByteArray.readUIntAt(16), refRevUInt)
    }

    @Test
    fun readLongAtTest() {
        assertEquals(refByteArray.readLongAt(0), refRevLong)
    }

    @Test
    fun readULongAtTest() {
        assertEquals(refByteArray.readULongAt(16), refRevULong)
    }

    @Test
    fun readFloatAtTest() {
        assertEquals(refByteArray.readFloatAt(0), refRevFloat)

    }

    @Test
    fun readDoubleAtTest() {
        assertEquals(refByteArray.readDoubleAt(0), refRevDouble)

    }

    @Test
    fun writeShortAtTest() {
        val array = ByteArray(2)
        array.writeShortAt(0, refShort)
        assertEquals(array.readRevShortAt(0), refRevShort)
    }

    @Test
    fun writeUShortAtTest() {
        val array = ByteArray(2)
        array.writeUShortAt(0, refUShort)
        assertEquals(array.readRevUShortAt(0), refRevUShort)
    }

    @Test
    fun writeIntAtTest() {
        val array = ByteArray(4)
        array.writeIntAt(0, refInt)
        assertEquals(array.readRevIntAt(0), refRevInt)
    }

    @Test
    fun writeUIntAtTest() {
        val array = ByteArray(4)
        array.writeUIntAt(0, refUInt)
        assertEquals(array.readRevUIntAt(0), refRevUInt)
    }

    @Test
    fun writeLongAtTest() {
        val array = ByteArray(8)
        array.writeLongAt(0, refLong)
        assertEquals(array.readRevLongAt(0), refRevLong)
    }

    @Test
    fun writeULongAtTest() {
        val array = ByteArray(8)
        array.writeULongAt(0, refULong)
        assertEquals(array.readRevULongAt(0), refRevULong)
    }

    @Test
    fun writeFloatAtTest() {
        val array = ByteArray(4)
        array.writeFloatAt(0, refFloat)
        assertEquals(array.readRevFloatAt(0), refRevFloat)
    }

    @Test
    fun writeDoubleAtTest() {
        val array = ByteArray(8)
        array.writeDoubleAt(0, refDouble)
        assertEquals(array.readRevDoubleAt(0), refRevDouble)
    }


    @Test
    fun readRevShortAtTest() {
        assertEquals(refByteArray.readRevShortAt(0), refByteArray.readShortAt(14))
    }

    @Test
    fun readRevUShortAtTest() {
        assertEquals(refByteArray.readRevUShortAt(16), refByteArray.readUShortAt(30))

    }

    @Test
    fun readRevIntAtTest() {
        assertEquals(refByteArray.readRevIntAt(0), refByteArray.readIntAt(12))

    }

    @Test
    fun readRevUIntAtTest() {
        assertEquals(refByteArray.readRevUIntAt(16), refByteArray.readUIntAt(28))

    }


    @Test
    fun readRevLongAtTest() {
        assertEquals(refByteArray.readRevLongAt(0), refByteArray.readLongAt(8))

    }

    @Test
    fun readRevULongAtTest() {
        assertEquals(refByteArray.readRevULongAt(16), refByteArray.readULongAt(24))
    }

    @Test
    fun readRevFloatAtTest() {
        assertEquals(refByteArray.readRevFloatAt(0), refByteArray.readFloatAt(12))
    }

    @Test
    fun readRevDoubleAtTest() {
        assertEquals(refByteArray.readRevDoubleAt(0), refByteArray.readDoubleAt(8))

    }

    @Test
    fun writeRevShortAtTest() {
        val array = ByteArray(2)
        array.writeRevShortAt(0, refRevShort)
        assertEquals(array.readShortAt(0), refShort)
    }

    @Test
    fun writeRevUShortAtTest() {
        val array = ByteArray(2)
        array.writeRevUShortAt(0, refRevUShort)
        assertEquals(array.readUShortAt(0), refUShort)
    }

    @Test
    fun writeRevIntAtTest() {
        val array = ByteArray(4)
        array.writeRevIntAt(0, refRevInt)
        assertEquals(array.readIntAt(0), refInt)
    }

    @Test
    fun writeRevUIntAtTest() {
        val array = ByteArray(4)
        array.writeRevUIntAt(0, refRevUInt)
        assertEquals(array.readUIntAt(0), refUInt)
    }

    @Test
    fun writeRevLongAtTest() {
        val array = ByteArray(8)
        array.writeRevLongAt(0, refRevLong)
        assertEquals(array.readLongAt(0), refLong)
    }

    @Test
    fun writeRevULongAtTest() {
        val array = ByteArray(8)
        array.writeRevULongAt(0, refRevULong)
        assertEquals(array.readULongAt(0), refULong)
    }

    @Test
    fun writeRevFloatAtTest() {
        val array = ByteArray(4)
        array.writeRevFloatAt(0, refRevFloat)
        assertEquals(array.readFloatAt(0), refFloat)
    }

    @Test
    fun writeRevDoubleAtTest() {
        val array = ByteArray(8)
        array.writeRevDoubleAt(0, refRevDouble)
        assertEquals(array.readDoubleAt(0), refDouble)
    }
}