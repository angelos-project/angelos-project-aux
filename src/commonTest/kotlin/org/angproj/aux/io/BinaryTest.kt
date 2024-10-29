package org.angproj.aux.io

import org.angproj.aux.TestInformationStub
import org.angproj.aux.util.NullObject
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith


class BinaryTest: MemBlockTest<Binary>() {

    override val txtLen = TestInformationStub.refArray.size

    override fun setInput(): Binary = TestInformationStub.refArray.toBinary()

    @Test
    fun retrieveStoreByte() {
        val bin = setInput()
        (0 until bin.limit step 1).forEach { bin.storeByte(it, TestInformationStub.refByte) }
        (0 until bin.limit step 1).forEach { assertEquals(bin.retrieveByte(it), TestInformationStub.refByte) }
    }

    @Test
    fun retrieveStoreUByte() {
        val bin = setInput()
        (0 until bin.limit step 1).forEach { bin.storeUByte(it, TestInformationStub.refUByte) }
        (0 until bin.limit step 1).forEach { assertEquals(bin.retrieveUByte(it), TestInformationStub.refUByte) }
    }

    @Test
    fun retrieveStoreShort() {
        val bin = setInput()
        (0 until bin.limit step 2).forEach { bin.storeShort(it, TestInformationStub.refShort) }
        (0 until bin.limit step 2).forEach { assertEquals(bin.retrieveShort(it), TestInformationStub.refShort) }
    }

    @Test
    fun retrieveStoreUShort() {
        val bin = setInput()
        (0 until bin.limit step 2).forEach { bin.storeUShort(it, TestInformationStub.refUShort) }
        (0 until bin.limit step 2).forEach { assertEquals(bin.retrieveUShort(it), TestInformationStub.refUShort) }
    }

    @Test
    fun retrieveStoreInt() {
        val bin = setInput()
        (0 until bin.limit step 4).forEach { bin.storeInt(it, TestInformationStub.refInt) }
        (0 until bin.limit step 4).forEach { assertEquals(bin.retrieveInt(it), TestInformationStub.refInt) }
    }

    @Test
    fun retrieveStoreUInt() {
        val bin = setInput()
        (0 until bin.limit step 4).forEach { bin.storeUInt(it, TestInformationStub.refUInt) }
        (0 until bin.limit step 4).forEach { assertEquals(bin.retrieveUInt(it), TestInformationStub.refUInt) }
    }

    @Test
    fun retrieveStoreLong() {
        val bin = setInput()
        (0 until bin.limit step 8).forEach { bin.storeLong(it, TestInformationStub.refLong) }
        (0 until bin.limit step 8).forEach { assertEquals(bin.retrieveLong(it), TestInformationStub.refLong) }
    }

    @Test
    fun retrieveStoreULong() {
        val bin = setInput()
        (0 until bin.limit step 8).forEach { bin.storeULong(it, TestInformationStub.refULong) }
        (0 until bin.limit step 8).forEach { assertEquals(bin.retrieveULong(it), TestInformationStub.refULong) }
    }

    @Test
    fun retrieveStoreFloat() {
        val bin = setInput()
        (0 until bin.limit step 4).forEach { bin.storeFloat(it, TestInformationStub.refFloat) }
        (0 until bin.limit step 4).forEach { assertEquals(bin.retrieveFloat(it), TestInformationStub.refFloat) }
    }

    @Test
    fun retrieveStoreDouble() {
        val bin = setInput()
        (0 until bin.limit step 8).forEach { bin.storeDouble(it, TestInformationStub.refDouble) }
        (0 until bin.limit step 8).forEach { assertEquals(bin.retrieveDouble(it), TestInformationStub.refDouble) }
    }

    @Test
    fun testNullBinary() {
        assertFailsWith<UnsupportedOperationException> { NullObject.binary.isNull() }
        assertFailsWith<UnsupportedOperationException> { binOf(0).isNull() }
    }

    @Test
    fun byteRWOutbound() {
        val m = setInput()

        m.retrieveByte(0)
        assertFailsWith<IllegalArgumentException> {
            m.retrieveByte(-1)
        }

        m.retrieveByte(m.limit-TypeSize.byte) // Won't crash
        assertFailsWith<IllegalArgumentException> {
            m.retrieveByte(m.limit) // Must throw
        }

        m.storeByte(0, 1)
        assertFailsWith<IllegalArgumentException> {
            m.storeByte(-1, 1)
        }

        m.storeByte(m.limit-TypeSize.byte, 0) // Won't crash
        assertFailsWith<IllegalArgumentException> {
            m.storeByte(m.limit, 1) // Must throw
        }
    }

    @Test
    fun shortRWOutbound() {
        val m = setInput()

        m.retrieveShort(0)
        assertFailsWith<IllegalArgumentException> {
            m.retrieveShort(-1)
        }

        m.retrieveShort(m.limit-TypeSize.short) // Won't crash
        assertFailsWith<IllegalArgumentException> {
            m.retrieveShort(m.limit-1) // Must throw
        }

        m.storeShort(0, 1)
        assertFailsWith<IllegalArgumentException> {
            m.storeShort(-1, 1)
        }

        m.storeShort(m.limit-TypeSize.short, 0) // Won't crash
        assertFailsWith<IllegalArgumentException> {
            m.storeShort(m.limit-1, 1) // Must throw
        }
    }

    @Test
    fun intRWOutbound() {
        val m = setInput()

        m.retrieveInt(0)
        assertFailsWith<IllegalArgumentException> {
            m.retrieveInt(-1)
        }

        m.retrieveInt(m.limit-TypeSize.int) // Won't crash
        assertFailsWith<IllegalArgumentException> {
            m.retrieveInt(m.limit-3) // Must throw
        }

        m.storeInt(0, 1)
        assertFailsWith<IllegalArgumentException> {
            m.storeInt(-1, 1)
        }

        m.storeInt(m.limit-TypeSize.int, 0) // Won't crash
        assertFailsWith<IllegalArgumentException> {
            m.storeInt(m.limit-3, 1) // Must throw
        }
    }

    @Test
    fun longRWOutbound() {
        val m = setInput()

        m.retrieveLong(0)
        assertFailsWith<IllegalArgumentException> {
            m.retrieveLong(-1)
        }

        m.retrieveLong(m.limit-TypeSize.long) // Won't crash
        assertFailsWith<IllegalArgumentException> {
            m.retrieveLong(m.limit-7) // Must throw
        }

        m.storeLong(0, 1)
        assertFailsWith<IllegalArgumentException> {
            m.storeLong(-1, 1)
        }

        m.storeLong(m.limit-TypeSize.long, 0) // Won't crash
        assertFailsWith<IllegalArgumentException> {
            m.storeLong(m.limit-7, 1) // Must throw
        }
    }
}